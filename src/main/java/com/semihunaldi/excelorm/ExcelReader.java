package com.semihunaldi.excelorm;

import com.semihunaldi.excelorm.annotations.Excel;
import com.semihunaldi.excelorm.annotations.ExcelColumn;
import com.semihunaldi.excelorm.exceptions.IllegalExcelArgumentException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ExcelReader
{
    public <T extends BaseExcel> List<T> read(XSSFWorkbook xssfWorkbook, Class<T> clazz) throws InstantiationException, IllegalAccessException, IllegalExcelArgumentException, IOException
    {
        Validator.validate(xssfWorkbook);
        List<T> tList = new LinkedList<>();
        read(clazz, tList, xssfWorkbook);
        xssfWorkbook.getPackage().revert();
        return tList;
    }

    public <T extends BaseExcel> List<T> read(InputStream inputStream, Class<T> clazz) throws InstantiationException, IllegalAccessException, IllegalExcelArgumentException, IOException
    {
        List<T> tList = new LinkedList<>();
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
        inputStream.close();
        read(clazz, tList, xssfWorkbook);
        xssfWorkbook.getPackage().revert();
        return tList;
    }

    public <T extends BaseExcel> List<T> read(File file, Class<T> clazz) throws InstantiationException, IllegalAccessException, IllegalExcelArgumentException, IOException, InvalidFormatException
    {
        List<T> tList = new LinkedList<>();
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);
        read(clazz, tList, xssfWorkbook);
        xssfWorkbook.getPackage().revert();
        return tList;
    }

    private <T extends BaseExcel> void read(Class<T> clazz, List<T> tList, XSSFWorkbook xssfWorkbook) throws InstantiationException, IllegalAccessException, IllegalExcelArgumentException
    {
        Excel excelAnnotation = clazz.getAnnotation(Excel.class);
        if (excelAnnotation != null)
        {
            Validator.validate(excelAnnotation);
            int firstRow = excelAnnotation.firstRow();
            int firstCol = excelAnnotation.firstCol();
            String sheetName = excelAnnotation.sheetName();
            XSSFSheet xssfSheet = xssfWorkbook.getSheet(sheetName);
            for (int rows = firstRow; rows < firstRow + xssfSheet.getPhysicalNumberOfRows(); rows++)
            {
                T t = clazz.newInstance();
                updateRowField(t,clazz,rows);
                XSSFRow row = xssfSheet.getRow(rows);
                if(row != null)
                {
                    for (int cells = firstCol; cells < firstCol + row.getPhysicalNumberOfCells(); cells++)
                    {
                        XSSFCell cell = row.getCell(cells);
                        if(cell != null)
                        {
                            Field field = findFieldByColNumber(clazz, cells,firstCol);
                            if (field != null)
                            {
                                setFieldValue(t, cell, field);
                            }
                        }
                    }
                    tList.add(t);
                }
            }
        }
    }

    private <T extends BaseExcel> void setFieldValue(T t, XSSFCell cell, Field field)
    {
        Object o = getCellValue(cell, field);
        setFieldValue(t, field, o);
    }

    private <T extends BaseExcel> void setFieldValue(T t, Field field, Object o)
    {
        try
        {
            boolean accessible = field.isAccessible();
            field.setAccessible(true);
            field.set(t, o);
            field.setAccessible(accessible);
        }
        catch (IllegalAccessException e)
        {
            //swallow
        }
    }

    private Field findFieldByColNumber(Class clazz, int col, int firstCol)
    {
        List<Field> fieldsListWithAnnotation = FieldUtils.getFieldsListWithAnnotation(clazz, ExcelColumn.class);
        for (Field field : fieldsListWithAnnotation)
        {
            ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
            if (firstCol + annotation.col() == col)
            {
                return field;
            }
        }
        return null;
    }

    private <T extends BaseExcel> void updateRowField(T t, Class clazz, int row)
    {
        Field field = FieldUtils.getField(clazz,"_myRow",true);
        setFieldValue(t,field,row);
    }

    private Object getCellValue(XSSFCell cell, Field field)
    {
        Class<?> type = field.getType();
        try
        {
            if (type == String.class)
            {
                cell.setCellType(CellType.STRING);
                return cell.getStringCellValue();
            }
            else if (type == Integer.class)
            {
                return Integer.valueOf(getNumericTypesAsString(cell));
            }
            else if (type == Double.class)
            {
                return Double.valueOf(getNumericTypesAsString(cell));
            }
            else if (type == BigInteger.class)
            {
                return new BigInteger(getNumericTypesAsString(cell));
            }
            else if (type == Long.class)
            {
                return Long.valueOf(getNumericTypesAsString(cell));
            }
            else if (type == Boolean.class)
            {
                cell.setCellType(CellType.BOOLEAN);
                return cell.getBooleanCellValue();
            }
            else if(type == Date.class)
            {
                return tryToGetDateCellValue(cell,field);
            }
            else
            {
                cell.setCellType(CellType.STRING);
                return cell.getStringCellValue();
            }
        }
        catch (Exception e)
        {
            return null;
        }
    }

    private Date tryToGetDateCellValue(XSSFCell cell, Field field)
    {
        if(cell.getCellTypeEnum() == CellType.STRING)
        {
            try
            {
                ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                if(annotation != null && StringUtils.isNotBlank(annotation.dateFormat()))
                {
                    cell.setCellType(CellType.STRING);
                    String stringCellValue = cell.getStringCellValue();
                    DateTimeFormatter dtf = DateTimeFormat.forPattern(annotation.dateFormat());
                    DateTime dateTime = dtf.parseDateTime(stringCellValue);
                    return dateTime.toDate();
                }
            }
            catch (Exception e)
            {
                return null;
            }
        }
        else if(cell.getCellTypeEnum() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell))
        {
            try
            {
                return cell.getDateCellValue();
            }
            catch (Exception e)
            {
                return null;
            }
        }
        return null;
    }

    private String getNumericTypesAsString(XSSFCell cell)
    {
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue();
    }

}

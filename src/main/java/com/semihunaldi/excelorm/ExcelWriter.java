package com.semihunaldi.excelorm;

import com.semihunaldi.excelorm.annotations.Excel;
import com.semihunaldi.excelorm.annotations.ExcelColumn;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

public class ExcelWriter
{
    public <T extends BaseExcel> void write(File file, List<T> list, Class<T> clazz)
            throws FileNotFoundException, IOException
    {
        XSSFWorkbook workbook;
        FileInputStream fileInputStream=null;
        if (file.exists()) {
            fileInputStream = new FileInputStream(file);
            workbook = new XSSFWorkbook(fileInputStream);
        } else {
            workbook = new XSSFWorkbook();
        }

        Excel excelAnnotation = clazz.getAnnotation(Excel.class);
        // Clearing sheet specified by name
        String sheetName = new StringBuilder().append(excelAnnotation.name()).append(excelAnnotation.sheet()).toString();
        int sheetIndex = workbook.getSheetIndex(sheetName);
        if (sheetIndex !=-1) {
            workbook.removeSheetAt(sheetIndex);
        }
        // Creating new one
        XSSFSheet sheet = workbook.createSheet(sheetName);
        createHeader(sheet, excelAnnotation.firstRow(), clazz);
        TreeMap<Integer, Field> fieldsMap = getFieldsMap(clazz);
        for (T t : list) {
            XSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
            for (Integer integer : fieldsMap.keySet()) {
                XSSFCell cell = row.createCell(integer);
                setCellValue(cell, fieldsMap.get(integer), t);
            }
        }

        if (fileInputStream!=null) {
            fileInputStream.close();
        }

        FileOutputStream outFile =new FileOutputStream(file);
        workbook.write(outFile);
        outFile.close();
    }

    private TreeMap<Integer, Field> getFieldsMap(Class<?> clazz)
    {
        TreeMap<Integer, Field> fieldsMap = new TreeMap<>();
        List<Field> fieldsListWithAnnotation = FieldUtils.getFieldsListWithAnnotation(clazz, ExcelColumn.class);
        for (Field field : fieldsListWithAnnotation)
        {
            ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
            fieldsMap.put(annotation.col(), field);
        }
        return fieldsMap;
    }

    private void createHeader(XSSFSheet sheet, int firstRow, Class<?> clazz)
    {
        XSSFRow row = sheet.createRow(firstRow - 1);

        TreeMap<Integer, String> headerNames = getHeaderNames(clazz);
        for (Integer integer : headerNames.keySet())
        {
            XSSFCell cell = row.createCell(integer);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(headerNames.get(integer));
        }
    }

    private TreeMap<Integer, String> getHeaderNames(Class<?> clazz)
    {
        TreeMap<Integer, String> headerMap = new TreeMap<>();
        List<Field> fieldsListWithAnnotation = FieldUtils.getFieldsListWithAnnotation(clazz, ExcelColumn.class);
        for (Field field : fieldsListWithAnnotation)
        {
            ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
            headerMap.put(annotation.col(), annotation.columnName());
        }
        return headerMap;
    }

    private void clearSheet(XSSFWorkbook wb, int sheetNum)
    {
        XSSFSheet sheet = wb.getSheetAt(sheetNum);
        Iterator<Row> rowIte = sheet.iterator();
        while (rowIte.hasNext())
        {
            rowIte.next();
            rowIte.remove();
        }
    }

    private <T extends BaseExcel> void setCellValue(XSSFCell cell, Field field, T t)
    {
        Class<?> type = field.getType();
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        try
        {
            if (type == String.class)
            {
                cell.setCellType(CellType.STRING);
                String value = (String) field.get(t);
                cell.setCellValue(value);
            }
            else if (type == Integer.class)
            {
                cell.setCellType(CellType.NUMERIC);
                int value = (int) field.get(t);
                cell.setCellValue(value);
            }
            else if (type == Double.class)
            {
                cell.setCellType(CellType.NUMERIC);
                double value = (double) field.get(t);
                cell.setCellValue(value);
            }
            else if (type == BigInteger.class)
            {
                cell.setCellType(CellType.NUMERIC);
                BigInteger value = (BigInteger) field.get(t);
                cell.setCellValue(value.doubleValue());
            }
            else if (type == Long.class)
            {
                cell.setCellType(CellType.NUMERIC);
                long value = (long) field.get(t);
                cell.setCellValue(value);
            }
            else if (type == Boolean.class)
            {
                cell.setCellType(CellType.NUMERIC);
                boolean value = (boolean) field.get(t);
                cell.setCellValue(value);
            }
            else if (type == Date.class)
            {
                Date value = (Date) field.get(t);
                ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                if (annotation != null && StringUtils.isNotBlank(annotation.dateFormat()))
                {
                    try
                    {
                        cell.setCellType(CellType.STRING);
                        DateTime dateTime = new DateTime(value);
                        String dateString = dateTime.toString(annotation.dateFormat());
                        cell.setCellValue(dateString);
                    }
                    catch (Exception e1)
                    {
                        //swallow
                    }
                }
            }
            else
            {
                cell.setCellType(CellType.STRING);
                String value = (String) field.get(t);
                cell.setCellValue(value);
            }
        }
        catch (Exception e)
        {
            //swallow
        }
        field.setAccessible(accessible);
    }
}

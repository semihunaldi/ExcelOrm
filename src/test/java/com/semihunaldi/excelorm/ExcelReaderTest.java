package com.semihunaldi.excelorm;


import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class ExcelReaderTest extends BaseTest
{
    @Test
    public void testFile() throws Exception
    {
        ExcelReader excelReader = new ExcelReader();
        List<Task> taskList = excelReader.read(getTestFile("TaskExcel.xlsx"), Task.class);
        Assert.assertEquals(10,taskList.size());
    }

    @Test
    public void testInputStream() throws Exception
    {
        ExcelReader excelReader = new ExcelReader();
        File file = getTestFile("TaskExcel.xlsx");
        InputStream inputStream = new FileInputStream(file);
        List<Task> taskList = excelReader.read(inputStream,Task.class);
        Assert.assertEquals(10,taskList.size());
    }

    @Test
    public void testWorkbook() throws Exception
    {
        ExcelReader excelReader = new ExcelReader();
        File file = getTestFile("TaskExcel.xlsx");
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);
        List<Task> taskList = excelReader.read(xssfWorkbook,Task.class);
        Assert.assertEquals(10,taskList.size());
    }

    @Test
    public void testMultipleSheets() throws Exception
    {
        ExcelReader excelReader = new ExcelReader();
        List<Task> taskList = excelReader.read(getTestFile(), Task.class);
        List<Person> personList = excelReader.read(getTestFile(), Person.class);
        Assert.assertEquals(10,taskList.size());
        Assert.assertEquals(4, personList.size());
    }

    @Test
    public void testExcelDataWithMiddleOfTheExcel() throws Exception
    {
        ExcelReader excelReader = new ExcelReader();
        List<TaskExcelAtMiddle> taskList = excelReader.read(getTestFile("TaskExcel.xlsx"), TaskExcelAtMiddle.class);
        Assert.assertEquals(10,taskList.size());
    }

    @Test
    public void testExcelDate() throws Exception
    {
        ExcelReader excelReader = new ExcelReader();
        List<DateTask> taskList = excelReader.read(getTestFile("TaskExcel.xlsx"), DateTask.class);
        Assert.assertEquals(10,taskList.size());
    }
}

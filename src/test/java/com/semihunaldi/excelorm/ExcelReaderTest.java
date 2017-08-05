package com.semihunaldi.excelorm;


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
}

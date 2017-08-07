package com.semihunaldi.excelorm;

import org.junit.Test;

import java.io.File;
import java.util.List;

public class ExcelWriterTest extends BaseTest
{
    @Test
    public void testExisting() throws Exception
    {
        ExcelReader excelReader = new ExcelReader();
        List<Task> taskList = excelReader.read(getTestFile("TaskExcel.xlsx"), Task.class);
        taskList.remove(3);
        taskList.get(2).setAge(999);
        ExcelWriter excelWriter = new ExcelWriter();
        excelWriter.write(getTestFile("TaskExcel.xlsx"),taskList,Task.class);
    }

    @Test
    public void testNonExisting() throws Exception
    {
        ExcelReader excelReader = new ExcelReader();
        List<Task> taskList = excelReader.read(getTestFile("TaskExcel.xlsx"), Task.class);
        taskList.remove(3);
        taskList.get(2).setAge(999);
        ExcelWriter excelWriter = new ExcelWriter();
        excelWriter.write(new File("TaskExcel_new.xlsx"),taskList,Task.class);
    }
}

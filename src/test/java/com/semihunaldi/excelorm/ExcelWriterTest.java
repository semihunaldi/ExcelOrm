package com.semihunaldi.excelorm;

import org.junit.Test;

import java.util.List;

public class ExcelWriterTest extends BaseTest
{
    @Test
    public void testExisting() throws Exception
    {
        ExcelReader excelReader = new ExcelReader();
        List<Task> taskList = excelReader.read(getTestFile(), Task.class);
        taskList.remove(3);
        taskList.get(2).setAge(999);
        taskList.get(5).setAmount(9.99);
        ExcelWriter excelWriter = new ExcelWriter();
        excelWriter.write(getTestFile(),taskList,Task.class);
    }

    @Test
    public void testNonExisting() throws Exception
    {
        ExcelReader excelReader = new ExcelReader();
        List<Task> taskList = excelReader.read(getTestFile(), Task.class);
        taskList.remove(2);
        taskList.get(2).setAge(888);
        taskList.get(5).setAmount(88.9);
        ExcelWriter excelWriter = new ExcelWriter();
        excelWriter.write(getTestFile("TaskExcel_new.xlsx"),taskList,Task.class);
    }
}

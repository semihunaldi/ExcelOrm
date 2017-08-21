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
    public void testMultipleSheets() throws Exception
    {
        ExcelReader excelReader = new ExcelReader();
        List<Task> taskList = excelReader.read(getTestFile(), Task.class);
        List<Person> personList = excelReader.read(getTestFile(), Person.class);
        taskList.remove(2);
        personList.remove(2);
        ExcelWriter excelWriter = new ExcelWriter();
        excelWriter.write(getTestFile(),taskList,Task.class);
        excelWriter.write(getTestFile(),personList,Person.class);
    }

    @Test
    public void testExcelDataWithMiddleOfTheExcel() throws Exception
    {
        ExcelReader excelReader = new ExcelReader();
        List<TaskExcelAtMiddle> taskList = excelReader.read(getTestFile("TaskExcel.xlsx"), TaskExcelAtMiddle.class);
        taskList.remove(7);
        ExcelWriter excelWriter = new ExcelWriter();
        excelWriter.write(getTestFile(),taskList,TaskExcelAtMiddle.class);
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
        excelWriter.write(getTestFile(),taskList,Task.class);
    }

    @Test
    public void testExcelEnum() throws Exception
    {
        ExcelReader excelReader = new ExcelReader();
        List<EnumTaskTest> taskList = excelReader.read(getTestFile(), EnumTaskTest.class);
        for (EnumTaskTest enumTaskTest : taskList) {
            enumTaskTest.setStatus(Status.FINISHED);
        }
        taskList.get(0).setStatus(null);
        ExcelWriter excelWriter = new ExcelWriter();
        excelWriter.write(getTestFile(),taskList,EnumTaskTest.class);
    }

}

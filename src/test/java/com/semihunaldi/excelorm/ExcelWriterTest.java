package com.semihunaldi.excelorm;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ExcelWriterTest extends BaseTest
{
    public ExcelWriterTest()
    {
        copyFile();
    }

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

        List<Task> taskListReturned = excelReader.read(getTestFile(), Task.class);
        Assert.assertEquals(999,taskListReturned.get(2).getAge().intValue());
        Assert.assertEquals(9.99,taskListReturned.get(5).getAmount(),0);
        revertCopy();
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

        List<Task> taskListReturned = excelReader.read(getTestFile(), Task.class);
        List<Task> personListReturned = excelReader.read(getTestFile(), Task.class);
        Assert.assertEquals(taskListReturned.size(),taskList.size());
        Assert.assertEquals(personListReturned.size(),personListReturned.size());
        revertCopy();
    }

    @Test
    public void testExcelDataWithMiddleOfTheExcel() throws Exception
    {
        ExcelReader excelReader = new ExcelReader();
        List<TaskExcelAtMiddle> taskList = excelReader.read(getTestFile(), TaskExcelAtMiddle.class);
        taskList.remove(7);
        ExcelWriter excelWriter = new ExcelWriter();
        excelWriter.write(getTestFile(),taskList,TaskExcelAtMiddle.class);

        List<TaskExcelAtMiddle> taskListReturned = excelReader.read(getTestFile(), TaskExcelAtMiddle.class);
        Assert.assertEquals(taskList.size(),taskListReturned.size());
        revertCopy();
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

        List<Task> taskListReturned = excelReader.read(getTestFile(), Task.class);
        Assert.assertEquals(888,taskListReturned.get(2).getAge().intValue());
        Assert.assertEquals(88.9,taskListReturned.get(5).getAmount(),0);
        revertCopy();
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

        List<EnumTaskTest> taskListReturned = excelReader.read(getTestFile(), EnumTaskTest.class);
        Assert.assertEquals(null,taskListReturned.get(0).getStatus());
        Assert.assertEquals(Status.FINISHED,taskListReturned.get(1).getStatus());
        revertCopy();
    }


    @Test
    public void testWriteOnWorkbook() throws Exception
    {
        ExcelReader excelReader = new ExcelReader();
        List<Task> taskList = excelReader.read(getTestFile(), Task.class);
        ExcelWriter excelWriter = new ExcelWriter();
        XSSFWorkbook workbook = excelWriter.writeOnWorkbook(taskList, Task.class);
        XSSFSheet tasks = workbook.getSheet("Tasks");
        XSSFRow row = tasks.getRow(1);
        XSSFCell cell = row.getCell(0);
        Assert.assertEquals("test name1",cell.getStringCellValue());
        revertCopy();
    }

    @Test
    public void testWriteNullDate() throws Exception
    {
        List<Task> taskList = new ArrayList<>();
        Task task = new Task();
        task.setFirstName("qqq");
        task.setLastName("qqq");
        task.setAge(1);
        task.setDate(null);
        task.setDescription("asd");
        task.setAmount(22d);
        taskList.add(task);
        ExcelWriter excelWriter = new ExcelWriter();
        excelWriter.write(getTestFile(),taskList,Task.class);

        ExcelReader excelReader = new ExcelReader();
        List<Task> returnedTaskList = excelReader.read(getTestFile(), Task.class);
        Assert.assertEquals(null,returnedTaskList.get(0).getDate());
        revertCopy();
    }
}

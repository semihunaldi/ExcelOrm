package com.semihunaldi.excelorm;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class ExcelWriterTest extends BaseTest
{


    @Test
    public void testExisting() throws Exception
    {
        testNonExisting();
        File xlsFile = getTestFileAtCWD();
        ExcelReader excelReader = new ExcelReader();
        List<Task> taskList = excelReader.read(xlsFile, Task.class);
        taskList.remove(3);
        taskList.get(2).setAge(999);
        ExcelWriter excelWriter = new ExcelWriter();
        if (xlsFile.exists()) {
            xlsFile.delete();
        }
        excelWriter.write(xlsFile,taskList,Task.class);
    }

    @Test
    public void testNonExisting() throws Exception
    {
        int listSize = 10;
        List<Task> taskList = getTaskList(listSize);
        File xlsFile = getTestFileAtCWD();
        if (xlsFile.exists()) {
            xlsFile.delete();
        }
        ExcelWriter excelWriter = new ExcelWriter();
        excelWriter.write(xlsFile,taskList,Task.class);
        ExcelReader excelReader = new ExcelReader();
        taskList = excelReader.read(xlsFile, Task.class);
        Assert.assertEquals(listSize,taskList.size());
    }
}

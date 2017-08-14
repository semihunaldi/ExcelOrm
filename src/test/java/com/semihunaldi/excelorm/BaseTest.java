package com.semihunaldi.excelorm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class BaseTest
{
    private final String fileName = "TaskExcel.xlsx";

    File getTestFile()
    {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(fileName).getFile());
    }

    File getTestFileAtCWD() throws IOException {
        String fileFullPath = new java.io.File( "." ).getCanonicalPath()+"/"+fileName;
        return new File(fileFullPath);
    }

    List<Task> getTaskList(int size){
        List<Task> taskList = new ArrayList<>();

        for (int i=0; i<size; i++) {
            Task task = new Task();
            task.setAge(i);
            task.setAmount(new Double(i+i));
            task.setDate(new Date());
            task.setDescription(String.format("%dth Description", i));
            task.setFirstName(String.format("FirstName#%d", i));
            task.setLastName(String.format("LastName#%d", i));
            taskList.add(task);
        }
        return taskList;
    }
}

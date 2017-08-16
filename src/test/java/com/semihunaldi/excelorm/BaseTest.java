package com.semihunaldi.excelorm;

import java.io.File;

public abstract class BaseTest
{
    private static String fileName = "TaskExcel.xlsx";

    File getTestFile()
    {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(fileName).getFile());
    }

    File getTestFile(String newFileName)
    {
        File testFile = getTestFile();
        return new File(testFile.getParent(),newFileName);
    }
}

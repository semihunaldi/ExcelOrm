package com.semihunaldi.excelorm;

import java.io.File;

public abstract class BaseTest
{
    File getTestFile(String fileName)
    {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(fileName).getFile());
    }
}

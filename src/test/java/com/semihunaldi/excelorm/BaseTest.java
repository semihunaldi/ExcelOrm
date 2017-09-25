package com.semihunaldi.excelorm;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

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

    void copyFile() {
        File testFile = getTestFile();
        File copy = new File(testFile.getParentFile().getAbsolutePath().concat("/").concat("___").concat(testFile.getName()));
        Path path = Paths.get(testFile.getAbsolutePath());
        Path copyPath = Paths.get(copy.getAbsolutePath());
        try {
            Files.copy(path,copyPath,StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("e");
        }
    }

    void revertCopy() {
        File testFile = getTestFile();
        File copy = new File(testFile.getParentFile().getAbsolutePath().concat("/").concat("___").concat(testFile.getName()));
        Path path = Paths.get(testFile.getAbsolutePath());
        Path copyPath = Paths.get(copy.getAbsolutePath());
        try {
            Files.copy(copyPath,path,StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("b");
        }
    }
}

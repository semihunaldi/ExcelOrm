package com.semihunaldi.excelorm;

import com.semihunaldi.excelorm.annotations.Excel;
import com.semihunaldi.excelorm.exceptions.IllegalExcelArgumentException;
import org.apache.commons.lang3.StringUtils;

public class Validator
{
    public static void validate(Excel excelAnnotation)
    {
        if(StringUtils.isBlank(excelAnnotation.sheetName()))
        {
            throw new IllegalExcelArgumentException("sheetName can not be empty");
        }
        if(excelAnnotation.firstRow() < 1)
        {
            throw new IllegalExcelArgumentException("firstRow can not be less than 1");
        }
    }
}

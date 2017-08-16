package com.semihunaldi.excelorm;

import com.semihunaldi.excelorm.annotations.Excel;
import com.semihunaldi.excelorm.annotations.ExcelColumn;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Excel(firstRow = 1, firstCol = 0, sheetName = "People")
@Data
public class Person extends BaseExcel
{
    @ExcelColumn(col = 0 , columnName = "Name")
    private String name;

    @ExcelColumn(col = 1 , columnName = "Surname")
    private String surName;
}

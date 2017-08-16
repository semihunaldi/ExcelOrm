package com.semihunaldi.excelorm;

import com.semihunaldi.excelorm.annotations.Excel;
import com.semihunaldi.excelorm.annotations.ExcelColumn;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Excel(firstRow = 3, firstCol = 4, sheetName = "TaskMiddle")
@Data
public class TaskExcelAtMiddle extends BaseExcel
{
    @ExcelColumn(col = 0 , columnName = "Name")
    private String firstName;

    @ExcelColumn(col = 1 , columnName = "Last Name")
    private String lastName;

    @ExcelColumn(col = 2 , columnName = "Age")
    private Integer age;

    @ExcelColumn(col = 3 , columnName = "Amount")
    private Double amount;

    @ExcelColumn(col = 4 , columnName = "Description")
    private String description;

    @ExcelColumn(col = 5, columnName = "Date", dateFormat = "dd/MM/yyyy HH:mm")
    private Date date;
}

package com.semihunaldi.excelorm;

import com.semihunaldi.excelorm.annotations.Excel;
import com.semihunaldi.excelorm.annotations.ExcelColumn;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Excel(firstRow = 1,sheet = 0, firstCol = 0)
@Data
public class Task extends BaseExcel
{
    @ExcelColumn(col = 0)
    private String firstName;

    @ExcelColumn(col = 1)
    private String lastName;

    @ExcelColumn(col = 2)
    private Integer age;

    @ExcelColumn(col = 3)
    private Double amount;

    @ExcelColumn(col = 4)
    private String description;
}

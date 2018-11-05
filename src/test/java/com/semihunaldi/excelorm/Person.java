package com.semihunaldi.excelorm;

import com.semihunaldi.excelorm.annotations.Excel;
import com.semihunaldi.excelorm.annotations.ExcelColumn;

@Excel(firstRow = 1, firstCol = 0, sheetName = "People")
public class Person extends BaseExcel {

	@ExcelColumn(col = 0, columnName = "Name")
	private String name;

	@ExcelColumn(col = 1, columnName = "Surname")
	private String surName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}

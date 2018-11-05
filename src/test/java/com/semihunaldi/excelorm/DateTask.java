package com.semihunaldi.excelorm;

import com.semihunaldi.excelorm.annotations.Excel;
import com.semihunaldi.excelorm.annotations.ExcelColumn;

import java.util.Date;

@Excel(firstRow = 1, firstCol = 0, sheetName = "DateTask")
public class DateTask extends BaseExcel {

	@ExcelColumn(col = 0, columnName = "Name")
	private String firstName;

	@ExcelColumn(col = 1, columnName = "Last Name")
	private String lastName;

	@ExcelColumn(col = 2, columnName = "Age")
	private Integer age;

	@ExcelColumn(col = 3, columnName = "Amount")
	private Double amount;

	@ExcelColumn(col = 4, columnName = "Description")
	private String description;

	@ExcelColumn(col = 5, columnName = "Date", dateFormat = "dd/MM/yyyy HH:mm")
	private Date date;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

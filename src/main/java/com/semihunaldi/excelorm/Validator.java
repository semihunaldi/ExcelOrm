package com.semihunaldi.excelorm;

import com.semihunaldi.excelorm.annotations.Excel;
import com.semihunaldi.excelorm.exceptions.IllegalExcelArgumentException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Validator {

	public static void validate(Excel excelAnnotation) throws IllegalExcelArgumentException {
		if(StringUtils.isBlank(excelAnnotation.sheetName())){
			throw new IllegalExcelArgumentException("sheetName can not be empty");
		}
		if(excelAnnotation.firstRow() < 1){
			throw new IllegalExcelArgumentException("firstRow can not be less than 1");
		}
	}

	public static void validate(XSSFWorkbook xssfWorkbook) throws IllegalExcelArgumentException {
		if(xssfWorkbook == null){
			throw new IllegalExcelArgumentException("xssfWorkbook can not bu null");
		}
	}
}

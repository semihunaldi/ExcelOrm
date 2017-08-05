package com.semihunaldi.excelorm;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseExcel implements Serializable
{
    private int _myRow;
}

package com.semihunaldi.excelorm;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(of = "_myRow")
public class BaseExcel implements Serializable
{
    private int _myRow;
    private int _myOldRow;
}

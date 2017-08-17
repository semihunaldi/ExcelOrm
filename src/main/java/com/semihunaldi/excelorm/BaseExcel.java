package com.semihunaldi.excelorm;


import java.io.Serializable;

public class BaseExcel implements Serializable
{
    private int _myRow;
    private int _myOldRow;

    public int get_myRow()
    {
        return _myRow;
    }

    public void set_myRow(int _myRow)
    {
        this._myRow = _myRow;
    }

    public int get_myOldRow()
    {
        return _myOldRow;
    }

    public void set_myOldRow(int _myOldRow)
    {
        this._myOldRow = _myOldRow;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseExcel baseExcel = (BaseExcel) o;

        return _myRow == baseExcel._myRow;
    }

    @Override
    public int hashCode()
    {
        return _myRow;
    }
}

package com.hashmapinc.tempus.model;

public enum UnitSetCell {

    UNIT(0),
    NAME(1),
    BASE_UNIT(2),
    A(3),
    B(4),
    C(5),
    D(6);

    private int cellNo;

    UnitSetCell(int cellNo) {
        this.cellNo = cellNo;
    }

    public int getCellNo() {
        return cellNo;
    }
}

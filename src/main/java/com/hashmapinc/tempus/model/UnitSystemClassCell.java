package com.hashmapinc.tempus.model;

public enum UnitSystemClassCell {
    BASE(4),
    ENGLISH(5),
    METRIC(6),
    CANADIAN(7);

    private int cellNo;

    UnitSystemClassCell(int cellNo) {
        this.cellNo = cellNo;
    }

    public int getCellNo() {
        return cellNo;
    }
}

package com.hashmapinc.tempus.model;

public enum QuantityClassCell {

    CLASS_NAME(0),
    CLASS_UNIT(1);

    private int cellNo;

    QuantityClassCell(int cellNo) {
        this.cellNo = cellNo;
    }

    public int getCellNo() {
        return cellNo;
    }
}

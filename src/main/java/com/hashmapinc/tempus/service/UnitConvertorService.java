package com.hashmapinc.tempus.service;

import com.hashmapinc.tempus.exception.UnitConvertorException;
import com.hashmapinc.tempus.model.Quantity;

public interface UnitConvertorService {

    void initializeUnitSetMap() throws UnitConvertorException;

    Quantity convertToSiUnit(Quantity quantity)throws UnitConvertorException;

    Quantity convertToTargetUnit(Quantity quantity, String unit) throws UnitConvertorException;

    String getBaseUnit(String unit);
}

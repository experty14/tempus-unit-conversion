package com.hashmapinc.tempus.service;

import com.hashmapinc.tempus.exception.UnitSystemSetException;

public interface UnitSystemService {
    String getUnitFor(UnitSystem unitSystem, String sourceUnit) throws UnitSystemSetException;
}

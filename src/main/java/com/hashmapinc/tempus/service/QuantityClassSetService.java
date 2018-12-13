package com.hashmapinc.tempus.service;

import com.hashmapinc.tempus.exception.QuantityClassSetException;

import java.util.Map;
import java.util.Set;

public interface QuantityClassSetService {

    void initializeQuantityClassMap() throws QuantityClassSetException;

    Set<String> getAllQuantityClass() throws QuantityClassSetException;

    Map<String, Set<String>> getMemberUnitsForAllQuantityClass() throws QuantityClassSetException;

    Set<String> getMemberUnitsForQuantityClass(String quantityClass) throws QuantityClassSetException;
}

package com.hashmapinc.tempus.service;

import com.hashmapinc.tempus.UnitConvertorContext;
import com.hashmapinc.tempus.exception.QuantityClassSetException;
import com.hashmapinc.tempus.exception.UnitConvertorContextException;
import com.hashmapinc.tempus.service.QuantityClassSetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

public class QuantityClassSetServiceTest {

    private QuantityClassSetService quantityClassSetService;

    @BeforeEach
    public void setUp() throws UnitConvertorContextException {
        quantityClassSetService = UnitConvertorContext.getInstanceOfQuantityClassSetService();
    }

    @Test
    public void createQuantityClassSetService() {
        Assertions.assertNotNull(quantityClassSetService);
    }

    @Test
    public void getAllQuantityClass() throws QuantityClassSetException {
        Set quantitySetClasses = quantityClassSetService.getAllQuantityClass();
        Assertions.assertNotNull(quantitySetClasses);
        Assertions.assertEquals(212, quantitySetClasses.size());
    }

    @Test
    public void getMemberUnitsForQuantityClassForLength() throws QuantityClassSetException{
        Set lengths = quantityClassSetService.getMemberUnitsForQuantityClass("length");
        Assertions.assertNotNull(lengths);
        Assertions.assertEquals(83, lengths.size());
    }

    @Test
    public void getMemberUnitsForQuantityClassForMass() throws QuantityClassSetException {
        Set masses = quantityClassSetService.getMemberUnitsForQuantityClass("mass");
        Assertions.assertNotNull(masses);
        Assertions.assertEquals(26, masses.size());
    }

    @Test
    public void getMemberUnitsForQuantityClassForRanadomClassShouldThrowException() throws QuantityClassSetException {
        Assertions.assertThrows(QuantityClassSetException.class, () -> {
            quantityClassSetService.getMemberUnitsForQuantityClass("random");
        });
    }

    @Test
    public void  getMemberUnitsForAllQuantityClass() throws QuantityClassSetException {
        Map quantityClassMap = quantityClassSetService.getMemberUnitsForAllQuantityClass();
        Assertions.assertNotNull(quantityClassMap);
        Assertions.assertEquals(212, quantityClassMap.size());
    }
}

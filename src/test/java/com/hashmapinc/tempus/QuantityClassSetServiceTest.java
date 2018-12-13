package com.hashmapinc.tempus;

import com.hashmapinc.tempus.exception.QuantityClassSetException;
import com.hashmapinc.tempus.exception.UnitConvertorContextException;
import com.hashmapinc.tempus.service.QuantityClassSetService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

public class QuantityClassSetServiceTest {

    private QuantityClassSetService quantityClassSetService;

    @Before
    public void setUp() throws UnitConvertorContextException {
        quantityClassSetService = UnitConvertorContext.getInstanceOfQuantityClassSetService();
    }

    @Test
    public void createQuantityClassSetService() {
        Assert.assertNotNull(quantityClassSetService);
    }

    @Test
    public void getAllQuantityClass() throws QuantityClassSetException {
        Set quantitySetClasses = quantityClassSetService.getAllQuantityClass();
        Assert.assertNotNull(quantitySetClasses);
        Assert.assertEquals(175, quantitySetClasses.size());
    }

    @Test
    public void getMemberUnitsForQuantityClassForLength() throws QuantityClassSetException{
        Set lengths = quantityClassSetService.getMemberUnitsForQuantityClass("length");
        Assert.assertNotNull(lengths);
        Assert.assertEquals(83, lengths.size());
    }

    @Test
    public void getMemberUnitsForQuantityClassForMass() throws QuantityClassSetException {
        Set masses = quantityClassSetService.getMemberUnitsForQuantityClass("mass");
        Assert.assertNotNull(masses);
        Assert.assertEquals(26, masses.size());
    }

    @Test(expected = QuantityClassSetException.class)
    public void getMemberUnitsForQuantityClassForRanadomClassShouldThrowException() throws QuantityClassSetException {
        quantityClassSetService.getMemberUnitsForQuantityClass("random");
    }

    @Test
    public void  getMemberUnitsForAllQuantityClass() throws QuantityClassSetException {
        Map quantityClassMap = quantityClassSetService.getMemberUnitsForAllQuantityClass();
        Assert.assertNotNull(quantityClassMap);
        Assert.assertEquals(175, quantityClassMap.size());
    }
}

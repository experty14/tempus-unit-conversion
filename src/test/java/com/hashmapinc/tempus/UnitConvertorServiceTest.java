package com.hashmapinc.tempus;

import com.hashmapinc.tempus.exception.UnitConvertorContextException;
import com.hashmapinc.tempus.exception.UnitConvertorException;
import com.hashmapinc.tempus.model.Quantity;
import com.hashmapinc.tempus.service.UnitConvertorService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UnitConvertorServiceTest {

    private UnitConvertorService unitConvertorService;
    private String testUnit = "testUnit";

    @Before
    public void setUp() throws UnitConvertorContextException {
        unitConvertorService = UnitConvertorContext.getInstanceOfUnitConvertorService();
    }

    @Test
    public void createUnitConvertorService() {
        Assert.assertNotNull(unitConvertorService);
    }

    @Test
    public void convertToSiUnitMeterShouldReturnMeter() throws UnitConvertorException {
        Quantity quantity = new Quantity(10.0, "m");
        Quantity siQuantity = unitConvertorService.convertToSiUnit(quantity);
        Assert.assertNotNull(siQuantity);
        Assert.assertEquals(quantity, siQuantity);
    }

    @Test
    public void convertToSiUnitKiloMeter() throws UnitConvertorException {
        Quantity quantity = new Quantity(10.0, "km");
        Quantity siQuantity = unitConvertorService.convertToSiUnit(quantity);
        Assert.assertNotNull(siQuantity);

        Assert.assertEquals(Double.valueOf(10000.0), siQuantity.getValue());
    }

    @Test
    public void convertToTargetUnit() throws UnitConvertorException {
        Quantity quantity = new Quantity(10.0, "m");
        Quantity targetQuantity = unitConvertorService.convertToTargetUnit(quantity, "km");
        Assert.assertNotNull(targetQuantity);
        Assert.assertEquals(Double.valueOf(0.01), targetQuantity.getValue());
    }

    @Test
    public void convertToTargetUnitMeterShouldReturnMeter() throws UnitConvertorException {
        Quantity quantity = new Quantity(10.0, "m");
        Quantity targetQuantity = unitConvertorService.convertToTargetUnit(quantity, "m");
        Assert.assertNotNull(targetQuantity);
        Assert.assertEquals(quantity, targetQuantity);
    }

    @Test
    public void convertToTargetUnitKiloMeterShouldReturnFeet() throws UnitConvertorException {
        Quantity quantity = new Quantity(2.5, "km");
        Quantity targetQuantity = unitConvertorService.convertToTargetUnit(quantity, "ft");
        Assert.assertNotNull(targetQuantity);
        Assert.assertEquals(Double.valueOf(8202.099737532808), targetQuantity.getValue());
        Assert.assertEquals("ft", targetQuantity.getUnit());
    }

    @Test(expected = UnitConvertorException.class)
    public void convertQuantityToSiUnitThrowException() throws UnitConvertorException {
        Quantity quantity = new Quantity(10.0, testUnit);
        unitConvertorService.convertToSiUnit(quantity);
    }

    @Test(expected = UnitConvertorException.class)
    public void convertToTargetUnitWithBothInvalidUnitsThrowException() throws UnitConvertorException {
        Quantity quantity = new Quantity(2.5 , testUnit);
        unitConvertorService.convertToTargetUnit(quantity , "testUnit1");
    }

    @Test(expected = UnitConvertorException.class)
    public void convertToTargetUnitWithTagetAsInvalidUnitThrowException() throws UnitConvertorException {
        Quantity quantity = new Quantity(10.0 , "m");
        unitConvertorService.convertToTargetUnit(quantity , testUnit);
    }

    @Test(expected = UnitConvertorException.class)
    public void convertToTargetUnitKilometerToKilogramShouldThrowException() throws UnitConvertorException {
        Quantity quantity = new Quantity(2.5, "km");
        unitConvertorService.convertToTargetUnit(quantity, "kg");
    }
}

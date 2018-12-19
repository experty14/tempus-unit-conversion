package com.hashmapinc.tempus;

import com.hashmapinc.tempus.exception.UnitConvertorContextException;
import com.hashmapinc.tempus.exception.UnitConvertorException;
import com.hashmapinc.tempus.model.Quantity;
import com.hashmapinc.tempus.service.UnitConvertorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UnitConvertorServiceTest {

    private UnitConvertorService unitConvertorService;
    private String testUnit = "testUnit";

    @BeforeAll
    public void setUp() throws UnitConvertorContextException {
        unitConvertorService = UnitConvertorContext.getInstanceOfUnitConvertorService();
    }

    @Test
    public void createUnitConvertorService() {
        Assertions.assertNotNull(unitConvertorService);
    }

    @Test
    public void convertToSiUnitMeterShouldReturnMeter() throws UnitConvertorException {
        Quantity quantity = new Quantity(10.0, "m");
        Quantity siQuantity = unitConvertorService.convertToSiUnit(quantity);
        Assertions.assertNotNull(siQuantity);
        Assertions.assertEquals(quantity, siQuantity);
    }

    @Test
    public void convertToSiUnitKiloMeter() throws UnitConvertorException {
        Quantity quantity = new Quantity(10.0, "km");
        Quantity siQuantity = unitConvertorService.convertToSiUnit(quantity);
        Assertions.assertNotNull(siQuantity);

        Assertions.assertEquals(Double.valueOf(10000.0), siQuantity.getValue());
    }

    @Test
    public void convertToTargetUnit() throws UnitConvertorException {
        Quantity quantity = new Quantity(10.0, "m");
        Quantity targetQuantity = unitConvertorService.convertToTargetUnit(quantity, "km");
        Assertions.assertNotNull(targetQuantity);
        Assertions.assertEquals(Double.valueOf(0.01), targetQuantity.getValue());
    }

    @Test
    public void convertToTargetUnitMeterShouldReturnMeter() throws UnitConvertorException {
        Quantity quantity = new Quantity(10.0, "m");
        Quantity targetQuantity = unitConvertorService.convertToTargetUnit(quantity, "m");
        Assertions.assertNotNull(targetQuantity);
        Assertions.assertEquals(quantity, targetQuantity);
    }

    @Test
    public void convertToTargetUnitKiloMeterShouldReturnFeet() throws UnitConvertorException {
        Quantity quantity = new Quantity(2.5, "km");
        Quantity targetQuantity = unitConvertorService.convertToTargetUnit(quantity, "ft");
        Assertions.assertNotNull(targetQuantity);
        Assertions.assertEquals(Double.valueOf(8202.099737532808), targetQuantity.getValue());
        Assertions.assertEquals("ft", targetQuantity.getUnit());
    }

    @Test
    public void convertQuantityToSiUnitThrowException() throws UnitConvertorException {
        Quantity quantity = new Quantity(10.0, testUnit);
        unitConvertorService.convertToSiUnit(quantity);
        Assertions.assertThrows(UnitConvertorException.class, () -> {
            unitConvertorService.convertToSiUnit(quantity);
        });
    }

    @Test
    public void convertToTargetUnitWithBothInvalidUnitsThrowException() throws UnitConvertorException {
        Quantity quantity = new Quantity(2.5 , testUnit);
        Assertions.assertThrows(UnitConvertorException.class, () -> {
            unitConvertorService.convertToTargetUnit(quantity , "testUnit1");
        });
    }

    @Test
    public void convertToTargetUnitWithTagetAsInvalidUnitThrowException() throws UnitConvertorException {
        Quantity quantity = new Quantity(10.0 , "m");
        Assertions.assertThrows(UnitConvertorException.class, () -> {
            unitConvertorService.convertToTargetUnit(quantity , testUnit);
        });
    }

    @Test
    public void convertToTargetUnitKilometerToKilogramShouldThrowException() throws UnitConvertorException {
        Quantity quantity = new Quantity(2.5, "km");
        Assertions.assertThrows(UnitConvertorException.class, () -> {
            unitConvertorService.convertToTargetUnit(quantity, "kg");
        });
    }
}

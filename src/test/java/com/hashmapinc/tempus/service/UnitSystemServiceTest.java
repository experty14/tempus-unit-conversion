package com.hashmapinc.tempus.service;

import com.hashmapinc.tempus.UnitConvertorContext;
import com.hashmapinc.tempus.exception.UnitConvertorContextException;
import com.hashmapinc.tempus.exception.UnitSystemSetException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnitSystemServiceTest {

    private UnitSystemService unitSystemService;

    @BeforeEach
    public void setUp() throws UnitConvertorContextException {
        unitSystemService = UnitConvertorContext.getInstanceOfUnitSystemService();
    }

    @Test
    public void createUnitSystemService() {
        Assertions.assertNotNull(unitSystemService);
    }

    @Test
    public void getAllQuantityClass() throws UnitSystemSetException {
        assertEquals("lbm/(ft2.s)", unitSystemService.getUnitFor(UnitSystem.ENGLISH, "kg/(m2.s)"));
    }

    @Test
    public void shouldThrowExceptionForBaseUnitAsSource() {
        Assertions.assertThrows(UnitSystemSetException.class, () -> unitSystemService.getUnitFor(UnitSystem.ENGLISH, "B"));
    }


}
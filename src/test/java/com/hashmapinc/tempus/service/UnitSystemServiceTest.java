package com.hashmapinc.tempus.service;

import com.hashmapinc.tempus.UnitConvertorContext;
import com.hashmapinc.tempus.exception.QuantityClassSetException;
import com.hashmapinc.tempus.exception.UnitConvertorContextException;
import com.hashmapinc.tempus.exception.UnitSystemSetException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals("f2", unitSystemService.getUnitFor(UnitSystem.ENGLISH, "m2"));
    }

}
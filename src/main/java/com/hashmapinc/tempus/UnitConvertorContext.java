package com.hashmapinc.tempus;

import com.hashmapinc.tempus.exception.QuantityClassSetException;
import com.hashmapinc.tempus.exception.UnitConvertorContextException;
import com.hashmapinc.tempus.exception.UnitConvertorException;
import com.hashmapinc.tempus.exception.UnitSystemSetException;
import com.hashmapinc.tempus.service.*;

public class UnitConvertorContext {

    private static UnitConvertorService unitConvertorService;

    private static QuantityClassSetService quantityClassSetService;
    private static UnitSystemService unitSystemService;

    private static String workbookFile = "Unit_Of_Measurement.xlsx";
    private static String quantityClassSheet = "UOM-memberUnitSet";
    private static String unitSystemSheet = "UOM-unitSystem";
    private static String unitSetSheet = "UOM-unitSet";

    private UnitConvertorContext() {
    }

    public static UnitConvertorService getInstanceOfUnitConvertorService() throws UnitConvertorContextException {
        try {
            if (unitConvertorService == null) {
                unitConvertorService = new UnitConvertorServiceImpl(workbookFile, unitSetSheet);
                unitConvertorService.initializeUnitSetMap();
            }
            return unitConvertorService;
        } catch (UnitConvertorException e) {
            throw new UnitConvertorContextException(e.getMessage(), e);
        }
    }

    public static QuantityClassSetService getInstanceOfQuantityClassSetService() throws UnitConvertorContextException {
        try {
            if (quantityClassSetService == null) {
                quantityClassSetService =  new QuantityClassSetServiceImpl(workbookFile, quantityClassSheet);
                quantityClassSetService.initializeQuantityClassMap();
            }
            return quantityClassSetService;
        } catch (QuantityClassSetException e) {
            throw new UnitConvertorContextException(e.getMessage(), e);
        }
    }

    public static UnitSystemService getInstanceOfUnitSystemService() throws UnitConvertorContextException {
        try {
            if (unitSystemService == null) {
                unitSystemService =  new UnitSystemServiceImpl(workbookFile, unitSystemSheet);
                ((UnitSystemServiceImpl) unitSystemService).initialize();
            }
            return unitSystemService;
        } catch (UnitSystemSetException e) {
            throw new UnitConvertorContextException(e.getMessage(), e);
        }
    }
}

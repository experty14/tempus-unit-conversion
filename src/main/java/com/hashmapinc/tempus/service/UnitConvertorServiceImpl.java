package com.hashmapinc.tempus.service;

import com.hashmapinc.tempus.exception.UnitConvertorException;
import com.hashmapinc.tempus.model.Quantity;
import com.hashmapinc.tempus.model.UnitConstants;
import com.hashmapinc.tempus.model.UnitSet;
import com.hashmapinc.tempus.model.UnitSetCell;
import com.hashmapinc.tempus.util.XlsReaderUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UnitConvertorServiceImpl implements UnitConvertorService {

    private Map<String, UnitSet> unitSetMap;

    private XlsReaderUtil reader;
    private String workbookFile;
    private String sheetName;

    private static final String IS_BASE = "IS-BASE";
    private static final String UNIT_NOT_SUPPORTED_MSG = "Provided unit is not supported in library";


    public UnitConvertorServiceImpl(String workbookFile, String sheetName) {
        reader = new XlsReaderUtil();
        this.workbookFile = workbookFile;
        this.sheetName = sheetName;
    }

    public void initializeUnitSetMap() throws UnitConvertorException {
        unitSetMap = new HashMap<>();

        try (Workbook workbook = reader.getWorkbook(workbookFile)) {
            Sheet sheet = reader.getSheetFromWorkbook(workbook , sheetName);

            sheet.forEach(row -> {
                if (row.getRowNum() != 0) {
                    String unit = reader.getStringValueFromRow(row, UnitSetCell.UNIT.getCellNo());
                    UnitSet unitSet = createUnitSet(row, unit);
                    unitSetMap.put(unit , unitSet);
                }
            });
        } catch (IOException | InvalidFormatException e) {
            throw new UnitConvertorException(e.getMessage(), e);
        }
    }

    @Override
    public Quantity convertToSiUnit(Quantity quantity) throws UnitConvertorException {
        UnitSet unitSet = unitSetMap.get(quantity.getUnit());
        if (unitSet != null && unitSet.getBaseUnit().equals(IS_BASE)) {
            return quantity;
        } else if (unitSet == null) {
            throw new UnitConvertorException(UNIT_NOT_SUPPORTED_MSG);
        }
        return convertQuantityToSiUnit(quantity.getValue() , unitSet);
    }

    @Override
    public Quantity convertToTargetUnit(Quantity quantity , String unit) throws UnitConvertorException {
        UnitSet givenUnitSet = unitSetMap.get(quantity.getUnit());
        UnitSet targetUnitSet = unitSetMap.get(unit);

        if (givenUnitSet != null && targetUnitSet != null) {
            if (givenUnitSet.equals(targetUnitSet)) {
                return quantity;
            } else if (givenUnitSet.getBaseUnit().equals(IS_BASE) && targetUnitSet.getBaseUnit().equals(givenUnitSet.getUnit())) {
                return convertSiToTargetUnit(quantity, targetUnitSet);
            } else if (targetUnitSet.getBaseUnit().equals(IS_BASE) && givenUnitSet.getBaseUnit().equals(targetUnitSet.getUnit())) {
                return convertToSiUnit(quantity);
            } else if (givenUnitSet.getBaseUnit().equals(targetUnitSet.getBaseUnit())) {
                Quantity siQuantity = convertToSiUnit(quantity);
                return convertSiToTargetUnit(siQuantity , targetUnitSet);
            } else {
                throw new UnitConvertorException("Unit Conversion is not possible, as provided two units belong to two different qunatity class");
            }
        } else {
            throw new UnitConvertorException(UNIT_NOT_SUPPORTED_MSG);
        }
    }

    private Quantity convertSiToTargetUnit(Quantity quantity , UnitSet unitSet) {
        return convertQuantityToTargetUnit(quantity.getValue() , unitSet);
    }

    private Quantity convertQuantityToSiUnit(Double value , UnitSet unitSet) {
        Double siValue = applyFormulaForSiUnit(value, unitSet.getConstants());
        return new Quantity(siValue, unitSet.getBaseUnit());
    }

    private Double applyFormulaForSiUnit(Double value, UnitConstants constants) {
        return (constants.getA() + (constants.getB() * value)) / (constants.getC() + (constants.getD() * value));
    }

    private Quantity convertQuantityToTargetUnit(Double value , UnitSet unitSet) {
        Double targetValue = applyFormulaForTargetUnit(value, unitSet.getConstants());
        return new Quantity(targetValue, unitSet.getUnit());
    }

    private Double applyFormulaForTargetUnit(Double value , UnitConstants constants) {
        return (constants.getA() - (constants.getC() * value)) / ((constants.getD() * value) - constants.getB());
    }

    private UnitSet createUnitSet(Row row, String unit) {
        String name = reader.getStringValueFromRow(row, UnitSetCell.NAME.getCellNo());
        String baseUnit = reader.getStringValueFromRow(row, UnitSetCell.BASE_UNIT.getCellNo());
        UnitConstants unitConstants = createUnitConstant(row);
        return new UnitSet(unit , name , baseUnit , unitConstants);
    }

    private UnitConstants createUnitConstant(Row row) {
        Double a = reader.getDoubleValueFromRow(row, UnitSetCell.A.getCellNo());
        Double b = reader.getDoubleValueFromRow(row, UnitSetCell.B.getCellNo());
        Double c = reader.getDoubleValueFromRow(row, UnitSetCell.C.getCellNo());
        Double d = reader.getDoubleValueFromRow(row, UnitSetCell.D.getCellNo());
        return new UnitConstants(a , b , c , d);
    }
}

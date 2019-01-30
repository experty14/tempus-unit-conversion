package com.hashmapinc.tempus.service;

import com.hashmapinc.tempus.exception.UnitSystemSetException;
import com.hashmapinc.tempus.model.UnitSystemClassCell;
import com.hashmapinc.tempus.util.XlsReaderUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.util.*;

public class UnitSystemServiceImpl implements UnitSystemService {

    private Map<String, String> unitAndQuantityClass;
    private Map<String, Map<UnitSystem, String>> quantityClassAndBaseUnit;


    private XlsReaderUtil reader;
    private String workbookFile;
    private String sheetName;

    public UnitSystemServiceImpl(String workbookFile, String sheetName) {
        reader = new XlsReaderUtil();
        this.workbookFile = workbookFile;
        this.sheetName = sheetName;
    }

    public void initialize() throws UnitSystemSetException {

        unitAndQuantityClass = new HashMap<>();
        quantityClassAndBaseUnit = new HashMap<>();

        try (Workbook workbook = reader.getWorkbook(workbookFile)) {
            Sheet sheet = reader.getSheetFromWorkbook(workbook , sheetName);

            sheet.forEach(row -> {
                if (row.getRowNum() != 0) {
                    String quantityClass = reader.getStringValueFromRow(row , UnitSystemClassCell.QUANTITY_CLASS.getCellNo()).trim();
                    String englishUnit = reader.getStringValueFromRow(row , UnitSystemClassCell.ENGLISH.getCellNo()).replaceAll("\\s+", "");
                    String metricUnit = reader.getStringValueFromRow(row , UnitSystemClassCell.METRIC.getCellNo()).replaceAll("\\s+", "");
                    String canadianUnit = reader.getStringValueFromRow(row , UnitSystemClassCell.CANADIAN.getCellNo()).replaceAll("\\s+", "");

                    //final String unitExists = unitAndQuantityClass.computeIfAbsent(englishUnit, key1 -> unitAndQuantityClass.computeIfAbsent(metricUnit, key2 -> unitAndQuantityClass.getOrDefault(canadianUnit, null)));

                    unitAndQuantityClass.put(englishUnit, quantityClass);
                    unitAndQuantityClass.put(metricUnit, quantityClass);
                    unitAndQuantityClass.put(canadianUnit, quantityClass);

                    quantityClassAndBaseUnit.put(quantityClass, new HashMap<>(){{
                        put(UnitSystem.ENGLISH, englishUnit);
                        put(UnitSystem.METRIC, metricUnit);
                        put(UnitSystem.CANADIAN, canadianUnit);
                    }});

                }
            });
        } catch (IOException e) {
            throw new UnitSystemSetException(e.getMessage(), e);
        }
    }

    @Override
    public String getUnitFor(UnitSystem unitSystem, String sourceUnit) throws UnitSystemSetException {
        final String quantityClass = unitAndQuantityClass.getOrDefault(sourceUnit, null);
        if(!Objects.isNull(quantityClass)){
            final Map<UnitSystem, String> systemWiseUnits = quantityClassAndBaseUnit.getOrDefault(quantityClass, null);
            if(!Objects.isNull(systemWiseUnits)){
                final String result = systemWiseUnits.getOrDefault(unitSystem, null);
                if(!Objects.isNull(result)) return result;
            }

        }
        throw new UnitSystemSetException("Error while fetching unit for given system: "+ unitSystem + " and sourceUnit: "+ sourceUnit);
    }
}

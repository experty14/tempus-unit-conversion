package com.hashmapinc.tempus.service;

import com.hashmapinc.tempus.exception.UnitSystemSetException;
import com.hashmapinc.tempus.model.UnitSystemClassCell;
import com.hashmapinc.tempus.util.XlsReaderUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class UnitSystemServiceImpl implements UnitSystemService {

    private Map<UnitSystem, Map<String, String>> systemBasedUnits;


    private XlsReaderUtil reader;
    private String workbookFile;
    private String sheetName;

    public UnitSystemServiceImpl(String workbookFile, String sheetName) {
        reader = new XlsReaderUtil();
        this.workbookFile = workbookFile;
        this.sheetName = sheetName;
        this.systemBasedUnits = new HashMap<>();
    }

    public void initialize() throws UnitSystemSetException {

        for(UnitSystem unitSystem : UnitSystem.values()){
            systemBasedUnits.put(unitSystem, new HashMap<>());
        }

        try (Workbook workbook = reader.getWorkbook(workbookFile)) {
            Sheet sheet = reader.getSheetFromWorkbook(workbook , sheetName);

            sheet.forEach(row -> {
                if (row.getRowNum() != 0) {
                    String baseUnit = reader.getStringValueFromRow(row , UnitSystemClassCell.BASE.getCellNo()).replaceAll("\\s+", "");
                    String englishUnit = reader.getStringValueFromRow(row , UnitSystemClassCell.ENGLISH.getCellNo()).replaceAll("\\s+", "");
                    String metricUnit = reader.getStringValueFromRow(row , UnitSystemClassCell.METRIC.getCellNo()).replaceAll("\\s+", "");
                    String canadianUnit = reader.getStringValueFromRow(row , UnitSystemClassCell.CANADIAN.getCellNo()).replaceAll("\\s+", "");

                    systemBasedUnits.get(UnitSystem.ENGLISH).put(baseUnit, englishUnit);
                    systemBasedUnits.get(UnitSystem.METRIC).put(baseUnit, metricUnit);
                    systemBasedUnits.get(UnitSystem.CANADIAN).put(baseUnit, canadianUnit);
                }
            });
        } catch (IOException e) {
            throw new UnitSystemSetException(e.getMessage(), e);
        }
    }

    @Override
    public String getUnitFor(UnitSystem unitSystem, String baseUnit) throws UnitSystemSetException {
        Map<String, String> systemUnitsByBase = systemBasedUnits.get(unitSystem);
        if(systemUnitsByBase != null){
            final String unit = systemUnitsByBase.getOrDefault(baseUnit, null);
            if(unit != null){
                return unit;
            }
        }
        throw new UnitSystemSetException("Error while fetching unit for given system: "+ unitSystem + " and baseUnit: "+ baseUnit);
    }
}

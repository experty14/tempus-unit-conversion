package com.hashmapinc.tempus.service;

import com.hashmapinc.tempus.exception.QuantityClassSetException;
import com.hashmapinc.tempus.model.QuantityClassCell;
import com.hashmapinc.tempus.util.XlsReaderUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
public class QuantityClassSetServiceImpl implements QuantityClassSetService {

    private Map<String, Set<String>> quantityClassMemberUnits;

    private XlsReaderUtil reader;
    private String workbookFile;
    private String sheetName;

    public QuantityClassSetServiceImpl(String workbookFile, String sheetName) {
        reader = new XlsReaderUtil();
        this.workbookFile = workbookFile;
        this.sheetName = sheetName;
    }

    public void initializeQuantityClassMap() throws QuantityClassSetException {
        quantityClassMemberUnits = new HashMap<>();

        try (Workbook workbook = reader.getWorkbook(workbookFile)) {
            Sheet sheet = reader.getSheetFromWorkbook(workbook , sheetName);

            sheet.forEach(row -> {
                if (row.getRowNum() != 0) {
                    String quantityClassName = reader.getStringValueFromRow(row , QuantityClassCell.CLASS_NAME.getCellNo());
                    String quantityClassUnit = reader.getStringValueFromRow(row , QuantityClassCell.CLASS_UNIT.getCellNo());
                    Set<String> memberUnits = quantityClassMemberUnits.get(quantityClassName);
                    if (memberUnits == null) {
                        memberUnits = new HashSet<>();
                    }
                    memberUnits.add(quantityClassUnit);
                    quantityClassMemberUnits.put(quantityClassName , memberUnits);
                }
            });
        } catch (IOException | InvalidFormatException e) {
            throw new QuantityClassSetException(e.getMessage(), e);
        }
    }

    public Set<String> getAllQuantityClass() throws QuantityClassSetException {
        return quantityClassMemberUnits.keySet();
    }

    public Map<String, Set<String>> getMemberUnitsForAllQuantityClass() throws QuantityClassSetException {
        return quantityClassMemberUnits;
    }

    public Set<String> getMemberUnitsForQuantityClass(String quantityClass) throws QuantityClassSetException {
        Set<String> memberUnits = quantityClassMemberUnits.get(quantityClass);
        if (memberUnits == null) {
            throw new QuantityClassSetException("Quantity Class not present with this name");
        }
        return memberUnits;
    }
}

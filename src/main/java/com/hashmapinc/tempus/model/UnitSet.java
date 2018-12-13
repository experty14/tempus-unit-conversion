package com.hashmapinc.tempus.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UnitSet {

    private String unit;
    private String name;
    private String baseUnit;
    private UnitConstants constants;
}

package com.hashmapinc.tempus.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Quantity {

    private Double value;
    private String unit;
}

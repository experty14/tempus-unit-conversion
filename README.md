<img src="https://github.com/hashmapinc/hashmap.github.io/blob/master/images/tempus/TempusLogoBlack2.png" width="910" height="245" alt="Hashmap, Inc Tempus"/>

[![License](http://img.shields.io/:license-Apache%202-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)


# tempus-unit-conversion
Unit conversion library written for Tempus. Library uses Energistics Unit of Measure Usage Guide and standards for unit conversion.
All unit conversion are supported according to Energistics conversion formula. Library provide api for converting given quantity to SI Unit system. 
Unit Conversion library also provide api's for quantity in given unit to target unit. API's for listing quantity classes 
and its different member units are supported in library.

## Table of Contents

- [Features](#features)
- [Requirements](#requirements)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [License](#license)

## Features

This library aims to provide a few key features:

* Converting given Quantity with unit to SI Unit
* Converting given Quantity with unit to any other target unit in same Quantity Class
* Listing all Quantity unit classes
* Listing all Member Units for a particular Quantity Class

## Requirements

* JDK 1.8 at a minimum
* Maven 3.1 or newer
* Git client (to build locally)

## Getting Started
To build the library and get started first off clone the GitHub repository 

    git clone https://github.com/hashmapinc/tempus-unit-conversion.git

Change directory into the tempus-unit-conversion

    cd tempus-unit-conversion
    
Execute a maven clean install

    mvn clean install
    
A Build success message should appear

    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time: 6.308 s
    [INFO] Finished at: 2018-12-13T11:35:54+05:30
    [INFO] Final Memory: 32M/479M
    [INFO] ------------------------------------------------------------------------

## Usage

#### Initalization
```java
    UnitConvertorService unitConvertorService = UnitConvertorContext.getInstanceOfUnitConvertorService();
    QuantityClassSetService quantityClassSetService = UnitConvertorContext.getInstanceOfQuantityClassSetService();
``` 
#### Unit Convertor API's

* Convert kilometer(km) to meter(m) which is SI unit of length
```java
    Quantity quantity = new Quantity(10.0, "km");
    Quantity siQuantity = unitConvertorService.convertToSiUnit(quantity);
```

* Convert kilometer(km) to feet(m) 
```java
Quantity quantity = new Quantity(2.5, "km");
Quantity targetQuantity = unitConvertorService.convertToTargetUnit(quantity, "ft");
```

* Get All Quantity Class
```java
    Set quantitySetClasses = quantityClassSetService.getAllQuantityClass();
```

* Get Member Units for length Quantiy Class
```java
    Set lengths = quantityClassSetService.getMemberUnitsForQuantityClass("length");
```

* Get all Member Units with all Quanaity Class
```java
    Map quantityClassMap = quantityClassSetService.getMemberUnitsForAllQuantityClass();
```
## License

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

## Maven

Can be used with the following maven dependancy
```xml
         <dependency>
            <groupId>com.hashmapinc.tempus</groupId>
            <artifactId>tempus-unit-conversion</artifactId>
            <version>1.0.2</version>
        </dependency>
```

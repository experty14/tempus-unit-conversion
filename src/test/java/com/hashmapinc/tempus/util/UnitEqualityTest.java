package com.hashmapinc.tempus.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("Should pass the method parameters provided by the unitsProvider() method")
class UnitEqualityTest {

    private static Stream<Arguments> unitsProvider() {
        return Stream.of(
                Arguments.of("s/((f.a).s)", "s/((f.a).s)", true),
                Arguments.of("s/(f.a.s)", "s/(f.a.s)", true),
                Arguments.of("s/(f.a)", "s/(a.f)", true),
                Arguments.of("s/(f.a)", "s/(f.a)", true),
                Arguments.of("s/(a.a)", "s/(a.a)", true),
                Arguments.of("s.d/(a.a)", "s.d/(a.a)", true),
                Arguments.of("s.d/(1000 ft.a)", "s.d/(a.1000ft)", true),
                Arguments.of("s.d/(1000 ft.a)", "s.d/(a.kft)", true),
                Arguments.of("s / (a. b)", "s /(b. a)", true),
                Arguments.of("s/((f.a).s)", "s/((f.a).s)", true),
                Arguments.of("s/((f.a).s)", "s/(f.s)", false)
        );
    }


    @DisplayName("Should equate given units correctly")
    @ParameterizedTest(name = "{index} => unit1={0}, unit2={1}, isSimilar={2}")
    @MethodSource("unitsProvider")
    public void checkIfEquals(String unit1, String unit2, boolean expected) {
        //System.out.println(Utility.checkIfEquals("s/((f*a)*s)", "m/(a*(s*f))"));
        assertEquals(expected, UnitEquality.check(unit1, unit2));
    }
}
package com.hashmapinc.tempus.util;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnitEquality {
    public static boolean check(String expression1, String expression2){
        String exp11 = expression1.replaceAll("\\.", "*").replaceAll("\\s+", "");
        String exp12 = expression2.replaceAll("\\.", "*").replaceAll("\\s+", "");
        if(!_check(exp11, exp12)){
            if(exp11.contains("1000") || exp12.contains("1000")){
                final String exp111 = exp11.replace("1000", "k");
                final String exp112 = exp12.replace("1000", "k");
                return _check(exp111, exp112);
            } else {
                return false;
            }
        }
        return true;
    }

    private static boolean _check(String exp1, String exp2){
        if(!getDeclaredVariables(exp1).equals(getDeclaredVariables(exp2))){
            return false;
        }
        final String[] split1 = exp1.split("/");
        final String[] split2 = exp2.split("/");

        if(split1.length != split2.length) return false;

        if(split1.length == 1){
            final Set<String> set1 = getDeclaredVariables(String.join("", split1));
            final Set<String> set2 = getDeclaredVariables(String.join("", split2));
            return set1.equals(set2);
        } else {
            return _check(String.join("", split1), String.join("", split2));
        }
    }

    private static Set<String> getDeclaredVariables(String expression){
        Set<String> varList= new HashSet<>();
        Pattern p = Pattern.compile("[A-Za-z]+");
        Matcher m = p.matcher(expression);
        while (m.find())
        {
            varList.add(m.group());
        }
        return varList;
    }
}

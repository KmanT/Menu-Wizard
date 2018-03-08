package com.company;

public class Validator {
    public Validator() {

    }

    public static Boolean emptyCheck(String val) {
        return val.isEmpty();
    }

    /*public static Boolean intCheck(String val) {
        return intCheck(val);

    }

    public static Boolean intCheck(String val, int radix) {
        if(val.isEmpty()) return false;
        for(int i = 0; i < val.length(); i++) {
            if(i == 0 && val.charAt(i) == '-') {
                if (val.length() == 1) return false;
                else continue;
            }
            if (Character.digit(val.charAt(i), radix) < 0) return false;
        }
        return true;
    }*/

    public static Boolean intCheck(String val) {
        try {
            Integer.parseInt(val);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Boolean doubleCheck(String val) {
        try {
            Double.parseDouble(val);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Boolean floatCheck(String val) {
        try {
            Float.parseFloat((val));
            return true;
        } catch (Exception e) {
            return false;
        }
    }



}

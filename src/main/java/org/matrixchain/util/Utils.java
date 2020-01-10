package org.matrixchain.util;

import java.math.BigInteger;

public class Utils {

    public static boolean isHexEncoded(String value) {
        if (value == null) return false;
        if ("".equals(value)) return true;

        try {
            //noinspection ResultOfMethodCallIgnored
            new BigInteger(value, 16);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

package com.ogs.grounder;

public class Utils {
    public static String cleanString(String s) {
        return s.toLowerCase().trim().replaceAll("(,|\\.|$|\\(|\\)|&)", "");
    }
}

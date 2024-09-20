package com.example.progettoisw.utils;

public class StringUtils {
    public static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase().concat(str.substring(1));
    }
}

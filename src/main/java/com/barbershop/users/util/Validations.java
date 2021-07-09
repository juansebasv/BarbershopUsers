package com.barbershop.users.util;

public class Validations {

    public static boolean isValid(String word) {
        return (null != word) && (!word.trim().isEmpty()) && (word.trim().length() > 1);
    }
}

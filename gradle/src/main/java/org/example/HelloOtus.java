package org.example;

import com.google.common.base.Strings;

public class HelloOtus {
    public static void main(String[] args) {
        String str = "Hello World!";
        if (Strings.isNullOrEmpty(str)) {
            System.out.println("String is null or empty");
        } else {
            System.out.println("String is not null or empty");
        }
    }
}
package com.airnz.email.emailsample.utils;

import java.util.regex.Pattern;

public class Utils {
    public static String REGEX_EMAIL = "^[\\w\\.\\%\\+\\-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}$";

    public static boolean isValidEmail(String email) {
        return email != null && Pattern.matches(REGEX_EMAIL, email);
    }

    public static String getToken(String token) {
        return token == null ? null : token.replaceAll("Bearer", "").trim();
    }
}

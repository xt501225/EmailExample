package com.airnz.email.emailsample.utils;

import java.util.UUID;

public class IDGenerator {

    public static String generateID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}

package com.project.ecommerce_api.helpers;

import java.util.Random;

public class TokenGenerator {
    public static String generateToken() {
        Random random = new Random();
        int token = 100000 + random.nextInt(900000); // Generate a random number between 100000 and 999999
        return String.valueOf(token);

    }
}

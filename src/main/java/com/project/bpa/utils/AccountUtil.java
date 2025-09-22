package com.project.bpa.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class AccountUtil {
    // Generate 9 digits account number
    public static String generateAccountNumber() {
        Random random = new Random();
        String accountNumber = String.format("%09d", random.nextInt(900000000) + 100000000);
        return accountNumber;
    }
}

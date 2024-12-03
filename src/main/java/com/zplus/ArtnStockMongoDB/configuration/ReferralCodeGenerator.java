package com.zplus.ArtnStockMongoDB.configuration;
import java.security.SecureRandom;
import java.util.UUID;

public class ReferralCodeGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CODE_LENGTH = 7;

//    public static void main(String[] args) {
//        String referralCode = generateReferralCode();
//        System.out.println("Generated Referral Code: " + referralCode);
//    }

    public static String generateReferralCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder codeBuilder = new StringBuilder(CODE_LENGTH);

        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            codeBuilder.append(randomChar);
        }
        System.out.println("Code: " + codeBuilder.toString());

        return codeBuilder.toString();
    }
}







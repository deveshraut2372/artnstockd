package com.zplus.ArtnStockMongoDB.configuration;

import java.util.UUID;

public class GiftAndPromoCode {


    public static String generatePromoCode() {
        UUID uuid = UUID.randomUUID();
        String promoCode = uuid.toString().replace("-", "").substring(0, 6);
        System.out.println("Promo Code: " + promoCode);
        return promoCode;
    }
}

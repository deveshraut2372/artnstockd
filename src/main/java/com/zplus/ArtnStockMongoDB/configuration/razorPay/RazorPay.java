package com.zplus.ArtnStockMongoDB.configuration.razorPay;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@AllArgsConstructor@ToString
public class RazorPay {

    private String applicationFee;

    private String razorpayOrderId;

    private String secretKey;

    private String notes;

    private String imageURL;

    private String theme;

    private String  merchantName;

    private String purchaseDescription;

    private String customerName;

    private String customerEmail;

    public RazorPay() {

    }
}

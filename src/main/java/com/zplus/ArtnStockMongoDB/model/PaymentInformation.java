package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@ToString
public class PaymentInformation {

    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String status;
    private String signature;
}

package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Getter
@Setter
public class PaymentDetails {

    private String paymentName;
    private String method;
    private String paymentEmail;
    private String minPayout;

}

package com.zplus.ArtnStockMongoDB.configuration.razorPay;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter@AllArgsConstructor
public class Response {

    private int statusCode;
    private RazorPay razorPay;

    public Response() {

    }
}

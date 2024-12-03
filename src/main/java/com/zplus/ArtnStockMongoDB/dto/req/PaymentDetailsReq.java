package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDetailsReq {

    private String paymentName;
    private String method;
    private String paymentEmail;
    private String minPayout;
    private String userId;

}

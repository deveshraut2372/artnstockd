package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PaymentReqDto {

    private Integer customerId;
    private String customerFullName;
    private String customerMail;
    private String customerMobileNo;
    private String amount;
}

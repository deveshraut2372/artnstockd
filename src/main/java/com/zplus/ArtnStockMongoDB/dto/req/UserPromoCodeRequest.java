package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class UserPromoCodeRequest {

    private String userPromoCodeId;
    private String promoCode;
    private String codeType;
    private String userId;
//    private Double estimatedAmount;
}

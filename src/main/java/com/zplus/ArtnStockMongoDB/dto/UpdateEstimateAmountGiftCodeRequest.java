package com.zplus.ArtnStockMongoDB.dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class UpdateEstimateAmountGiftCodeRequest {
    private String userId;
    private String giftCode;
}

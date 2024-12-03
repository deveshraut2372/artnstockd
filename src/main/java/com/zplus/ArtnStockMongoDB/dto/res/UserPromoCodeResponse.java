package com.zplus.ArtnStockMongoDB.dto.res;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@ToString
public class UserPromoCodeResponse {
    private String message;
    private Boolean flag;
    private Double discount;
}

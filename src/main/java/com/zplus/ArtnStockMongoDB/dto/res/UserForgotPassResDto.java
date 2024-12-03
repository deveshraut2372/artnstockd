package com.zplus.ArtnStockMongoDB.dto.res;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserForgotPassResDto {

    private String customerId;
    private Boolean status;
    private String customerMail;
}

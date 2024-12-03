package com.zplus.ArtnStockMongoDB.dto.res;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class UserUpdatePasswordResDto {
    private String userId;

    private String emailAddress;

    private String msg;

    private Boolean flag;

    private Integer responseCode;
}

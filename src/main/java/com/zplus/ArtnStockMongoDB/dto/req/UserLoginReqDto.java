package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class UserLoginReqDto {

    private String userName;

    private String password;
}

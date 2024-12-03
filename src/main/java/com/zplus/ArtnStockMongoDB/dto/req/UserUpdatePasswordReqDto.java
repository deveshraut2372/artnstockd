package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class UserUpdatePasswordReqDto {
    private String userId;

    private String password;

    private String newPassword;

    private String confirmPassword;
}

package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserChangeEmailReq {

    private String newEmailAddress;

    private String userId;
}

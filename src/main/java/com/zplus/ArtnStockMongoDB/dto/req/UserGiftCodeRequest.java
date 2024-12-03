package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class UserGiftCodeRequest {

    private String userGiftCodeId;
    private String giftCode;
    private String codeType;
    private String userMasterId;
}

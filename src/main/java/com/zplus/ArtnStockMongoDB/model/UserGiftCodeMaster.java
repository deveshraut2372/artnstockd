package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = "user_gift_code_master")
public class UserGiftCodeMaster {

    @Id
    private String userGiftCodeId;
    private String giftCode;
    private Double discount;
    private String status;
    @DBRef
    private UserMaster userMaster;
}

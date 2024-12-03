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
@Document(collection = "user-promo_code_master")
public class UserPromoCodeMaster {
    @Id
    private String userPromoCodeId;
    private String promoCode;
    private Double discount;
    private String status;

    @DBRef
    private UserMaster userMaster;
}

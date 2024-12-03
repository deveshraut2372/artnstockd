package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@ToString
@Document(collection = "promo_code_master")
public class PromoCodeMaster {

    @Id
    private String promoCodeId;
    private String promoCode;
    private String status;
    private String description;
    private Date startDate;
    private Date endDate;
    private Double discount;
    private Double maxAmount;
}

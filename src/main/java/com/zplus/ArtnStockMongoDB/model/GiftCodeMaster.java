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
@Document(collection = "gift_code_master")
public class GiftCodeMaster {

    @Id
    private String giftCodeId;
    private String giftCode;
    private String status;
    private String description;
    private Double discount;
    private Double maxAmount;
    private Date startDate;
    private Date endDate;

}

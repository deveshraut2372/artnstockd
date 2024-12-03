package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter@Setter
public class GiftCodeRequest {
    private String giftCodeId;
    private String status;
    private String description;
    private Double discount;
    private Double maxAmount;
    private Date startDate;
    private Date endDate;
}

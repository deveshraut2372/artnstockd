package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter@Setter
public class PromoCodeRequest {
    private String promoCodeId;
    private String status;
    private String description;
    private Date startDate;
    private Date endDate;
    private Double discount;
    private Double maxAmount;
}

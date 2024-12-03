package com.zplus.ArtnStockMongoDB.dto.req;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DiscountMasterReq {

    private String discountId;

    private Double firstDiscount;

    private Double seasonalDiscount;

    private Double festivalDiscount;

    private Boolean seasonalDiscountStatus;

    private Boolean festivalDiscountStatus;

    private String status;


}

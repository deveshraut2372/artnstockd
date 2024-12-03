package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReferralEarningReqDto {
private String item;
        private String itemDeatails;
        private Double salePrice;
        private int Quatity;
        private String licenseType;
        private String artName;
        private String artId;
        private String frame;
        private String printSize;
    }
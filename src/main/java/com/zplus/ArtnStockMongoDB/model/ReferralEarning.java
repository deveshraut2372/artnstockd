package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = "referral_earning")
public class ReferralEarning {
    private String itemDeatails;
    private String item;
    private Double salePrice;
    private int Quatity;
    private Double earning;
    private Double percentageEarning;
    private Double tax;
    private Double percentageTax;
    private Double earningAfterDeduction;
    private String licenseType;
    private String artName;
    private String artId;
    private String frame;
    private String printSize;
}


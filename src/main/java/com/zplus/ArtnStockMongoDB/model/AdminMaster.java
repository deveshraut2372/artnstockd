package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = "admin_master")
public class AdminMaster {
    @Id
    private String adminId;

    private String FacebookLink;
    private String InstagramLink;
    private String LinkedInLink;
    private String twitterLink;
    private String shippingPrice;
    private Double tax;
    private Double artPercentage;
}

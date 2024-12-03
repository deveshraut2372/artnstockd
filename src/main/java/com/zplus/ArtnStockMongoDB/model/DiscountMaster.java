package com.zplus.ArtnStockMongoDB.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "Discount_Master")
public class DiscountMaster {

    @Id
    private String discountId;

    private Double firstDiscount;

    private Double seasonalDiscount;

    private Double festivalDiscount;

    private Boolean seasonalDiscountStatus;

    private Boolean festivalDiscountStatus;

    private String status;

    private Date date;


}

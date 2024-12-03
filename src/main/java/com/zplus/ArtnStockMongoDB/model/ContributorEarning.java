package com.zplus.ArtnStockMongoDB.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "contributor_earning")
public class ContributorEarning {
  @Id
    private String conEarningId;
    private String artId;
    private LocalDate date;
    private String orderStatus;
    private int quantity;
    private double amount;
    private String paymentStatus;
    private String userId;
    private Double productPrice;
    private String cartArtFrameMaster; //NewOrderUniqueNo
    private String cartProductMaster; //CartProductUniqueNo
    private Double basePrice;

}

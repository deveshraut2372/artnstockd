package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@Document(collection = "reference_earning_summary")
public class ReferenceEarningSummary {
    @Id
    private String refereceId;
    private String clientRole;
    private Date registedDate;
    private LocalDate firstPurchase;
    private Double salePrice;
    private Double taxDeduction;
    private Double yourEarning;
    private String paymentStatus;
    private List<ReferralEarning> referralEarningList;
    private String userId;
    private String clientId;
    @DBRef
    private UserMaster userdata;

    @DBRef
    private UserMaster clientdata;

}

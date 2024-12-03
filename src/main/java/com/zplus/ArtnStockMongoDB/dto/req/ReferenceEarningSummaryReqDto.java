package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class ReferenceEarningSummaryReqDto {
    private String clientRole;
    private Date registedDate;
    private LocalDate firstPurchase;
    private Double SalePrice;
    private Double taxDeduction;
    private List<ReferralEarningReqDto> referralEarningList;
    private String userdata;
    private String clientdata;
    private String paymentStatus;
}
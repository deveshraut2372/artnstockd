package com.zplus.ArtnStockMongoDB.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReferenceEarningSummaryResDto {
    private String clientName;
    private String refereceId;
    private String clientRole;
    private Date registedDate;
    private LocalDate firstPurchase;
    private Double salePrice;
    private Double taxDeduction;
    private Double yourEarning;
    private String paymentStatus;
    //private List<ReferralEarning> referralEarningList;
    private String userId;
    private String clientId;

}

package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter@Setter
public class ReferralEarningMailRequest {
    private String referPersonEmailId;
    private String userId;
    private String referralEarningId;
    private String referralType;
    private Date referDate;
    private String earningForm;
    private String status;

}

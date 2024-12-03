package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateCustomerReviewMasterReq {
    private String customerReviewId;
    private String reviewMsg;
    private String reviewTitle;
    private String status;
    private Integer reviewStar;
    private List<String> reviewImage;
    private String artId;
    private String userId;
}

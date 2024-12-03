package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class CustomerReviewMasterReq {
    private String customerReviewId;
    private String reviewMsg;
//    private String status;
    private Integer reviewStar;
    private List<String> reviewImage;
    private String artId;
    private String userId;
    private Integer likeCount;
}



//    private String parantReviewId;

//    private String artFrameId;
//    private String artProductId;
//    private String productId;

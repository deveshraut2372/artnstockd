package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.CustomerReviewMasterReq;
import com.zplus.ArtnStockMongoDB.dto.req.LikeRequestDto;
import com.zplus.ArtnStockMongoDB.dto.req.ReviewReplyRequestDto;
import com.zplus.ArtnStockMongoDB.dto.req.UpdateCustomerReviewMasterReq;
import com.zplus.ArtnStockMongoDB.dto.res.Message;
import com.zplus.ArtnStockMongoDB.model.CustomerReviewMaster;

import java.util.List;

public interface CustomerReviewMasterService {

    Boolean createCustomerReviewMaster(CustomerReviewMasterReq customerReviewMasterReq);

    Boolean updateCustomerReviewMaster(UpdateCustomerReviewMasterReq updateCustomerReviewMasterReq);

    List getAllCustomerReviewMaster();

    CustomerReviewMaster editCustomerReviewMaster(String customerReviewId);

    List getActiveCustomerReviewMaster();

    List getArtIdWiseCustomerReviewMaster(String artId);

    Message addAdminReviewReplyToUser(ReviewReplyRequestDto reviewReplyRequestDto);

    List<CustomerReviewMaster> todayReviewList();

    List<CustomerReviewMaster> YearReviewList(Integer year);

    List<CustomerReviewMaster> MonthReviewList(Integer month);

    Boolean increseLikecount(LikeRequestDto likeRequestDto);

    Boolean minusLikecount(LikeRequestDto likeRequestDto);

  Boolean checkUserLike(LikeRequestDto likeRequestDto);


//    List getArtProductIdWiseCustomerReviewMaster(String artProductId);

//    List getProductIdWiseCustomerReviewMaster(String productId);
}

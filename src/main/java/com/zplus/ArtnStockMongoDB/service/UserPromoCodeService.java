package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.UserPromoCodeRequest;
import com.zplus.ArtnStockMongoDB.dto.res.Message;
import com.zplus.ArtnStockMongoDB.dto.res.UserPromoCodeResponse;
import com.zplus.ArtnStockMongoDB.model.UserPromoCodeMaster;

import java.util.List;

public interface UserPromoCodeService {
    UserPromoCodeResponse createUserPromoCodeMaster(UserPromoCodeRequest userPromoCodeRequest);

    Boolean updateUserPromoCodeMaster(UserPromoCodeRequest userPromoCodeRequest);

    List getAllUserPromoCodeMaster();

    UserPromoCodeMaster editUserPromoCodeMaster(String userPromoCodeId);

    List getUserWisePromoCodeCodeMaster(String userId);
}

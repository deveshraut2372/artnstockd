package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.UserGiftCodeRequest;
import com.zplus.ArtnStockMongoDB.dto.res.UserPromoCodeResponse;
import com.zplus.ArtnStockMongoDB.model.UserGiftCodeMaster;

import java.util.List;

public interface UserGiftCodeService {
    UserPromoCodeResponse createUserGiftCodeMaster(UserGiftCodeRequest userGiftCodeRequest);

    List getAllUserGiftCodeMaster();

    UserGiftCodeMaster editUserGiftCodeMaster(String userGiftCodeId);

    List getUserWiseGiftCodeMaster(String userId);
}

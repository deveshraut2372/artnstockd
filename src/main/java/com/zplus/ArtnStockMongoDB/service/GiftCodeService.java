package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.GiftCodeRequest;
import com.zplus.ArtnStockMongoDB.model.GiftCodeMaster;

import java.util.List;

public interface GiftCodeService {
    Boolean createGiftCodeMaster(GiftCodeRequest giftCodeRequest);

    Boolean updateGiftCodeMaster(GiftCodeRequest giftCodeRequest);

    List getAllGiftCodeMaster();

    List getActiveGiftCodeMaster();

    GiftCodeMaster editGiftCodeMaster(String giftCodeId);

    List getGiftCodeMasterByStatus(String status);

    Boolean deleteGiftCodeByGiftCodeId(String giftCodeId);
}

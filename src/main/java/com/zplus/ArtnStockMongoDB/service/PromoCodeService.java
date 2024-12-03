package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.PromoCodeRequest;
import com.zplus.ArtnStockMongoDB.model.PromoCodeMaster;

import java.util.List;

public interface PromoCodeService {
    Boolean createPromoCodeMaster(PromoCodeRequest promoCodeRequest);

    Boolean updatePromoCodeMaster(PromoCodeRequest promoCodeRequest);

    List getAllPromoCodeMaster();

    PromoCodeMaster editPromoCodeMaster(String promoCodeId);

    List getActivePromoCodeMaster();

    Boolean deletePromoCodeMaster(String promoCodeId);
}

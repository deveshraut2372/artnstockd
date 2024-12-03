package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.DiscountMasterReq;
import com.zplus.ArtnStockMongoDB.model.DiscountMaster;

import java.util.List;

public interface DiscountMasterService {

    Boolean createDiscountMaster(DiscountMasterReq discountMasterReq);

    Boolean updateDiscountMaster(DiscountMasterReq discountMasterReq);

    List<DiscountMaster> getAllDiscounts();

    DiscountMaster editByDiscountId(String discountId);

    Boolean deleteByDiscountId(String discountId);
}

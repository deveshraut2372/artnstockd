package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.SizeAndPriceReq;
import com.zplus.ArtnStockMongoDB.model.SizeAndPrice;

import java.util.List;

public interface SizeAndPriceService {
    Boolean createSizeAndPrice(SizeAndPriceReq sizeAndPriceReq);

    Boolean updateSizeAndPrice(SizeAndPriceReq sizeAndPriceReq);


    List<SizeAndPrice> getAllSizes();

    SizeAndPrice getBySizeAndPriceId(String sizeAndPriceId);

    Boolean deleteBySizeAndPriceId(String sizeAndPriceId);

    List<SizeAndPrice> getSizeAndPricesByProductColorId(String productColorId);
}

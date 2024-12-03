package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.CartArtFrameMasterRequest;
import com.zplus.ArtnStockMongoDB.dto.res.CartArtSaveResponse;
import com.zplus.ArtnStockMongoDB.model.CartArtFrameMaster;
import com.zplus.ArtnStockMongoDB.model.CartMaster;

import java.util.List;

public interface CartArtFrameMasterService {
    CartArtSaveResponse saveCartArtFrame(CartArtFrameMasterRequest cartArtFrameMasterRequest);

    Boolean IncreaseCartQty(String cartArtFrameId);

    CartMaster getUserIdWiseCartMasterData(String userId);

    List<CartArtFrameMaster> getUserIdWiseCartArtFrameData(String userId);

    Boolean deleteCart(String cartArtFrameId);

    Boolean DecreaseCartQty(String cartArtFrameId);

    CartArtFrameMaster getCratArtFrameMaster(String cartArtFrameId);
}

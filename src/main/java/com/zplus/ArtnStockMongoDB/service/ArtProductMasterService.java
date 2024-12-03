package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.ArtMasterFilterReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.ArtProductReqDto;
import com.zplus.ArtnStockMongoDB.model.ArtProductMaster;

import java.util.List;

public interface ArtProductMasterService {

    Boolean createArtProductMaster(ArtProductReqDto artProductReqDto);

    List getActiveArtProductMaster();

    ArtProductMaster getArtProductIdData(String artProductId);

    List getAllArtProductMaster();


    Boolean createArtProductMasterD(ArtProductReqDto artProductReqDto);
}

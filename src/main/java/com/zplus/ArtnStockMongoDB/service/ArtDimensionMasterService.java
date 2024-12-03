package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.ArtDimensionMasterReqDto;
import com.zplus.ArtnStockMongoDB.model.ArtDimensionMaster;

import java.util.List;

public interface ArtDimensionMasterService {
    Boolean createArtDimensionMasterMaster(ArtDimensionMasterReqDto artDimensionMasterReqDto);

    Boolean updateArtDimensionMasterMaster(ArtDimensionMasterReqDto artDimensionMasterReqDto);

    List getAllArtDimensionMaster();

    ArtDimensionMaster editArtDimensionMaster(String artDimensionId);

    List getActiveArtDimensionMaster();
}

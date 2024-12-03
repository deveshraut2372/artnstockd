package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.FrameMatMasterReqDto;
import com.zplus.ArtnStockMongoDB.dto.res.FrameMatMasterResDto;
import com.zplus.ArtnStockMongoDB.model.BannerMaster;
import com.zplus.ArtnStockMongoDB.model.FrameMatMaster;

import java.util.List;

public interface FrameMatMasterService {
    Boolean createFrameMatMaster(FrameMatMasterReqDto frameMatMasterReqDto);

    Boolean updateFrameMatMaster(FrameMatMasterReqDto frameMatMasterReqDto);

    List<FrameMatMaster> getAllFrameMatMaster();

    FrameMatMaster editFrameMatMaster(String frameMatId);

    List getActiveFrameMatMaster();
}

package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.FrameReqDto;
import com.zplus.ArtnStockMongoDB.model.FrameMaster;

import java.util.List;

public interface FrameService {
    Boolean createFrameMaster(FrameReqDto frameReqDto);

    Boolean updateFrameMaster(FrameReqDto frameReqDto);

    List getAllFrameMaster();

    FrameMaster editProductTypeMaster(String frameId);

    List getActiveFrameMaster();
}

package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.LimitedEditionReqDto;
import com.zplus.ArtnStockMongoDB.model.LimitedEditionMaster;

import java.util.List;

public interface LimitedEditionService {
    Boolean createLimitedEditionMaster(LimitedEditionReqDto limitedEditionReqDto);

    Boolean updateLimitedEditionMaster(LimitedEditionReqDto limitedEditionReqDto);

    List getAllLimitedEditionMaster();

    LimitedEditionMaster editLimitedEditionMaster(String limitedEditionId);

    List getActiveLimitedEditionMaster();

    LimitedEditionMaster getTypeWiseList(String type);
}

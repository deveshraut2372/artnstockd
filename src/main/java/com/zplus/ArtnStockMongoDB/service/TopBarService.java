package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.TopBarMasterReq;
import com.zplus.ArtnStockMongoDB.model.TopBarMaster;

import java.util.List;

public interface TopBarService {

    Boolean createTopBar(TopBarMasterReq topBarMasterReq);

    Boolean updateTopBar(TopBarMasterReq topBarMasterReq);

    List<TopBarMaster> getAllTopBars();

    TopBarMaster editBytopBarId(String topBarId);

    Boolean DeleteBytopBarId(String topBarId);
}

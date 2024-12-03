package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.MediumMasterReq;
import com.zplus.ArtnStockMongoDB.model.MediumMaster;

import java.util.List;

public interface MediumMasterService {
    Boolean createMediumMaster(MediumMasterReq mediumMasterReq);

    Boolean updateMediumMaster(MediumMasterReq mediumMasterReq);

    List getAllMediumMaster();

    MediumMaster editmediumMaster(String mediumId);

    List getActiveMediumMaster();

    Boolean deleteMediumMaster(String mediumId);

    List getMediumMasterByStatus(String status);
}

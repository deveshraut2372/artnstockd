package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.MatReqDto;
import com.zplus.ArtnStockMongoDB.model.MatMaster;

import java.util.List;

public interface MatMasterService {
    Boolean createMatMaster(MatReqDto matReqDto);

    Boolean updateMatMaster(MatReqDto matReqDto);

    List getAllMatMaster();

    MatMaster editMatMaster(String matId);

    List getActiveMatMaster();

    MatMaster getTypeWiseList(String matType);
}

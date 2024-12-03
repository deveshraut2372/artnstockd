package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.ContributerMarkupRes;
import com.zplus.ArtnStockMongoDB.dto.req.OrientationMasterRequest;
import com.zplus.ArtnStockMongoDB.model.OrientationMaster;

import java.util.List;

public interface OrientationMasterService {
    Boolean createOrientationMaster(OrientationMasterRequest orientationMasterRequest);

    Boolean updateOrientationMaster(OrientationMasterRequest orientationMasterRequest);

    List getAllOrientationMaster();

    OrientationMaster editOrientationMaster(String orientationId);

    List getActiveOrientationMasterList();

    List getShapeWiseList(String shape);

    ContributerMarkupRes getContributerEarning100percentage(Double markup);
}

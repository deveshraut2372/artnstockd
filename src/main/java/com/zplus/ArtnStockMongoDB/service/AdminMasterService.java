package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.AdminMasterRequest;
import com.zplus.ArtnStockMongoDB.dto.req.ArtMasterFilterReqDto;
import com.zplus.ArtnStockMongoDB.model.AdminMaster;

import java.util.List;

public interface AdminMasterService {
    Boolean createAdminMaster(AdminMasterRequest adminMasterRequest);

    Boolean updateAdminMaster(AdminMasterRequest adminMasterRequest);

    List getAllAdminMaster();

    AdminMaster editAdminMaster(String adminId);

    Boolean changeTermsAndCondition();


}

package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.CustomerContactMasterReq;
import com.zplus.ArtnStockMongoDB.model.CustomerContactMaster;

import java.util.List;

public interface CustomerContactMasterService {
    Boolean createCustomerContactMaster(CustomerContactMasterReq customerContactMasterReq);

    Boolean updateCustomerContactMaster(CustomerContactMasterReq customerContactMasterReq);

    CustomerContactMaster editCustomerContactMaster(String customerContactId);

    List getCustomerContactMasterList();
}

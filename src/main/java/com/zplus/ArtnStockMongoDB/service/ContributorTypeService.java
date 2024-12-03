package com.zplus.ArtnStockMongoDB.service;


import com.zplus.ArtnStockMongoDB.dto.req.ContributorTypeReq;
import com.zplus.ArtnStockMongoDB.model.ContributorTypeMaster;

import java.util.List;

public interface ContributorTypeService {
    Boolean createContributorTypeMaster(ContributorTypeReq contributorTypeReq);

    Boolean updateContributorTypeMaster(ContributorTypeReq contributorTypeReq);

    List getAllContributorTypeMaster();

    ContributorTypeMaster editCountryMaster(String contributorTypeId);

    List getActiveContributorTypeMaster();

    Boolean deleteByContributorTypeId(String contributorTypeId);
}

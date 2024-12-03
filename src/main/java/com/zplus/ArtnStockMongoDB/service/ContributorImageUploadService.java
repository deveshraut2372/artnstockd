package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.ContributorImageFlagChangeReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.ContributorImageStatusChangeReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.ContributorImageUploadReq;
import com.zplus.ArtnStockMongoDB.dto.res.UserIdWiseContributorResDto;
import com.zplus.ArtnStockMongoDB.model.ContributorImageUploadMaster;

import java.util.List;

public interface ContributorImageUploadService  {





    List getAllContributorImageUpload();


    ContributorImageUploadMaster editContributorImageUploadr(String contributorImageUploadId);



    Boolean updateContributorImageUpload(ContributorImageUploadReq contributorImageUploadReq);

    Boolean createContributorImageUpload(List<ContributorImageUploadReq> contributorImageList);

    List getFlagTrueContributorImageUpload(String userId);


    List<ContributorImageUploadMaster>  getUserIdWiseContributorMaster(String userId);

    Boolean changeImageFlag(ContributorImageFlagChangeReqDto contributorImageFlagChangeReqDto);

    Boolean ContributorMasterChangeStatus(ContributorImageStatusChangeReqDto contributorImageStatusChangeReqDto);

    List<ContributorImageUploadMaster> getStatusWiseContributorMaster(String status);
}

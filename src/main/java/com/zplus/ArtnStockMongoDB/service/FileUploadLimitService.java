package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.res.ApprovedRatioRes;
import com.zplus.ArtnStockMongoDB.model.FileUploadLimitMaster;

import java.util.List;

public interface FileUploadLimitService {
    Boolean CalculateApprovedPercentage();


    List<FileUploadLimitMaster> getAllFileUploadLimitMaster();

    ApprovedRatioRes getUserIdWiseRatio(String userId);

     FileUploadLimitMaster createFileUploadLimit(String userId);
}

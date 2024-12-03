package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.FileUploadLimitPerWeekReqDto;
import com.zplus.ArtnStockMongoDB.model.FileUploadLimitPerWeekMaster;

import java.util.List;

public interface FileUploadLimitPerWeekService {
    Boolean saveFileUploadLimitPerWeekMaster(FileUploadLimitPerWeekReqDto fileUploadLimitPerWeekReqDto);

    Boolean updateFrequentlyAskedQuestions(FileUploadLimitPerWeekReqDto fileUploadLimitPerWeekReqDto);

    List<FileUploadLimitPerWeekMaster> getAllListFileUploadLimitPerWeekMaster();

    FileUploadLimitPerWeekMaster getByFileUploadLimitPerWeekId(String fileUploadLimitPerWeekId);
}

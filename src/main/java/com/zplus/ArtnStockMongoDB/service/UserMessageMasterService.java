package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.UserMessageMasterRequest;
import com.zplus.ArtnStockMongoDB.dto.res.MainResDto;
import com.zplus.ArtnStockMongoDB.model.UserMessageMaster;

import java.util.List;

public interface UserMessageMasterService {
    MainResDto create(UserMessageMasterRequest userMessageMasterRequest);
    MainResDto update(UserMessageMasterRequest userMessageMasterRequest);
    UserMessageMaster getById(String userMessageId);
    List<UserMessageMaster> getAll();
    MainResDto deleteById(String userMessageId);

    UserMessageMaster getUserIdWiseMessage(String userId);
}
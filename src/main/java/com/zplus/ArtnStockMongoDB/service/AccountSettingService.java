package com.zplus.ArtnStockMongoDB.service;


import com.zplus.ArtnStockMongoDB.dto.req.AccountSettingReq;
import com.zplus.ArtnStockMongoDB.model.AccountSettingMaster;

import java.util.List;

public interface AccountSettingService {
    
    Boolean createAccountSettingMaster(AccountSettingReq accountSettingReq);

    Boolean updateAccountSettingMaster(AccountSettingReq accountSettingReq);

    AccountSettingMaster editAccountSettingMaster(String accountSettingId);

    List<AccountSettingMaster> getAccountSettingMasters(String userId);
}

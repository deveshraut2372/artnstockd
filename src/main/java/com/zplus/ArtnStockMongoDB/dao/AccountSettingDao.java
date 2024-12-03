package com.zplus.ArtnStockMongoDB.dao;


import com.zplus.ArtnStockMongoDB.model.AccountSettingMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountSettingDao extends MongoRepository<AccountSettingMaster,String> {


    List<AccountSettingMaster> findAllByUserMaster_UserId(String userId);

    AccountSettingMaster findByStatus(String active);
}

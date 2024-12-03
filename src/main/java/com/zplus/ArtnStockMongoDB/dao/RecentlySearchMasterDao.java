package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.RecentlySearchMaster;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RecentlySearchMasterDao extends MongoRepository<RecentlySearchMaster,String> {

//    Optional<RecentlySearchMaster> findByUserMasterUserId(String userId);

    Optional<RecentlySearchMaster> findByUserMasterUserIdAndText(String userId, String text);

    void deleteByUserMasterUserId(String userId);
}

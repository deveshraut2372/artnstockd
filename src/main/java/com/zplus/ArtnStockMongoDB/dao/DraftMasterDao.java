package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.DraftMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DraftMasterDao extends MongoRepository<DraftMaster,String> {

    DraftMaster findByUserMaster_UserId(String userId);

    DraftMaster findByUserMasterUserId(String userId);
}

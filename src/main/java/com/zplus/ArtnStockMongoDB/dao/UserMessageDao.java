package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.UserMessageMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMessageDao extends MongoRepository<UserMessageMaster,String> {

    UserMessageMaster findByUserMaster_UserId(String userId);
}

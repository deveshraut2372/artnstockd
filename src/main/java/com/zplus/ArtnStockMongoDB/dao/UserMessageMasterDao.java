package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.UserMessageMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMessageMasterDao extends MongoRepository<UserMessageMaster,String> {


}






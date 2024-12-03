package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.TopBarMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface TopBarMasterDao extends MongoRepository<TopBarMaster,String> {

    @Query("{ }")
    List<TopBarMaster> getAll();
}

package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.LimitedEditionMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LimitedEditionDao extends MongoRepository<LimitedEditionMaster,String> {
    List findAllByStatus(String active);


    LimitedEditionMaster findAllByType(String type);
}

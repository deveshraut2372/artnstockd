package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.MediumMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediumMasterDao extends MongoRepository<MediumMaster,String> {

    List findAllByMediumStatus(String status);
}

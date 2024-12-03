package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.DiscountMaster;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface DiscountMasterDao extends MongoRepository<DiscountMaster,String> {


    List<DiscountMaster> findAllByStatus(String active);
}

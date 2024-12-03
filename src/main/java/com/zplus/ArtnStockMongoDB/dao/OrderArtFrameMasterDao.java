package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.OrderArtFrameMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderArtFrameMasterDao extends MongoRepository<OrderArtFrameMaster,String> {

}

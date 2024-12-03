package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.ArtDimensionMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtDimensionDao extends MongoRepository<ArtDimensionMaster,String> {

    List findAllByStatus(String active);
}

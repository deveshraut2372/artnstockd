package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.MainCategoryMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MainCategoryDao extends MongoRepository<MainCategoryMaster,String> {
    List<MainCategoryMaster> findAllByStatus(String active);
}

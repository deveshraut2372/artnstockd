package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.CategoryMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMasterDao extends MongoRepository<CategoryMaster,String> {

    List<CategoryMaster> findAllByStatus(String status);
}

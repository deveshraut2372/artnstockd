package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.ProductMainCategoryMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMainCategoryDao extends MongoRepository<ProductMainCategoryMaster,String> {

    List findAllByStatus(String active);
}

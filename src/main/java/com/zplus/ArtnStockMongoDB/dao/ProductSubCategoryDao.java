package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.ProductSubCategoryMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSubCategoryDao extends MongoRepository<ProductSubCategoryMaster,String> {

    List<ProductSubCategoryMaster> findAllByStatus(String active);

    List<ProductSubCategoryMaster> findByProductMainCategoryMaster_ProductMainCategoryId(String productMainCategoryId);

    List<ProductSubCategoryMaster> findByType(String type);

    List<ProductSubCategoryMaster> findByProductMainCategoryMaster_ProductMainCategoryIdAndStatus(String productMainCategoryId, String active);
}



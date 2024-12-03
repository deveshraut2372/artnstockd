package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.ProductStyle;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductStyleRepository extends MongoRepository<ProductStyle,String>{
    List<ProductStyle> findAllByProductSubSubCategoryId(String productSubSubCategoryId);
}

package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.ProductSubSubCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSubSubCategoryDao extends MongoRepository<ProductSubSubCategory,String> {

    List<ProductSubSubCategory> findAllByProductSubCategoryId(String productSubCategoryId);
}

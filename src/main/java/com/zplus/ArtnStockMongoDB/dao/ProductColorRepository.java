package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.ProductColor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface ProductColorRepository extends MongoRepository<ProductColor,String> {
    List<ProductColor> findAllByProductStyleId(String productStyleId);
}

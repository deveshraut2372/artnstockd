package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.ProductMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductMasterDao extends MongoRepository<ProductMaster,String> {
    Optional<ProductMaster> findByProductId(String productId);

    List findAllByStatus(String active);

    @Query("{productId :?0}")
    ProductMaster getProductId(String productId);

    List<ProductMaster> findByProductSubCategoryMaster_ProductSubCategoryId(String productSubCategoryId);

//    @Query(value = "{'productId':?0}",fields = "{'sizeAndPrices':1'}")
//    SizeAndPrice getAizeAndPrice(String productId);
}


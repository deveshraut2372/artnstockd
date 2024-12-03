package com.zplus.ArtnStockMongoDB.dao;


import com.zplus.ArtnStockMongoDB.model.SizeAndPrice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SizeAndPriceRepository extends MongoRepository<SizeAndPrice,String> {


    List<SizeAndPrice> findAllByProductColorId(String productColorId);


    @Query("{productColorId:?0}")
    Set<SizeAndPrice> getAllByProductColorId(String productColorId);
//    List<SizeAndPrice> findAllByProductColorId(String productColorId);
}

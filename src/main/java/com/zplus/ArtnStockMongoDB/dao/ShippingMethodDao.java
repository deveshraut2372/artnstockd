package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.ShippingMethod;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingMethodDao extends MongoRepository<ShippingMethod,String> {

    @Query("{shippingMethodId :?0}")
    ShippingMethod getMaster(@Param("shippingMethodId") String shippingMethodId);

    ShippingMethod findByShippingMethodName(String standard);
}

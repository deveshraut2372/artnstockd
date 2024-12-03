package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.CartProductMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartProductDao extends MongoRepository<CartProductMaster,String> {

    List<CartProductMaster> findByUserMaster_UserIdAndStatus(String userId, String incart);

    List<CartProductMaster> findByUserMaster_UserId(String userId);

    CartProductMaster findByCartProductId(String cartProductId);

    List<CartProductMaster> findByStatusAndUserMaster_UserId(String incart, String userId);

    @Query("{cartProductId :?0}")
    CartProductMaster getId(@Param("cartProductId") String id);


    int countByUserMaster_UserIdAndStatus(String userId, String incart);

    List<CartProductMaster> findAllByCartMaster_CartIdAndStatus(String cartId, String delete);
}


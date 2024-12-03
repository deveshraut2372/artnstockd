package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.UserMaster;
import com.zplus.ArtnStockMongoDB.model.WishListMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListDao extends MongoRepository<WishListMaster,String> {

//    Boolean existByUserMaster(UserMaster userMaster);

    Long countByUserMaster(UserMaster userMaster);

    Long countByUserMaster_UserId(String userId);

    List<WishListMaster> findByUserMaster_UserId(String userId);
}

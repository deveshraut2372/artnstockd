package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.CartArtFrameMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartArtFrameDao extends MongoRepository<CartArtFrameMaster,String> {
    List<CartArtFrameMaster> findByStatusAndUserMaster_UserId(String incart,String userId);

    CartArtFrameMaster findByCartArtFrameId(String cartArtFrameId);

    List<CartArtFrameMaster> findByUserMaster_UserId(String userId);

    @Query("{cartArtFrameId :?0}")
    CartArtFrameMaster getId(String id);


    int countByUserMaster_UserIdAndStatus(String userId, String incart);


    List<CartArtFrameMaster> findAllByCartMaster_CartIdAndStatus(String cartId, String delete);
}



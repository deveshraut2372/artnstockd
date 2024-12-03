package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.OrderMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderMasterDao extends MongoRepository<OrderMaster,String> {
    Optional<OrderMaster> findByOrderId(String orderId);

    List<OrderMaster> findByUserMaster_UserId(String userId);

    OrderMaster findByCartArtFrameMaster_CartArtFrameIdAndOrderId(String cartArtFrameId, String orderId);

    @Query("{orderId :?0}")
    OrderMaster getOrder(@Param("orderId") String orderId);

    List<OrderMaster> findByCartArtFrameMaster_Status(String status);

    OrderMaster findByCartArtFrameMaster_CartArtFrameUniqueNo(String cartArtFrameUniqueNo);

    OrderMaster findByCartProductMaster_CartProductIdAndOrderId(String cartProductId, String orderId);

    List<OrderMaster> findAllByUserMaster_UserId(String userId);
}


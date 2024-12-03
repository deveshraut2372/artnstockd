package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.CartAdminArtProductMaster;
import com.zplus.ArtnStockMongoDB.model.CartArtFrameMaster;
import com.zplus.ArtnStockMongoDB.model.CartMaster;
import com.zplus.ArtnStockMongoDB.model.CartProductMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartDao extends MongoRepository<CartMaster,String> {
    List<CartMaster> findByUserMasterUserId(String userId);

    CartMaster findByUserMaster_UserId(String userId);

    Optional<CartMaster> findByCartId(String cartId);

    CartMaster findByUserMasterUserIdAndStatus(String userId, String active);

    @Query("{cartId :?0}")
    CartMaster getCart(@Param("cartId") String cartId);

    @Query("{cartId :?0}")
    List<CartArtFrameMaster> getCartId(String cartId);

    CartMaster findByCartProductMasterCartProductId(String cartProductId);

//    CartMaster findByCartArtFrameMasterCartArtFrameId(String cartProductId);



    @Query("{cartId :?0}")
    List<CartProductMaster> getCartMasterId(String cartId);


    CartMaster findByCartArtFrameMasterCartArtFrameId(String cartArtFrameId);

    @Query("{cartId :?0}")
    List<CartArtFrameMaster> getCartArtFramesMasterId(String cartId);

    CartMaster findByCartAdminArtProductMasterCartAdminArtProductId(String cartAdminArtProductId);

    @Query("{cartId :?0}")
    List<CartAdminArtProductMaster> getCartAdminArtProductMasterId(String cartId);

    CartMaster findByUserMaster_UserIdAndCartId(String userId, String cartId);


//    int getCountArtFrames(String cartId, String userId);
}



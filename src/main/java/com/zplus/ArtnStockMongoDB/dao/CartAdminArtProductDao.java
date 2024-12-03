package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.CartAdminArtProductMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CartAdminArtProductDao extends MongoRepository<CartAdminArtProductMaster,String> {
    List<CartAdminArtProductMaster> findByStatusAndUserMaster_UserId(String incart, String userId);

    CartAdminArtProductMaster findByCartAdminArtProductId(String cartProductId);


    int countByUserMaster_UserIdAndStatus(String userId, String incart);

    List<CartAdminArtProductMaster> findAllByCartMaster_CartIdAndStatus(String cartId, String delete);

//    @Query("{cartArtFrameId :?0}")
//    @Query("{cartAdminArtProductId :?0 }")
    @Query("{cartAdminArtProductId : 0?}")
    CartAdminArtProductMaster getById(String deleteId);
}

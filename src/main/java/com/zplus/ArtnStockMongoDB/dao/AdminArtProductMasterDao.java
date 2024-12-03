package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.AdminArtProductMaster;
import com.zplus.ArtnStockMongoDB.model.UserMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AdminArtProductMasterDao extends MongoRepository<AdminArtProductMaster,String> {


    Optional<AdminArtProductMaster> findByAdminArtProductId(String adminArtProductId);


    List<AdminArtProductMaster> findAllByStatus(String active);

    @Query("{status:?0}")
    List getAllByStatus(String status);


    @Query("{adminArtProductId :?0}")
    AdminArtProductMaster getAdminArtProductMaster(String id);

    List<AdminArtProductMaster> findAllByProductMaster_productId(String productId);

    List<AdminArtProductMaster> findAllByUserMaster(UserMaster userMaster);

    List<AdminArtProductMaster> findAllByStatusAndUserMaster(String approved, UserMaster userMaster);
}

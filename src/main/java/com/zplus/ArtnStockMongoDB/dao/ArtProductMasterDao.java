package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.AdminArtProductMaster;
import com.zplus.ArtnStockMongoDB.model.ArtProductMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtProductMasterDao extends MongoRepository<ArtProductMaster,String> {

    @Query("{artProductId :?0}")
    ArtProductMaster getArtProductMaster(String artProductId);

    List findAllByStatus(String active);

    Optional<ArtProductMaster> findByArtProductId(String artProductId);


//    ArtProductMaster findByProductMasterProductIdAndArtMasterArtId(String productId, String artId);

}

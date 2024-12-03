package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.CollectionMaster;
import com.zplus.ArtnStockMongoDB.model.UserMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollectionMasterDao extends MongoRepository<CollectionMaster,String> {

    @Query("{collectionId :?0}")
    CollectionMaster getCollection(String collectionId);

    List<CollectionMaster> findByStatus(String active);

    List findAllByStatus(String active);

    List<CollectionMaster> findByUserMasterUserId(String userId);

    Optional<CollectionMaster> findByCollectionId(String collectionId);

    List<CollectionMaster> findByTitle(String text);

    List<CollectionMaster> findByArtMaster_ArtNameContaining(String searchText);

    List<CollectionMaster> findAllByUserMaster(UserMaster userMaster);
}


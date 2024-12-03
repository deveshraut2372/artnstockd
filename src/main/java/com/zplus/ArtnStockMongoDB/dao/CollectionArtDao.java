package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.CollectionArtMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionArtDao extends MongoRepository<CollectionArtMaster,String> {
    List<CollectionArtMaster> findAllByCollectionId(String collectionId);
}

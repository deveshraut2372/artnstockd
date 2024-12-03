package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.CollectionAdminArtProductMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionAdminArtProductDao extends MongoRepository<CollectionAdminArtProductMaster,String> {

    List<CollectionAdminArtProductMaster> findAllByCollectionId(String collectionId);
}

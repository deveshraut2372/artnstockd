package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.DynamicHomepageContentMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface DynamicHomepageContentDao extends MongoRepository<DynamicHomepageContentMaster,String> {

//    DynamicHomepageContentMaster findByType(String type);

    Optional<DynamicHomepageContentMaster> findByDynamicHomepageContentId(String dynamicHomepageContentId);

    @Query("{type :?0}")
    DynamicHomepageContentMaster getType(String type);

    Optional<DynamicHomepageContentMaster> findByType(String type);

    @Query(value = "{'type':?0}",fields = "{'gridTitle':1'}")
    DynamicHomepageContentMaster updateGridTitle(String type);
}

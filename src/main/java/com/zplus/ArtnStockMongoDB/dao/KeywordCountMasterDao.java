package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.KeywordCountMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KeywordCountMasterDao extends MongoRepository<KeywordCountMaster,String> {
    Optional<KeywordCountMaster> findByKeywordCountId(String keywordCountId);

    KeywordCountMaster findByKeyword(String s);

    List<KeywordCountMaster> findByKeywordContaining(String keyword);
}

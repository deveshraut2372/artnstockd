package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.FrequentlyAskedQuestionsMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FrequentlyAskedQuestionsDao extends MongoRepository<FrequentlyAskedQuestionsMaster,String> {
    List findAllByStatus(String active);

    List<FrequentlyAskedQuestionsMaster> findByType(String type);
}


package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.ContributorEarning;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContributorEarningDao extends MongoRepository<ContributorEarning, Integer> {
    List<ContributorEarning> findByUserId(String userid);
}

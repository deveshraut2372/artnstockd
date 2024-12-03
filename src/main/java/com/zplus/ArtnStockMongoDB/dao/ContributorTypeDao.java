package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.ContributorTypeMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContributorTypeDao extends MongoRepository<ContributorTypeMaster,String> {


    List findAllByStatus(String active);
}

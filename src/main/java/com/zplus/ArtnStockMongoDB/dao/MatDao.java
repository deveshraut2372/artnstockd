package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.MatMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatDao extends MongoRepository<MatMaster,String> {

    List findByStatus(String active);

    MatMaster findByMatType(String matType);

}


package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.PrintingSizeMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrintingSizeMasterDao extends MongoRepository<PrintingSizeMaster,String>{

    List<PrintingSizeMaster> findByPrintingSizeStatus(String active);
}

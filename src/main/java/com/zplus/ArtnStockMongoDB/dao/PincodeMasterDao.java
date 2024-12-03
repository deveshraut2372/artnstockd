package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.PincodeMaster;
import com.zplus.ArtnStockMongoDB.model.ProductMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PincodeMasterDao extends MongoRepository<PincodeMaster,String> {
    
    List findByStatus(String active);

    PincodeMaster findByPinCode(Integer pinCode);

    List<PincodeMaster> findAllByStatus(String active);
}


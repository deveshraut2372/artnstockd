package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.GiftCodeMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiftCodeDao extends MongoRepository<GiftCodeMaster, String> {
    List findByStatus(String active);


    GiftCodeMaster findByGiftCode(String giftCode);

    List findAllByStatus(String status);
}



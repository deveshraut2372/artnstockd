package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.PromoCodeMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromoCodeDao extends MongoRepository<PromoCodeMaster, String> {
    List findByStatus(String active);

    PromoCodeMaster findByPromoCode(String promoCode);

    PromoCodeMaster findByPromoCodeAndStatus(String promoCode, String active);
}



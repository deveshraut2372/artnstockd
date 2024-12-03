package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.UserPromoCodeMaster;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserPromoCodeDao extends MongoRepository<UserPromoCodeMaster, String> {
    UserPromoCodeMaster findByUserMasterUserIdAndPromoCode(String userId, String promoCode);

    List findByUserMasterUserId(String userId);

    UserPromoCodeMaster findByUserMaster_UserIdAndPromoCode(String userId, String promoCode);

    UserPromoCodeMaster findByPromoCode(String promoCode);

    void deleteByPromoCode(String promoCode);
}



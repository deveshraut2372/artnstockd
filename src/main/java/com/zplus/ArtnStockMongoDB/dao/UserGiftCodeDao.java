package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.UserGiftCodeMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGiftCodeDao extends MongoRepository<UserGiftCodeMaster,String> {
    List findByUserMaster_UserId(String userId);

    UserGiftCodeMaster findByUserMaster_UserIdAndGiftCode(String userId, String giftCode);

    void deleteByGiftCode(String giftCode);
}



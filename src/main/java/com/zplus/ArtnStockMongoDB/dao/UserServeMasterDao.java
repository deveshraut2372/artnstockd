package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.UserServeMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserServeMasterDao extends MongoRepository<UserServeMaster,String> {



    UserServeMaster findByUserMaster_userId(String userId);

    List<UserServeMaster> findAllByUserMaster_UserId(String userId);

}

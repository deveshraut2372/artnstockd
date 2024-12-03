package com.zplus.ArtnStockMongoDB.dao;
import com.zplus.ArtnStockMongoDB.model.NotificationMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


    @Repository
    public interface NotificationMasterDao extends MongoRepository<NotificationMaster, String>
    {



    }

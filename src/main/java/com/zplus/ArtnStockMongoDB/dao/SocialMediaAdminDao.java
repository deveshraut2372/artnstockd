package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.SocialMediaAdminMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocialMediaAdminDao extends MongoRepository<SocialMediaAdminMaster,String> {


    List<SocialMediaAdminMaster> findAllByStatus(String active);
}

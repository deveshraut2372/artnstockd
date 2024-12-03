package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.BannerMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerDao extends MongoRepository<BannerMaster,String> {
    List findAllByStatus(String active);

    List findByBannerType(String bannerType);
}

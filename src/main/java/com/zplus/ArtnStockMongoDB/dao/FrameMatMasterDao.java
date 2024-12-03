package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.dto.res.FrameMatMasterResDto;
import com.zplus.ArtnStockMongoDB.model.FrameMatMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FrameMatMasterDao extends MongoRepository<FrameMatMaster,String> {

    List findByStatus(String active);
}

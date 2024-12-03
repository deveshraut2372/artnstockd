package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.ReleaseFileMaster;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReleaseFileMasterDao extends MongoRepository<ReleaseFileMaster,String> {
}

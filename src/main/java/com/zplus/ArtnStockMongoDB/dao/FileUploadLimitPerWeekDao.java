package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.FileUploadLimitPerWeekMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileUploadLimitPerWeekDao extends MongoRepository<FileUploadLimitPerWeekMaster,String> {
}

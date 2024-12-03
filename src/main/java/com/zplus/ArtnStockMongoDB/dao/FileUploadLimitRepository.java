package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.FileUploadLimitMaster;
import com.zplus.ArtnStockMongoDB.model.UserMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface FileUploadLimitRepository extends MongoRepository<FileUploadLimitMaster, String> {

    FileUploadLimitMaster findByUserMaster(UserMaster userMaster);
    FileUploadLimitMaster findByUserMaster_UserId(String userId);
}

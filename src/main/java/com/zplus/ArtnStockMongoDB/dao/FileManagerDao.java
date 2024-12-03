package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.ArtMaster;
import com.zplus.ArtnStockMongoDB.model.ContributorImageUploadMaster;
import com.zplus.ArtnStockMongoDB.model.FileManagerMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileManagerDao extends MongoRepository<FileManagerMaster,String> {

    List findAllByStatus(String active);

    Optional<FileManagerMaster> findByFileManagerId(String fileManagerId);

    List<FileManagerMaster> findByUserMasterUserId(String userId);

    List<FileManagerMaster> findByUserMaster_UserId(String userId);

    List<FileManagerMaster> findByTitle(String text);

    List<FileManagerMaster> findByArtMaster_ArtNameContaining(String searchText);

    List<FileManagerMaster> findAllByUserMaster_UserIdAndArtMaster_ArtId(String userId, String id);

    List<FileManagerMaster> findAllByUserMaster_UserIdAndAdminArtProductMaster_AdminArtProductId(String userId, String id);

//    List<FileManagerMaster> findAllByArtMaster_ArtId(String id);

//    List<FileManagerMaster> findAllByAdminArtProductMaster_AdminArtProductId(String id);

 
//    @Query(value = "{ 'userMaster.userId' : ?0 }", fields = "{ 'fileManagerId' : 1, 'title' : 1 }")
//    List<FileManagerMaster> getUserIdWiseData(String userId);
}


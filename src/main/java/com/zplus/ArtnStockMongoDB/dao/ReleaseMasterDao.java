package com.zplus.ArtnStockMongoDB.dao;
import com.zplus.ArtnStockMongoDB.model.ReleaseMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


import java.util.List;

public interface ReleaseMasterDao extends MongoRepository<ReleaseMaster,String> {
    List<ReleaseMaster> findByType(String type);

    List<ReleaseMaster> findByUserMaster_UserId(String userId);


    @Query("{'releaseId':0?}")
    ReleaseMaster getById(String release);

    List<ReleaseMaster> findAllByAddDetailsMaster_ArtDetailsId(String artDetailsId);

    List<ReleaseMaster> findByUserMaster_UserIdAndType(String userId, String type);

    @Query("{ 'fileName': { '$regex': ?0, '$options': 'i' } }")
    List<ReleaseMaster> getReleasesByFileName(String fileName);

    List<ReleaseMaster> findAllByUserMaster_UserId(String userId);

    List<ReleaseMaster> findAllByUserMaster_UserIdAndReleaseStatus(String userId, String releaseStatus);

    List<ReleaseMaster> findByUserMaster_UserIdAndTypeAndReleaseStatus(String userId, String type, String active);
}

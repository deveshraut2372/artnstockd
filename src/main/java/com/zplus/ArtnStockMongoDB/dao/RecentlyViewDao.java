package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.RecentlyViewMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface RecentlyViewDao extends MongoRepository<RecentlyViewMaster,String> {

    Optional<RecentlyViewMaster> findByRecentlyViewId(String recentlyViewId);

    void deleteByUserMaster_UserId(String userId);

    @Query(value = "{'userMaster.userId':?0 , 'artMaster.artId':?1}")
    boolean existsByUserIdAndArtId(String userId, String artId);

//    RecentlyViewMaster findByUserMasterUserIdAndArtMasterArtId(String userId, String artId);

    RecentlyViewMaster findByUserMaster_UserIdAndArtMaster_ArtId(String userId, String artId);
}

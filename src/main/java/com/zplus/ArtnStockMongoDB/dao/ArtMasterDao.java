package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.ArtMaster;
import com.zplus.ArtnStockMongoDB.model.ReleaseMaster;
import com.zplus.ArtnStockMongoDB.model.UserMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArtMasterDao extends MongoRepository<ArtMaster, String> {

    Optional<ArtMaster> findByArtId(String artId);

    List<ArtMaster> findAllByStatus(String active);

    Page<ArtMaster> findAllByStatus(String active, PageRequest pageable);

    List<ArtMaster> findByUserMasterUserId(String userId);

    List findByStyleMasterStyleId(String styleId);

//    List<ArtMaster> findByStyleMasterSubjectMasterSubjectIdAndStatus(String subjectId, String active);

    @Query("{artId :?0}")
    ArtMaster getArtId(String artId);

    @Query("{'keywords' : { '$regex' : ?0 , $options: 'i'}}")
    List<ArtMaster> findAllByKeywordsLikeSearchText(String searchText);

    List findBySubjectMasterSubjectId(String subjectId);

    Collection<ArtMaster> findBySubjectMasterSubjectIdAndStatus(String subjectId, String active);

    @Query("{'artName' : { '$regex' : ?0 , $options: 'i'}}")
    List<ArtMaster> findAllByArtNameLikeSearchText(String searchText);

    List<ArtMaster> findByStatusAndUserMasterUserId(String approved, String userId);

//    @Query("{'userMaster.userFirstName' ,'userMaster.userRole:customer' : { '$regex' : ?0 , $options: 'i'}}")
//    List<ArtMaster> findAllByUserMaster_UserRoleAndUserMaster_UserFirstNameLikeSearchText(String customer, String searchText);

//    @Query(value = "{'userMaster.userFirstName' :searchText}", fields = "{ '$regex' : ?0 , $options: 'i'}")
//    List<ArtMaster> findAllByUserMaster_UserFirstNameLikeSearchText(String searchText);

    @Query("{'userMaster.userFirstName' : { '$regex' : ?0 , $options: 'i'}}")
    List<ArtMaster> findByUserMasterUserFirstNameLikeUserFirstName(String userFirstName);

    List<ArtMaster> findByImage(String image);

    List<ArtMaster> findByArtIdIn(List<String> artMasterIds);

    List<ArtMaster> findBySubjectMasterSubjectNameContainingOrStyleMasterNameContainingOrArtNameContaining(String text, String text1, String text2);

    List<ArtMaster> findByUserMaster_UserIdAndStatus(String userId, String status);

    Optional<ArtMaster> findByUserMaster_UserId(String userId);

    List<ArtMaster> findAllByStatusAndUserMaster(String status, UserMaster userMaster);

    List<ArtMaster> findAllByUserMaster(UserMaster userMaster);

    List<ArtMaster> findAllByUserMaster_UserId(String userId);

    List<ArtMaster> findAllByUserMaster_UserIdAndArtIdContaining(String userId, String keyword);

    List<ReleaseMaster> findAllReleaseMasterListByUserMaster_UserIdAndStatus(String userId, String approved);

    List<ArtMaster> findAllByStatusAndUserMaster_Status(String status, String active);

    List<ArtMaster> findAllByUserMaster_UserIdAndStatus(String userId, String approved);


//    List<ArtMaster> findByViewedAtGreaterThanEqualOrderByViewedAtDesc(Date timestamp);

}


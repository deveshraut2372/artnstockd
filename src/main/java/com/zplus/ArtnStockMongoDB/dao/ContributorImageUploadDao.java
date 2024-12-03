package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.ContributorImageUploadMaster;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContributorImageUploadDao extends MongoRepository<ContributorImageUploadMaster,String> {

    Optional<ContributorImageUploadMaster> findByContributorImageUploadId(String contributorImageUploadId);

    @Query(value = "{ 'userMaster.userId' : ?0 }", fields = "{ '_id' : 1,'imageURL' : 1, 'imageFlag' : 1, 'status' : 1 }")
    List<ContributorImageUploadMaster> findByUserMasterUserIdIncludeIdAndImageURLAndImageFlagAndStatusFields(String userId);

    @Query(value = "{ 'userMaster.userId' : ?0 , 'imageFlag' : true}", fields = "{ 'imageURL' : 1, 'imageFlag' : 1, 'status' : 1, '_id' : 1 }")
    List<ContributorImageUploadMaster> findAllByUserMasterUserIdIncludeImageURLAndImageFlagAndStatusAndIdFields(String userId);

    @Query(value = "{ 'status' : ?0 }", fields = "{ '_id' : 1,'imageURL' : 1, 'imageFlag' : 1, 'status' : 1 }")
    List<ContributorImageUploadMaster> getMaster(String status);
}

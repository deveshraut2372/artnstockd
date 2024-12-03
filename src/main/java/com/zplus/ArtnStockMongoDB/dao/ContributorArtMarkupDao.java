package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.ContributorArtMarkupMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContributorArtMarkupDao extends MongoRepository<ContributorArtMarkupMaster,String> {

    List<ContributorArtMarkupMaster> findByArtMaster_ArtId(String artId);

    ContributorArtMarkupMaster findByArtMasterArtId(String artId);

//    @Query("{'artMaster._id': ?0, 'artMaster.orientation': ?1, 'artMaster.size': ?2}")
//    ContributorArtMarkupMaster getArtWiseData(String artId, String orientation, String size);



}

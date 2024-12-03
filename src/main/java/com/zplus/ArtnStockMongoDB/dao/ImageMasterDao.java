package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.ImageMaster;
import org.bson.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageMasterDao extends MongoRepository<ImageMaster, String> {

    @Query("{imageId :?0}")
    ImageMaster getImage(@Param("imageId") String imageId);

    List<ImageMaster> findAllByImageIdIn(List<String> imageId);

    Document findByImageId(String imageId);

    List<ImageMaster> findBypHashIdRegex(String regexPattern);

    @Query("select im from ImageMaster im")
    List<ImageMaster> customQuery();
}



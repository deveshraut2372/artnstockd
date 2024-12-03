package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.AddDetailsMaster;
import com.zplus.ArtnStockMongoDB.model.ImageMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtDetailsMasterDao extends MongoRepository<AddDetailsMaster,String> {

    AddDetailsMaster findByImageMaster(ImageMaster imageMaster);

    boolean existsByImageMaster_ImageId(String imageId);

    @Query("{artDetailsId:?0}")
    AddDetailsMaster getArtById(String artDetailsId);
}

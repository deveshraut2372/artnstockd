package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.OrientationMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrientationMasterDao extends MongoRepository<OrientationMaster,String> {

    List<OrientationMaster> findByShapeStatus(String active);

    List<OrientationMaster> findByShape(String shape);

    OrientationMaster findByShapeAndHeightAndWidth(String shape, String height, String width);
}


package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.FrameMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FrameDao extends MongoRepository<FrameMaster,String> {

    Optional<FrameMaster> findByFrameId(String frameId);


    @Query(value = "{'status' : 'Active'}")
    List findByStatus();
}

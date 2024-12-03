package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.CustomerReviewMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface CustomerReviewMasterDao extends MongoRepository<CustomerReviewMaster,String> {
    
    List<CustomerReviewMaster> findByStatus(String active);

    List findByArtMasterArtId(String artId);

    @Query("{'$expr': {'$and': [{'$eq': [{'$dayOfMonth': '$date'}, {$dayOfMonth: ?0}]}, {'$eq': [{'$month': '$date'}, {$month: ?0}]}, {'$eq': [{'$year': '$date'}, {$year: ?0}]}]}}")
    List<CustomerReviewMaster> findByDateToday(@Param("date") Date date);

    @Query("{'$expr': {'$eq': [{'$year': '$date'}, ?0]}}")
    List<CustomerReviewMaster> findByYear(int year);

    @Query("{'$expr': {'$eq': [{'$month': '$date'}, ?0]}}")
    List<CustomerReviewMaster> findByMonth(Integer month);

//    List findByArtProductMasterArtProductId(String artProductId);
}

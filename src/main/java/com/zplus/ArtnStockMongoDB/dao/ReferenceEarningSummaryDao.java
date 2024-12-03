package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.dto.res.ReferenceEarningSummaryResDto;
import com.zplus.ArtnStockMongoDB.model.ReferenceEarningSummary;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReferenceEarningSummaryDao extends MongoRepository<ReferenceEarningSummary, String> {
    List<ReferenceEarningSummary> findByUserId(String userid);

//    List<ReferenceEarningSummaryResDto> findByUserdataUserIdAndFirstPurchaseMonthAndFirstPurchaseYear(String userId, int month, int year);

//    @Query("{ 'userMaster.userId': ?0, '$expr': { '$eq': [{ '$month': '$firstPurchase' }, ?1], '$eq': [{ '$year': '$firstPurchase' }, ?2] } }")
//    List<ReferenceEarningSummary> findByUserIdAndMonthYear(String userId, int month, int year);
//@Aggregation(pipeline = {"{ $match: { $and: [ { 'userdata.userId': :#{#userId} }, { 'firstPurchase': { $eq: :#{#month} } }, { 'firstPurchase': { $eq: :#{#year} } } ] } }", "{ $project: { 'clientdata.clientName': 1, 'refereceId': 1, 'clientdata.clientRole': 1, 'registedDate': 1, 'firstPurchase': 1, 'salePrice': 1, 'taxDeduction': 1, 'yourEarning': 1, 'paymentStatus': 1, 'userdata.userId': 1, 'clientdata.clientId': 1 } }"})
//List<ReferenceEarningSummaryResDto> getReferenceEarningSummaryData(@Param("userId") String userId, @Param("month") int month, @Param("year") int year);


}
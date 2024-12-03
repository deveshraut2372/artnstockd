package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.DateMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
public interface DateMasterDao extends MongoRepository<DateMaster,String> {

    DateMaster findByDate(String stringDate1);

    List<DateMaster> findByUserMasterUserId(String userId);

    DateMaster findByDateAndUserMasterUserId(String stringDate1, String userId);

//    List<DateMaster> findByUserMaster_UserIdAndDateBetween(String userId, String strDate, String strDate1);

    List<DateMaster> findByUserMaster_UserId(String userId, int current_week);

    List<DateMaster> findByUserMaster_UserIdAndDateBetween(String userId, String strDate, String strDate1);


//    @Query("{'date' : { $gte: ?0, $lte: ?1 } }")
//    List<DateMaster> findByDateBetween(LocalDate monday, LocalDate sunday);


//    @Query("{'date' : { $gte: ?0, $lte: ?1 } }")
//    List<DateMaster> findByDateBetween( String strDate, String date);


}



//{ "userMaster.getUserId" : "638d9e07eb133a55dc551cf5", "$and" : [{ "date" : { "$lt" : { "$date" : "2022-12-09T10:32:53.126Z"}}}, { "date" : { "$gte" : "2022-12-02"}}]}

//    @Query("{'date' : { $gte: ?0, $lte: ?1 } }")
//    public List<AnyYourObj> getObjectByDate(Date from, Date to);
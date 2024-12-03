//package com.zplus.ArtnStockMongoDB.dao;
//
//import com.zplus.ArtnStockMongoDB.model.CommonMaster;
//import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.data.mongodb.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface CommonDao extends MongoRepository<CommonMaster,String> {
//
//    List findAllByStatus(String active);
//
//    List findByArtMasterArtId(String artId);
//
//    List findByProductMasterProductId(String productId);
//
//    @Query("{commonId :?0}")
//    CommonMaster getCommonMaster(String commonId);
//
//    @Query("{commonId :?0}")
//    CommonMaster getCommonId(String commonId);
//}
//

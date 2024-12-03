package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.PrintingMaterialMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrintingMaterialMasterDao extends MongoRepository<PrintingMaterialMaster,String> {

    List<PrintingMaterialMaster> findByPrintingMaterialStatus(String active);
}

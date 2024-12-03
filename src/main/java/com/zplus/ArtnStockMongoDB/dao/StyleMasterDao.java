package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.StyleMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface StyleMasterDao extends MongoRepository<StyleMaster,String> {


    List findAllByStatus(String active);

    List<StyleMaster> findByArtDropdown(boolean artDropdown);

    @Query("{styleId :?0}")
    StyleMaster getStyle(String styleId);
}

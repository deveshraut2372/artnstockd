package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.ComboMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComboDao extends MongoRepository<ComboMaster,String> {
    List findAllByStatus(String active);

    Optional<ComboMaster> findByComboId(String comboId);

    List findByArtProductMasterArtProductId(String artProductId);


    List findByAdminArtProductMaster_AdminArtProductId(String adminArtProductId);

    Boolean existsByComboNo(String comboNo);
}

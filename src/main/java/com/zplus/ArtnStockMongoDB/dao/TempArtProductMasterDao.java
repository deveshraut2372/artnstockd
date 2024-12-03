package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.TempArtProductMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TempArtProductMasterDao extends MongoRepository<TempArtProductMaster,String > {
    Optional<TempArtProductMaster> findByTempArtProductId(String tempArtProductId);

    List findAllByStatus(String active);

    List findAllByUserMaster_UserId(String userId);


//    @Query("{'productMaster.productId': ?0, 'artDetailMaster.artDetailsId': ?1}")
//    TempArtProductMaster checkArtProductIdCreate(String productId, String artDetailsId);

    TempArtProductMaster findByProductMaster_ProductIdAndAddDetailsMaster_ArtDetailsId(String productId, String artDetailsId);

    List<TempArtProductMaster> findAllByProductMaster_ProductId(String productId);

    List<TempArtProductMaster> findAllByProductMaster_ProductIdAndProductSubCategoryMaster_ProductSubCategoryIdAndUserMaster_UserId(String productId, String productSubCategoryId, String userId);

    List<TempArtProductMaster> findAllByProductMaster_ProductIdAndProductSubCategoryMaster_ProductSubCategoryIdAndUserMaster_UserIdAndStatus(String productId, String productSubCategoryId, String userId, String active);

    TempArtProductMaster findByProductSubSubCategory_productSubSubCategoryIdAndUserMaster_UserId(String productSubSubCategoryId, String userId);

    List<TempArtProductMaster> findAllByAddDetailsMaster_artDetailsIdAndUserMaster_UserId(String artDetailsId, String userId);
}

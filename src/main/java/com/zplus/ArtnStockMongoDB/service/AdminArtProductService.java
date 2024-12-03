package com.zplus.ArtnStockMongoDB.service;


import com.zplus.ArtnStockMongoDB.dto.req.AdminArtProductRequest;
import com.zplus.ArtnStockMongoDB.dto.req.ArtMasterFilterReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.ArtProductReq;
import com.zplus.ArtnStockMongoDB.model.AdminArtProductMaster;

import java.util.List;
import java.util.Optional;

public interface AdminArtProductService {

    Boolean createAdminArtProductMaster(AdminArtProductRequest adminArtProductRequest);

    List<AdminArtProductMaster> getAllAdminArtProductMaster();

    List<AdminArtProductMaster> getActiveArtProducts();

    Optional<AdminArtProductMaster> getByAdminArtProductId(String adminArtProductId);

    Boolean updateAdminArtProductMaster(AdminArtProductRequest adminArtProductRequest);

    List<AdminArtProductMaster> getAdminArtProductsByStatus(String status);

    List<AdminArtProductMaster> getAdminArtProductsBySubCatagoryIdWise(String productSubCategoryId);

    AdminArtProductMaster getAdminArtProductMaster(String adminArtProductId);

    List AdminArtProductFilter(ArtMasterFilterReqDto artMasterFilterReqDto, String type, String text);

    List<AdminArtProductMaster> getAllAdminArtProducts(ArtProductReq artProductReq);
}

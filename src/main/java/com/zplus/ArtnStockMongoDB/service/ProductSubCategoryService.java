package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.ProductSubCategoryReqDto;
import com.zplus.ArtnStockMongoDB.model.ProductSubCategoryMaster;

import java.util.List;

public interface ProductSubCategoryService {
    Boolean createProductSubCategory(ProductSubCategoryReqDto productSubCategoryReqDto);

    Boolean updateProductSubCategory(ProductSubCategoryReqDto productSubCategoryReqDto);

    List getAllProductSubCategory();

    ProductSubCategoryMaster editProductSubCategory(String productSubCategoryId);

    List getActiveProductSubCategory();

    List<ProductSubCategoryMaster> getProductMainCategoryIdWiseProductSubCategory(String productMainCategoryId);

    List<ProductSubCategoryMaster> getTypeWiseList(String type);
}


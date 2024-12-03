package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.ProductSubSubCategoryReq;
import com.zplus.ArtnStockMongoDB.model.ProductSubSubCategory;

import java.util.List;
import java.util.Map;

public interface ProductSubSubCategoryService {


    Map createProductSubSubCategory(ProductSubSubCategoryReq productSubSubCategoryReq);

    Map<String, String> updateProductSubSubCategory(ProductSubSubCategoryReq productSubSubCategoryReq);

    List<ProductSubSubCategory> getAllByProductSubCategoryId(String productSubCategoryId);

    List<ProductSubSubCategory> getAll();

    List<ProductSubSubCategory> getByProductSubCategoryId(String productSubCategoryId);
}

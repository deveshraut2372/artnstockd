package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.ProductMainCategoryReqDto;
import com.zplus.ArtnStockMongoDB.model.ProductMainCategoryMaster;

import java.util.List;

public interface ProductMainCategoryService {
    Boolean createProductMainCategory(ProductMainCategoryReqDto productMainCategoryReqDto);

    Boolean updateProductMainCategory(ProductMainCategoryReqDto productMainCategoryReqDto);

    List getAllProductMainCategory();

    ProductMainCategoryMaster editProductMainCategory(String productMainCategoryId);

    List getActiveProductMainCategory();
}

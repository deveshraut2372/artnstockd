package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.ProductStyleReq;
import com.zplus.ArtnStockMongoDB.model.ProductStyle;

import java.util.List;

public interface ProductStyleService {
    Boolean createProductStyle(ProductStyleReq productStyleReq);

    Boolean updateProductStyle(ProductStyleReq productStyleReq);

    List getAllProductStyles();

    ProductStyle getByProductStyleId(String productStyleId);

    Boolean deleteByProductStyleId(String productStyleId);

    List<ProductStyle> getAllStylesByProductSubSubCategoryId(String productSubSubCategoryId);
}

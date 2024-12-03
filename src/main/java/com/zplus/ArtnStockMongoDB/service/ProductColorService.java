package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.ProductColorReq;
import com.zplus.ArtnStockMongoDB.dto.res.ProductColorRes;
import com.zplus.ArtnStockMongoDB.model.ProductColor;

import java.util.List;

public interface ProductColorService {
    Boolean createProductColor(ProductColorReq productColorReq);

    Boolean updateProductColor(ProductColorReq productColorReq);

    List getAllProductColors();

    ProductColor getByProductColorId(String productColorId);

    Boolean deleteByProductColorId(String productColorId);

    List<ProductColor> getByProductColorByProductStyleId(String productStyleId);

    ProductColorRes getByProductColorsByProductStyleId(String productStyleId);
}

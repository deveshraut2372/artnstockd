package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.ProductReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.UpdateProductReqDto;
import com.zplus.ArtnStockMongoDB.model.ProductMaster;

import java.util.List;

public interface ProductMasterService {
    Boolean createProductMaster(ProductReqDto productReqDto);

    Boolean updateProductMaster(UpdateProductReqDto updateProductReqDto);

    List getAllProductMaster();

    ProductMaster editProductMaster(String productId);

    List getActiveProductMaster();

    List<ProductMaster> getProductSubCategoryIdWiseProductMasterList(String productSubCategoryId);



//    List getPopularStatusWiseList();

}

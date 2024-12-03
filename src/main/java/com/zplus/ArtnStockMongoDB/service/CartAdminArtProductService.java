package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.AddToCartProductRequest;
import com.zplus.ArtnStockMongoDB.dto.req.AdminArtProductReq;
import com.zplus.ArtnStockMongoDB.dto.req.CartDeleteReq;
import com.zplus.ArtnStockMongoDB.dto.res.CartAdminArtProductRes;
import com.zplus.ArtnStockMongoDB.model.CartAdminArtProductMaster;

public interface CartAdminArtProductService {
    CartAdminArtProductRes saveCartAdminArtProduct(AdminArtProductReq adminArtProductReq);

    Boolean IncreaseCartQty(String cartAdminArtProductId);

    Boolean DecreaseCartQty(String cartAdminArtProductId);

    Boolean deleteCartAdminProduct(String cartAdminArtProductId);



//    Boolean updateCartAdminArtProduct(AdminArtProductReq adminArtProductReq);
}

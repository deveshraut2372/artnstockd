package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.UpdateEstimateAmountGiftCodeRequest;
import com.zplus.ArtnStockMongoDB.dto.req.*;
import com.zplus.ArtnStockMongoDB.dto.res.*;
import com.zplus.ArtnStockMongoDB.model.CartMaster;
import com.zplus.ArtnStockMongoDB.model.CartProductMaster;

import java.util.List;
import java.util.Map;

public interface CartMasterService {

//    Boolean deleteCart(String cartProductId);

    CartMaster getUserIdWiseCartData(String userId);

    List<CartProductMaster> getUserIdWiseCartProductData(String userId);

    Boolean saveCartProduct(AddToCartProductRequest addToCartProductRequest);

    Message updateCartStatus(String cartId);

    UserWiseCartResponse UserWiseGetTotalQty(String userId);

    Message CartIdWiseDeleteCartMaster(String cartId);

    UpdateOrderStatusRes UpdateEstimateAmountUsingPromoCode(UpdateEstimateAmountRequest updateEstimateAmountRequest);

    UpdateOrderStatusRes UpdateEstimateAmountUsingGiftCode(UpdateEstimateAmountGiftCodeRequest updateEstimateAmountGiftCodeRequest);

    Boolean IncreaseCartQty(String cartProductId);

    Boolean DecreaseCartQty(String cartProductId);

    List<CartMaster> getAll();

    Boolean deleteCart(String cartProductId);

    Message updateCartShippingMethod(UpdateCartShippingMethodReq updateCartShippingMethodReq);

    CartMasterResponseDto get(String userId);

    BuyNowRes BuyNow(BuyNowReq buyNowReq);

    CartMasterRes getUserIdWiseCartData1(String userId);

    Boolean delete(CartDeleteReq cartDeleteReq);

    Boolean IncreaseCartQtyByType(String cartProductId, String type);

    Boolean DecreaseCartQtyByType(String cartProductId, String type);

    Boolean tempDelete(CartDeleteReq cartDeleteReq);

    CartMasterRes getUserIdWisetempDeleteCartDetails(String userId);

    Boolean updateStatus(CartUpdateReq cartUpdateReq);

    BuyNowRes BuyNowNew(BuyNowReqq buyNowReqq);

    Map<String ,Object> getCartTotalAmount(String userId);

}

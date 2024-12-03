package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.*;
import com.zplus.ArtnStockMongoDB.dto.res.OrderCartProductResDto;
import com.zplus.ArtnStockMongoDB.dto.res.OrderResDto;
import com.zplus.ArtnStockMongoDB.dto.res.UpdateOrderStatusRes;
import com.zplus.ArtnStockMongoDB.dto.res.UserOrderRes;
import com.zplus.ArtnStockMongoDB.model.CartArtFrameMaster;
import com.zplus.ArtnStockMongoDB.model.OrderMaster;

import java.util.Date;
import java.util.List;

public interface OrderMasterService {
    Boolean createOrderMaster(OrderMasterRequest orderMasterRequest);

    List getUserIdWiseOrderList(String userId);

    OrderMaster editOrderMaster(String orderId);

    UpdateOrderStatusRes updateOrderStatus(OrderStatusUpdateRequest orderStatusUpdateRequest);

    OrderMaster getCartArtFrameIAndOrderIdWiseOrderList(String cartArtFrameId, String orderId);

    List<OrderResDto> getUserIdWiseCartArtFrameMasterList(String userId);

    List<OrderMaster> getAllOrderList();

    List<OrderResDto> getStatusWiseOrderList(String status);

    OrderResDto getCartArtFrameUniqueNoWiseCartArtFrameMaster(String cartArtFrameUniqueNo, String orderId);

    OrderMaster getCartProductIAndOrderIdWiseOrderList(String cartProductId, String orderId);

    OrderCartProductResDto getCartProductUniqueNoWiseData(String cartProductNo, String orderId);

    UpdateOrderStatusRes updateOrderStatusCartArtProductWise(OrderStatusUpdateArtProductRequest orderStatusUpdateArtProductRequest);

    Boolean createSingleOrder(SingleOrderMasterRequest singleOrderMasterRequest);

    UserOrderRes getAllOrdersByUserId(String userId);

    UserOrderRes getAllOrdersByUserIdAndDate(OrderFilterReq orderFilterReq);


//    UpdateOrderStatusRes updateOrderStatus(OrderStatusUpdateRequest orderStatusUpdateRequest);

}

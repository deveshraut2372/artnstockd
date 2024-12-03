package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.*;
import com.zplus.ArtnStockMongoDB.dto.res.OrderCartProductResDto;
import com.zplus.ArtnStockMongoDB.dto.res.OrderResDto;
import com.zplus.ArtnStockMongoDB.dto.res.UpdateOrderStatusRes;
import com.zplus.ArtnStockMongoDB.dto.res.UserOrderRes;
import com.zplus.ArtnStockMongoDB.model.CartArtFrameMaster;
import com.zplus.ArtnStockMongoDB.model.MatMaster;
import com.zplus.ArtnStockMongoDB.model.OrderMaster;
import com.zplus.ArtnStockMongoDB.service.OrderMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/order_master")
public class OrderMasterController {

    @Autowired
    private OrderMasterService orderMasterService;

    @PostMapping("/create")
    public ResponseEntity createOrderMaster(@RequestBody OrderMasterRequest orderMasterRequest) {
            Boolean flag = orderMasterService.createOrderMaster(orderMasterRequest);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createSingleOrder")
    public ResponseEntity createSingleOrder(@RequestBody SingleOrderMasterRequest singleOrderMasterRequest)
    {
        Boolean flag = orderMasterService.createSingleOrder(singleOrderMasterRequest);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getUserIdWiseOrderList/{userId}")
    public ResponseEntity getUserIdWiseOrderList(@PathVariable String userId)
    {
        List list = orderMasterService.getUserIdWiseOrderList(userId);
        return new ResponseEntity(list,HttpStatus.OK);
    }

    @GetMapping(value = "/editOrderMaster/{orderId}")
    public ResponseEntity editOrderMaster(@PathVariable String orderId)
    {
        OrderMaster orderMaster = orderMasterService.editOrderMaster(orderId);
        if(orderMaster!=null)
        {
            return new ResponseEntity(orderMaster, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(orderMaster,HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping(value = "/updateOrderStatus")
    public ResponseEntity updateOrderStatus( @RequestBody OrderStatusUpdateRequest orderStatusUpdateRequest) throws MessagingException, IOException {
        UpdateOrderStatusRes updateOrderStatus = orderMasterService.updateOrderStatus(orderStatusUpdateRequest);
        return new ResponseEntity(updateOrderStatus, HttpStatus.OK);
    }

    @GetMapping(value = "/getCartArtFrameIAndOrderIdWiseOrderList/{cartArtFrameId}/{orderId}")
    public ResponseEntity getCartArtFrameIAndOrderIdWiseOrderList(@PathVariable String cartArtFrameId,@PathVariable String orderId)
    {
        OrderMaster orderMaster = orderMasterService.getCartArtFrameIAndOrderIdWiseOrderList(cartArtFrameId,orderId);
        return new ResponseEntity(orderMaster,HttpStatus.OK);

    }
    @GetMapping(value = "/getCartProductIAndOrderIdWiseOrderList/{cartProductId}/{orderId}")
    public ResponseEntity getCartProductIAndOrderIdWiseOrderList(@PathVariable String cartProductId,@PathVariable String orderId)
    {
        OrderMaster orderMaster = orderMasterService.getCartProductIAndOrderIdWiseOrderList(cartProductId,orderId);
        return new ResponseEntity(orderMaster,HttpStatus.OK);
    }
    @GetMapping(value = "/getUserIdWiseCartArtFrameMasterList/{userId}")
    public ResponseEntity getUserIdWiseCartArtFrameMasterList(@PathVariable String userId)
    {
        List<OrderResDto> list = orderMasterService.getUserIdWiseCartArtFrameMasterList(userId);
        return new ResponseEntity(list,HttpStatus.OK);
    }
    @GetMapping(value = "/getAllOrderList")
    public ResponseEntity getAllOrderList()
    {
        List<OrderMaster> list = orderMasterService.getAllOrderList();
        return new ResponseEntity(list,HttpStatus.OK);
    }
    @GetMapping(value = "/getStatusWiseOrderList/{status}")
    public ResponseEntity getStatusWiseOrderList(@PathVariable String status)
    {
        List<OrderResDto> list = orderMasterService.getStatusWiseOrderList(status);
        return new ResponseEntity(list,HttpStatus.OK);
    }
    @GetMapping(value = "/getCartArtFrameUniqueNoWiseData/{cartArtFrameUniqueNo}/{orderId}")
    public ResponseEntity getCartArtFrameUniqueNoWiseData(@PathVariable String cartArtFrameUniqueNo,@PathVariable String orderId)
    {
        OrderResDto orderResDto = orderMasterService.getCartArtFrameUniqueNoWiseCartArtFrameMaster(cartArtFrameUniqueNo,orderId);
        return new ResponseEntity(orderResDto,HttpStatus.OK);
    }

    @GetMapping(value = "/getCartProductUniqueNoWiseData/{cartProductNo}/{orderId}")
    public ResponseEntity getCartProductUniqueNoWiseData(@PathVariable String cartProductNo,@PathVariable String orderId)
    {
        OrderCartProductResDto cartProductResDto = orderMasterService.getCartProductUniqueNoWiseData(cartProductNo,orderId);
        return new ResponseEntity(cartProductResDto,HttpStatus.OK);
    }


    @PutMapping(value = "/updateOrderStatusCartArtProductWise")
    public ResponseEntity updateOrderStatusCartArtProductWise( @RequestBody OrderStatusUpdateArtProductRequest orderStatusUpdateArtProductRequest) throws MessagingException, IOException {
        UpdateOrderStatusRes updateOrderStatus = orderMasterService.updateOrderStatusCartArtProductWise(orderStatusUpdateArtProductRequest);
        return new ResponseEntity(updateOrderStatus, HttpStatus.OK);
    }

    @GetMapping("/getAllOrdersByUserId/{userId}")
    public ResponseEntity getAllOrdersByUserId(@PathVariable("userId") String userId)
    {
        UserOrderRes userOrderRes=new UserOrderRes();
        userOrderRes=orderMasterService.getAllOrdersByUserId(userId);
        return new ResponseEntity(userOrderRes, HttpStatus.OK);
    }

    @PostMapping("/getAllOrdersByUserIdAndDate")
    public ResponseEntity getAllOrdersByUserIdAndDate(@RequestBody OrderFilterReq orderFilterReq)
    {
        UserOrderRes userOrderRes=new UserOrderRes();
        userOrderRes=orderMasterService.getAllOrdersByUserIdAndDate(orderFilterReq);
        return new ResponseEntity(userOrderRes, HttpStatus.OK);
    }



}

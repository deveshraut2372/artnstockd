package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.UpdateEstimateAmountGiftCodeRequest;
import com.zplus.ArtnStockMongoDB.dto.req.*;
import com.zplus.ArtnStockMongoDB.dto.res.*;
import com.zplus.ArtnStockMongoDB.model.CartMaster;
import com.zplus.ArtnStockMongoDB.model.CartProductMaster;
import com.zplus.ArtnStockMongoDB.service.CartMasterService;
import org.apache.commons.collections.BagUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/cart_master")
public class CartMasterController {

    @Autowired
    private CartMasterService cartMasterService;

    @GetMapping("/getUserIdWiseCartData/{userId}")
    public ResponseEntity getUserIdWiseCartData(@PathVariable String userId) {
        CartMaster cartMaster = cartMasterService.getUserIdWiseCartData(userId);
        return new ResponseEntity(cartMaster, HttpStatus.OK);
    }   

    @GetMapping(value = "/getUserIdWiseCartProductData/{userId}")
    public ResponseEntity getUserIdWiseCartProductData(@PathVariable String userId) {
        List<CartProductMaster> cartMaster = cartMasterService.getUserIdWiseCartProductData(userId);
        return new ResponseEntity(cartMaster, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveCartProduct(@RequestBody AddToCartProductRequest addToCartProductRequest)
    {
        System.out.println(" saveCartProduct ");
        Boolean flag = cartMasterService.saveCartProduct(addToCartProductRequest);

        if (Boolean.TRUE.equals(flag)) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/IncreaseCartQty/{cartProductId}")
    public ResponseEntity IncreaseCartQty(@PathVariable String cartProductId) {
        Boolean flag = cartMasterService.IncreaseCartQty(cartProductId);
        if (Boolean.TRUE.equals(flag)) {
            return new ResponseEntity(flag, HttpStatus.OK);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/DecreaseCartQty/{cartProductId}")
    public ResponseEntity DecreaseCartQty(@PathVariable String cartProductId) {
        Boolean flag = cartMasterService.DecreaseCartQty(cartProductId);
        if (Boolean.TRUE.equals(flag)) {
            return new ResponseEntity(flag, HttpStatus.OK);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/updateCartStatus/{cartId}")
    public ResponseEntity updateCartStatus(@PathVariable String cartId) {
        Message message = cartMasterService.updateCartStatus(cartId);
        return new ResponseEntity(message, HttpStatus.OK);
    }

    @GetMapping(value = "/UserWiseGetTotalCount/{userId}")
    public ResponseEntity UserWiseGetTotalQty(@PathVariable String userId) {
        UserWiseCartResponse userWiseCartResponse = cartMasterService.UserWiseGetTotalQty(userId);
        return new ResponseEntity(userWiseCartResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = "/CartIdWiseDeleteCartMaster/{cartId}")
    public ResponseEntity CartIdWiseDeleteCartMaster(@PathVariable String cartId) {
        Message message = cartMasterService.CartIdWiseDeleteCartMaster(cartId);
        return new ResponseEntity(message, HttpStatus.OK);
    }

    @PutMapping(value = "/UpdateEstimateAmountUsingPromoCode")
    public ResponseEntity UpdateEstimateAmount(@RequestBody UpdateEstimateAmountRequest updateEstimateAmountRequest) throws MessagingException, IOException {
        UpdateOrderStatusRes updateOrderStatus = cartMasterService.UpdateEstimateAmountUsingPromoCode(updateEstimateAmountRequest);
        return new ResponseEntity(updateOrderStatus, HttpStatus.OK);
    }

    @PutMapping(value = "/UpdateEstimateAmountUsingGiftCode")
    public ResponseEntity UpdateEstimateAmountUsingGiftCode(@RequestBody UpdateEstimateAmountGiftCodeRequest updateEstimateAmountGiftCodeRequest) throws MessagingException, IOException {
        UpdateOrderStatusRes updateOrderStatus = cartMasterService.UpdateEstimateAmountUsingGiftCode(updateEstimateAmountGiftCodeRequest);
        return new ResponseEntity(updateOrderStatus, HttpStatus.OK);
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity getAll() {
        List<CartMaster> cartMaster = cartMasterService.getAll();
        return new ResponseEntity(cartMaster, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteCart/{cartProductId}")
    public ResponseEntity deleteCart(@PathVariable String cartProductId) {
        Boolean flag = cartMasterService.deleteCart(cartProductId);
        if (Boolean.TRUE.equals(flag)) {
            return new ResponseEntity(flag, HttpStatus.OK);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/updateCartShippingMethod")
    public ResponseEntity updateCartStatus(@RequestBody UpdateCartShippingMethodReq updateCartShippingMethodReq) {
        Message message = cartMasterService.updateCartShippingMethod(updateCartShippingMethodReq);
        return new ResponseEntity(message, HttpStatus.OK);
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity get(@PathVariable String userId) {
        CartMasterResponseDto cartMasterResponseDto = cartMasterService.get(userId);
        return new ResponseEntity(cartMasterResponseDto, HttpStatus.OK);
    }

    @PostMapping(value = "/BuyNow")
    public ResponseEntity BuyNow(@RequestBody BuyNowReq buyNowReq)
    {
        BuyNowRes buyNowRes=new BuyNowRes();
        buyNowRes=cartMasterService.BuyNow(buyNowReq);
        return new ResponseEntity(buyNowRes, HttpStatus.OK);
    }

    @PostMapping(value="BuyNowNew")
    public ResponseEntity BuyNowNew(@RequestBody BuyNowReqq buyNowReqq)
    {
        BuyNowRes buyNowRes=new BuyNowRes();
        buyNowRes=cartMasterService.BuyNowNew(buyNowReqq);
        return new ResponseEntity(buyNowRes,HttpStatus.OK);
    }



    @GetMapping(value = "/getUserIdWiseCartDetails/{userId}")
    public ResponseEntity getUserIdWiseCartData1(@PathVariable("userId") String userId) {
        CartMasterRes  cartMasterRes = cartMasterService.getUserIdWiseCartData1(userId);
        return new ResponseEntity(cartMasterRes, HttpStatus.OK);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity delete(@RequestBody CartDeleteReq cartDeleteReq)
    {

        Boolean flag = cartMasterService.delete(cartDeleteReq);
        if (Boolean.TRUE.equals(flag)) {
            return new ResponseEntity(flag,HttpStatus.OK);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/tempDelete")
    public ResponseEntity tempDelete(@RequestBody CartDeleteReq cartDeleteReq)
    {
        Boolean flag = cartMasterService.tempDelete(cartDeleteReq);
        if (Boolean.TRUE.equals(flag)) {
            return new ResponseEntity(flag,HttpStatus.OK);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/IncreaseCartQtyByType/{cartProductId}/{type}")
    public ResponseEntity IncreaseCartQtyByType(@PathVariable String cartProductId,@PathVariable("type") String type)
    {
        Boolean flag = cartMasterService.IncreaseCartQtyByType(cartProductId,type);
        if (Boolean.TRUE.equals(flag)) {
            return new ResponseEntity(flag, HttpStatus.OK);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/DecreaseCartQtyByType/{cartProductId}/{type}")
    public ResponseEntity DecreaseCartQtyByType(@PathVariable String cartProductId,@PathVariable("type") String type)
    {
        Boolean flag = cartMasterService.DecreaseCartQtyByType(cartProductId,type);
        if (Boolean.TRUE.equals(flag)) {
            return new ResponseEntity(flag, HttpStatus.OK);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/getUserIdWisetempDeleteCartDetails/{userId}")
    public ResponseEntity getUserIdWisetempDeleteCartDetails(@PathVariable("userId") String userId) {
        CartMasterRes  cartMasterRes = cartMasterService.getUserIdWisetempDeleteCartDetails(userId);
        return new ResponseEntity(cartMasterRes, HttpStatus.OK);
    }

    @PostMapping(value = "/updateStatus")
    public ResponseEntity updateStatus(@RequestBody CartUpdateReq cartUpdateReq)
    {
        System.out.println(" cartDeleteReq = "+cartUpdateReq.toString());
        Boolean flag = cartMasterService.updateStatus(cartUpdateReq);
        if (Boolean.TRUE.equals(flag)) {
            return new ResponseEntity(flag,HttpStatus.OK);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getCartTotalAmount/{userId}")
    public ResponseEntity getCartTotalAmount(@PathVariable("userId") String userId)
    {
//        Double totalAmount

        Map<String,Object> result =cartMasterService.getCartTotalAmount(userId);
        System.out.println("  Total Amount ="+result);
        return new ResponseEntity(result,HttpStatus.OK);
    }


//    @GetMapping("/getUserIdAndProductIdWiseCartData/{userId}")
//    public ResponseEntity getUserIdWiseCartData(@PathVariable String userId) {
//        CartMaster cartMaster = cartMasterService.getUserIdWiseCartData(userId);
//        return new ResponseEntity(cartMaster, HttpStatus.OK);
//    }

}


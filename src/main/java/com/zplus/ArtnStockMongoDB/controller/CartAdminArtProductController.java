package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.*;
//import com.zplus.ArtnStockMongoDB.dto.req.CartArtFrameMasterRequest;
//import com.zplus.ArtnStockMongoDB.dto.res.CartArtSaveResponse;
import com.zplus.ArtnStockMongoDB.dto.res.CartAdminArtProductRes;
import com.zplus.ArtnStockMongoDB.model.CartAdminArtProductMaster;
import com.zplus.ArtnStockMongoDB.service.CartAdminArtProductService;
//import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "cart_admin_art_product")
@CrossOrigin(origins = "*")
public class CartAdminArtProductController {

    @Autowired
    private CartAdminArtProductService cartAdminArtProductService;

//    @PostMapping(value = "/saveCartArtFrame")
//    public ResponseEntity saveCartArtFrame(@RequestBody CartArtFrameMasterRequest cartArtFrameMasterRequest)
//    {
//        CartArtSaveResponse cartArtSaveResponse=cartArtFrameMasterService.saveCartArtFrame(cartArtFrameMasterRequest);
//        return new ResponseEntity(cartArtSaveResponse, HttpStatus.OK);
//    }

    @PostMapping
    public ResponseEntity saveCartProduct(@RequestBody AdminArtProductReq adminArtProductReq) {

        CartAdminArtProductMaster cartAdminArtProductMaster=new CartAdminArtProductMaster();

        CartAdminArtProductRes cartAdminArtProductRes=new CartAdminArtProductRes();


        System.out.println(" saveCartProduct  Admin Art Product Master  =="+adminArtProductReq.toString());
//        Boolean flag = cartAdminArtProductService.saveCartAdminArtProduct(adminArtProductReq);
        cartAdminArtProductRes=cartAdminArtProductService.saveCartAdminArtProduct(adminArtProductReq);
        if (Boolean.TRUE.equals(cartAdminArtProductRes.getFlag())) {
            return new ResponseEntity(cartAdminArtProductRes, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(cartAdminArtProductRes, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PutMapping
//    public ResponseEntity updateCartProduct(@RequestBody AdminArtProductReq adminArtProductReq) {
//        System.out.println(" saveCartProduct  Admin Art Product Master  =="+adminArtProductReq.toString());
//        Boolean flag = cartAdminArtProductService.updateCartAdminArtProduct(adminArtProductReq);
//        if (Boolean.TRUE.equals(flag)) {
//            return new ResponseEntity(flag, HttpStatus.CREATED);
//        } else {
//            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @GetMapping(value = "/IncreaseCartQty/{cartAdminArtProductId}")
    public ResponseEntity IncreaseCartQty(@PathVariable String cartAdminArtProductId) {
        Boolean flag = cartAdminArtProductService.IncreaseCartQty(cartAdminArtProductId);
        if (Boolean.TRUE.equals(flag)) {
            return new ResponseEntity(flag, HttpStatus.OK);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/DecreaseCartQty/{cartAdminArtProductId}")
    public ResponseEntity DecreaseCartQty(@PathVariable String cartAdminArtProductId) {
        Boolean flag = cartAdminArtProductService.DecreaseCartQty(cartAdminArtProductId);
        if (Boolean.TRUE.equals(flag)) {
            return new ResponseEntity(flag, HttpStatus.OK);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/deleteCartAdminProduct/{cartAdminArtProductId}")
    public ResponseEntity deleteCartAdminProduct(@PathVariable String cartAdminArtProductId) {
        Boolean flag = cartAdminArtProductService.deleteCartAdminProduct(cartAdminArtProductId);
        if (Boolean.TRUE.equals(flag)) {
            return new ResponseEntity(flag, HttpStatus.OK);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }







//    @GetMapping("/getUserIdWiseCartAdminArtProductData/{userId}")
//    public ResponseEntity getUserIdWiseCartAdminArtProductData(@PathVariable String userId) {
//        CartMaster cartMaster = cartMasterService.getUserIdWiseCartAdminArtProductData(userId);
//        return new ResponseEntity(cartMaster, HttpStatus.OK);
//    }

}

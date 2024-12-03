package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.AddToCartProductRequest;
import com.zplus.ArtnStockMongoDB.dto.req.CartArtFrameMasterRequest;
import com.zplus.ArtnStockMongoDB.dto.res.CartArtSaveResponse;
import com.zplus.ArtnStockMongoDB.dto.res.Message;
import com.zplus.ArtnStockMongoDB.model.CartArtFrameMaster;
import com.zplus.ArtnStockMongoDB.model.CartMaster;
import com.zplus.ArtnStockMongoDB.model.CartProductMaster;
import com.zplus.ArtnStockMongoDB.service.CartArtFrameMasterService;
import com.zplus.ArtnStockMongoDB.service.CartMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/cart_art_frame_master")
public class CartArtFrameMasterController {

    @Autowired
    private CartArtFrameMasterService cartArtFrameMasterService;

    @PostMapping(value = "/saveCartArtFrame")
    public ResponseEntity saveCartArtFrame(@RequestBody CartArtFrameMasterRequest cartArtFrameMasterRequest)
    {
        CartArtSaveResponse cartArtSaveResponse=cartArtFrameMasterService.saveCartArtFrame(cartArtFrameMasterRequest);
        return new ResponseEntity(cartArtSaveResponse,HttpStatus.OK);
    }


    @GetMapping(value = "/IncreaseCartQty/{cartArtFrameId}")
    public ResponseEntity IncreaseCartQty(@PathVariable String cartArtFrameId)
    {
        Boolean flag = cartArtFrameMasterService.IncreaseCartQty(cartArtFrameId);
        if(Boolean.TRUE.equals(flag))
        {
            return new ResponseEntity(flag,HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(flag,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/DecreaseCartQty/{cartArtFrameId}")
    public ResponseEntity DecreaseCartQty(@PathVariable String cartArtFrameId)
    {
        Boolean flag = cartArtFrameMasterService.DecreaseCartQty(cartArtFrameId);
        if(Boolean.TRUE.equals(flag))
        {
            return new ResponseEntity(flag,HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(flag,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getUserIdWiseCartMasterData/{userId}")
    public ResponseEntity getUserIdWiseCartMasterData(@PathVariable String userId)
    {
        CartMaster cartMaster=cartArtFrameMasterService.getUserIdWiseCartMasterData(userId);
        return new ResponseEntity(cartMaster,HttpStatus.OK);
    }

    @GetMapping(value = "/getUserIdWiseCartArtFrameData/{userId}")
    public ResponseEntity getUserIdWiseCartArtFrameData(@PathVariable String userId)
    {
        List<CartArtFrameMaster> list=cartArtFrameMasterService.getUserIdWiseCartArtFrameData(userId);
        return new ResponseEntity(list,HttpStatus.OK);
    }
    @DeleteMapping(value = "/deleteCart/{cartArtFrameId}")
    public ResponseEntity deleteCart(@PathVariable String cartArtFrameId)
    {
        Boolean flag = cartArtFrameMasterService.deleteCart(cartArtFrameId);
        if(Boolean.TRUE.equals(flag))
        {
            return new ResponseEntity(flag, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(flag,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getCartArtFrameMaster/{cartArtFrameId}")
    public ResponseEntity getCratArtFrameMaster(@PathVariable("cartArtFrameId") String cartArtFrameId)
    {
        CartArtFrameMaster cartArtFrameMaster=new CartArtFrameMaster();
        cartArtFrameMaster=cartArtFrameMasterService.getCratArtFrameMaster(cartArtFrameId);
        return ResponseEntity.ok().body(cartArtFrameMaster);


    }

}

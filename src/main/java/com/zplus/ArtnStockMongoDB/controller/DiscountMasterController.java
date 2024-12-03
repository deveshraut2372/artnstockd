package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.AddToCartProductRequest;
import com.zplus.ArtnStockMongoDB.dto.req.DiscountMasterReq;
import com.zplus.ArtnStockMongoDB.model.DiscountMaster;
import com.zplus.ArtnStockMongoDB.service.DiscountMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/Discount_Master")
public class DiscountMasterController {


    @Autowired
    private DiscountMasterService discountMasterService;


    @PostMapping
    public ResponseEntity createDiscountMaster(@RequestBody DiscountMasterReq discountMasterReq) {
        System.out.println(" createDiscountMaster ");
        Boolean flag = discountMasterService.createDiscountMaster(discountMasterReq);
        if (Boolean.TRUE.equals(flag)) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping
    public ResponseEntity updateDiscountMaster(@RequestBody DiscountMasterReq discountMasterReq) {
        System.out.println(" Update DiscountMaster ");
        Boolean flag = discountMasterService.updateDiscountMaster(discountMasterReq);
        if (Boolean.TRUE.equals(flag)) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity getAllDiscounts()
    {
        List<DiscountMaster> discountMasterList=new ArrayList<>();
        discountMasterList=discountMasterService.getAllDiscounts();
        return new ResponseEntity(discountMasterList, HttpStatus.OK);
    }

    @GetMapping(value = "/editByDiscountId/{discountId}")
    public ResponseEntity editByDiscountId(@PathVariable("discountId") String discountId)
    {
        DiscountMaster discountMaster=new DiscountMaster();
        discountMaster=discountMasterService.editByDiscountId(discountId);
        return new ResponseEntity(discountMaster, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteByDiscountId/{discountId}")
    public ResponseEntity deleteByDiscountId(@PathVariable("discountId") String discountId)
    {
        Boolean flag = discountMasterService.deleteByDiscountId(discountId);
        if (Boolean.TRUE.equals(flag)) {
            return new ResponseEntity(flag, HttpStatus.OK);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

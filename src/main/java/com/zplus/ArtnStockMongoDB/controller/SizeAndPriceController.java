package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.SizeAndPriceReq;
import com.zplus.ArtnStockMongoDB.model.SizeAndPrice;
import com.zplus.ArtnStockMongoDB.service.SizeAndPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/sizeAndPrice")
@CrossOrigin(origins = "*")
public class SizeAndPriceController {

    @Autowired
    private SizeAndPriceService sizeAndPriceService;

    @PostMapping(value = "/create")
    public ResponseEntity createSizeAndPrice(@RequestBody SizeAndPriceReq sizeAndPriceReq)
    {
        Boolean flag = sizeAndPriceService.createSizeAndPrice(sizeAndPriceReq);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/update")
    public ResponseEntity updateSizeAndPrice(SizeAndPriceReq sizeAndPriceReq)
    {
        Boolean flag = sizeAndPriceService.updateSizeAndPrice(sizeAndPriceReq);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.OK);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getAllSizes")
    public ResponseEntity getAllSizes()
    {
        List<SizeAndPrice> sizeAndPriceList= sizeAndPriceService.getAllSizes();
        return new ResponseEntity(sizeAndPriceList, HttpStatus.OK);
    }

    @GetMapping(value="/getBySizeAndPriceId/{SizeAndPriceId}")
    private ResponseEntity getBySizeAndPriceId(@PathVariable String SizeAndPriceId){
        SizeAndPrice sizeAndPrice=new SizeAndPrice();
        sizeAndPrice=sizeAndPriceService.getBySizeAndPriceId(SizeAndPriceId);
        return new ResponseEntity(sizeAndPrice,HttpStatus.OK);
    }


    @GetMapping(value="/getSizeAndPricesByProductColorId/{productColorId}")
    private ResponseEntity getSizeAndPricesByProductColorId(@PathVariable String productColorId){
        List<SizeAndPrice> sizeAndPriceList=new ArrayList<>();
        sizeAndPriceList=sizeAndPriceService.getSizeAndPricesByProductColorId(productColorId);
        return new ResponseEntity(sizeAndPriceList,HttpStatus.OK);
    }


    @DeleteMapping(value="/deleteBySizeAndPriceId/{SizeAndPriceId}")
    private ResponseEntity deleteBySizeAndPriceId(@PathVariable String SizeAndPriceId){
        Boolean flag = sizeAndPriceService.deleteBySizeAndPriceId(SizeAndPriceId);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.OK);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}

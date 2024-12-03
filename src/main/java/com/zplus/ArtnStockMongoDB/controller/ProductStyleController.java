package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.ProductStyleReq;
import com.zplus.ArtnStockMongoDB.model.ProductStyle;
import com.zplus.ArtnStockMongoDB.service.ProductStyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/productStyle")
public class ProductStyleController {

    @Autowired
    private ProductStyleService productStyleService;


    @PostMapping("/create")
    public ResponseEntity createProductStyle(@RequestBody ProductStyleReq productStyleReq) {
        Boolean flag = productStyleService.createProductStyle(productStyleReq);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update")
    public ResponseEntity updateProductStyle(@RequestBody ProductStyleReq productStyleReq) {
        Boolean flag = productStyleService.updateProductStyle(productStyleReq);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity getAllProductStyles() {
        List list = productStyleService.getAllProductStyles();
        return new ResponseEntity(list, HttpStatus.CREATED);
    }
    @GetMapping(value = "/getByProductStyleId/{productStyleId}")
    public ResponseEntity getByProductStyleId(@PathVariable String productStyleId)
    {
        ProductStyle productColor =productStyleService.getByProductStyleId(productStyleId);
        return new ResponseEntity(productColor, HttpStatus.OK);
    }

    @GetMapping(value = "/getAllStylesByProductSubSubCategoryId/{productSubSubCategoryId}")
    public ResponseEntity getAllStylesByProductSubSubCategoryId(@PathVariable String productSubSubCategoryId)
    {
        List<ProductStyle> productStyleList =productStyleService.getAllStylesByProductSubSubCategoryId(productSubSubCategoryId);
        return new ResponseEntity(productStyleList, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteByProductStyleId/{productStyleId}")
    public ResponseEntity deleteByProductStyleId(@PathVariable String productStyleId)
    {
        Boolean flag=productStyleService.deleteByProductStyleId(productStyleId);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}

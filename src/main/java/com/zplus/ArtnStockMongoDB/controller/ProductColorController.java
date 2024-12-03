package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.ProductColorReq;
import com.zplus.ArtnStockMongoDB.dto.res.ProductColorRes;
import com.zplus.ArtnStockMongoDB.model.ProductColor;
import com.zplus.ArtnStockMongoDB.service.ProductColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/product_color")
public class ProductColorController {

    @Autowired
    private ProductColorService productColorService;

    @PostMapping("/create")
    public ResponseEntity createProductColor(@RequestBody ProductColorReq productColorReq) {
        Boolean flag = productColorService.createProductColor(productColorReq);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update")
    public ResponseEntity updateProductColor(@RequestBody ProductColorReq productColorReq) {
        Boolean flag = productColorService.updateProductColor(productColorReq);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity getAllProductColors() {
        List list = productColorService.getAllProductColors();
        return new ResponseEntity(list, HttpStatus.CREATED);
    }
    @GetMapping(value = "/getByProductColorId/{productColorId}")
    public ResponseEntity getByProductColorId(@PathVariable String productColorId)
    {
        ProductColor productColor=productColorService.getByProductColorId(productColorId);
            return new ResponseEntity(productColor, HttpStatus.OK);
    }

    @GetMapping(value = "/getByProductColorByProductStyleId/{productStyleId}")
    public ResponseEntity getByProductColorByProductStyleId(@PathVariable String productStyleId)
    {
        List<ProductColor> productColorList=new ArrayList<>();
        productColorList=productColorService.getByProductColorByProductStyleId(productStyleId);
        return new ResponseEntity(productColorList, HttpStatus.OK);
    }

    @GetMapping(value = "/getByProductColorsByProductStyleId/{productStyleId}")
    public ResponseEntity getByProductColorsByProductStyleId(@PathVariable String productStyleId)
    {
        ProductColorRes productColorRes=new ProductColorRes();
        productColorRes=productColorService.getByProductColorsByProductStyleId(productStyleId);
        return new ResponseEntity(productColorRes, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteByProductColorId/{productColorId}")
    public ResponseEntity deleteByProductColorId(@PathVariable String productColorId)
    {
        Boolean flag=productColorService.deleteByProductColorId(productColorId);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.ProductReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.UpdateProductReqDto;
import com.zplus.ArtnStockMongoDB.model.ProductMaster;
import com.zplus.ArtnStockMongoDB.service.ProductMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/product_master")
public class ProductMasterController {

    @Autowired
    private ProductMasterService productMasterService;

    @PostMapping("/create")
    public ResponseEntity createProductMaster(@RequestBody ProductReqDto productReqDto) {
        System.out.println(" productReqDto ="+productReqDto.toString());
        Boolean flag = productMasterService.createProductMaster(productReqDto);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity updateProductMaster(@RequestBody UpdateProductReqDto updateProductReqDto) {
        Boolean flag = productMasterService.updateProductMaster(updateProductReqDto);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity getAllProductMaster() {
        List list = productMasterService.getAllProductMaster();
        return new ResponseEntity(list, HttpStatus.CREATED);
    }

    @GetMapping(value = "/editProductMaster/{productId}")
    public ResponseEntity editProductMaster(@PathVariable String productId) {
        ProductMaster productMaster = productMasterService.editProductMaster(productId);
        if (productMaster != null) {
            return new ResponseEntity(productMaster, HttpStatus.OK);
        } else {
            return new ResponseEntity(productMaster, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getActiveProductMaster")
    public ResponseEntity getActiveProductMaster() {
        List list = productMasterService.getActiveProductMaster();
        return new ResponseEntity(list, HttpStatus.OK);
    }





    @GetMapping(value = "/getProductSubCategoryIdWiseProductMasterList/{productSubCategoryId}")
    public ResponseEntity getProductSubCategoryIdWiseProductMasterList(@PathVariable String productSubCategoryId) {
        List<ProductMaster> list = productMasterService.getProductSubCategoryIdWiseProductMasterList(productSubCategoryId);
        return new ResponseEntity(list, HttpStatus.OK);
    }

//    @GetMapping(value = "getPopularStatusWiseList")
//    public ResponseEntity getPopularStatusWiseList()
//    {
//        List list = productMasterService.getPopularStatusWiseList();
//        return new ResponseEntity(list,HttpStatus.OK);
//
//    }

}
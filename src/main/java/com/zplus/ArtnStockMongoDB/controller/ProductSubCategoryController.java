package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.ProductMainCategoryReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.ProductSubCategoryReqDto;
import com.zplus.ArtnStockMongoDB.model.ProductMainCategoryMaster;
import com.zplus.ArtnStockMongoDB.model.ProductSubCategoryMaster;
import com.zplus.ArtnStockMongoDB.service.ProductSubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/product_sub_category_master")
public class ProductSubCategoryController {

    @Autowired
    private ProductSubCategoryService productSubCategoryService;

    @PostMapping("/create")
    public ResponseEntity createProductSubCategory(@RequestBody ProductSubCategoryReqDto productSubCategoryReqDto) {
        Boolean flag = productSubCategoryService.createProductSubCategory(productSubCategoryReqDto);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping
    public ResponseEntity updateProductSubCategory(@RequestBody ProductSubCategoryReqDto productSubCategoryReqDto) {
        Boolean flag = productSubCategoryService.updateProductSubCategory(productSubCategoryReqDto);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity getAllProductSubCategory() {
        List list = productSubCategoryService.getAllProductSubCategory();
        return new ResponseEntity(list, HttpStatus.CREATED);
    }
    @GetMapping(value = "/editProductSubCategory/{productSubCategoryId}")
    public ResponseEntity editProductSubCategory(@PathVariable String productSubCategoryId)
    {
        ProductSubCategoryMaster productSubCategoryMaster = productSubCategoryService.editProductSubCategory(productSubCategoryId);
        if(productSubCategoryMaster!=null)
        {
            return new ResponseEntity(productSubCategoryMaster, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(productSubCategoryMaster,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/getActiveProductSubCategory")
    public ResponseEntity getActiveProductSubCategory()
    {
        List list = productSubCategoryService.getActiveProductSubCategory();
        return new ResponseEntity(list,HttpStatus.OK);

    }

    @GetMapping(value = "/getProductMainCategoryIdWiseProductSubCategory/{productMainCategoryId}")
    public ResponseEntity getProductMainCategoryIdWiseProductSubCategory(@PathVariable String productMainCategoryId)
    {
        List<ProductSubCategoryMaster> list = productSubCategoryService.getProductMainCategoryIdWiseProductSubCategory(productMainCategoryId);
       return new ResponseEntity(list,HttpStatus.OK);
    }

    @GetMapping(value = "/getTypeWiseList/{type}")
    public ResponseEntity getTypeWiseList(@PathVariable String type)
    {
        List<ProductSubCategoryMaster> list = productSubCategoryService.getTypeWiseList(type);
        return new ResponseEntity(list,HttpStatus.OK);
    }

}

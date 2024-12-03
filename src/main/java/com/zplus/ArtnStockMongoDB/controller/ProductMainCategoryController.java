package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.ProductMainCategoryReqDto;
import com.zplus.ArtnStockMongoDB.model.ProductMainCategoryMaster;
import com.zplus.ArtnStockMongoDB.service.ProductMainCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/product_main_category_master")
public class ProductMainCategoryController {

    @Autowired
    private ProductMainCategoryService productMainCategoryService;

    @PostMapping("/create")
    public ResponseEntity createProductMainCategory(@RequestBody ProductMainCategoryReqDto productMainCategoryReqDto) {
        Boolean flag = productMainCategoryService.createProductMainCategory(productMainCategoryReqDto);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity updateProductMainCategory(@RequestBody ProductMainCategoryReqDto productMainCategoryReqDto) {
        Boolean flag = productMainCategoryService.updateProductMainCategory(productMainCategoryReqDto);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity getAllProductMainCategory() {
        List list = productMainCategoryService.getAllProductMainCategory();
        return new ResponseEntity(list, HttpStatus.CREATED);
    }
    @GetMapping(value = "/editProductMainCategory/{productMainCategoryId}")
    public ResponseEntity editProductMainCategory(@PathVariable String productMainCategoryId)
    {
        ProductMainCategoryMaster productMainCategoryMaster = productMainCategoryService.editProductMainCategory(productMainCategoryId);
        if(productMainCategoryMaster!=null)
        {
            return new ResponseEntity(productMainCategoryMaster, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(productMainCategoryMaster,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/getActiveProductMainCategory")
    public ResponseEntity getActiveProductMainCategory()
    {
        List list = productMainCategoryService.getActiveProductMainCategory();
        return new ResponseEntity(list,HttpStatus.OK);

    }

}

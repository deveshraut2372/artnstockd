package com.zplus.ArtnStockMongoDB.controller;



import com.zplus.ArtnStockMongoDB.dto.req.ProductSubSubCategoryReq;
import com.zplus.ArtnStockMongoDB.model.ProductSubSubCategory;
import com.zplus.ArtnStockMongoDB.service.ProductSubSubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/Product_Sub_Sub_Category")
public class ProductSubSubCategoryController {

    @Autowired
    private ProductSubSubCategoryService productSubSubCategoryService;


    @PostMapping("/create")
    public ResponseEntity createProductSubSubCategory(@RequestBody ProductSubSubCategoryReq productSubSubCategoryReq)
    {
        Map<String,String> message=new HashMap();
        message=productSubSubCategoryService.createProductSubSubCategory(productSubSubCategoryReq);

        System.out.println("  message ="+message);

        if(message.get("flag").equalsIgnoreCase("true")) {
            return new ResponseEntity(message, HttpStatus.CREATED);
        }else {
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity updateProductSubSubCategory(@RequestBody ProductSubSubCategoryReq productSubSubCategoryReq)
    {
        Map<String,String> message=new HashMap();
        message=productSubSubCategoryService.updateProductSubSubCategory(productSubSubCategoryReq);

        System.out.println("  message ="+message);

        if(message.get("flag").equalsIgnoreCase("true")) {
            return new ResponseEntity(message, HttpStatus.ACCEPTED);
        }else {
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        }
    }

//    getAllByProductSubCategoryId
//    String productSubCategoryId

    @GetMapping("/getAllByProductSubCategoryId/{productSubCategoryId}")
    public ResponseEntity getAllByProductSubCategoryId(@PathVariable("productSubCategoryId") String productSubCategoryId)
    {
        List<ProductSubSubCategory> productSubSubCategoryList=new ArrayList<>();
        productSubSubCategoryList=productSubSubCategoryService.getAllByProductSubCategoryId(productSubCategoryId);
        return new ResponseEntity( productSubSubCategoryList,HttpStatus.OK);
    }


    @GetMapping("/getAll")
    public ResponseEntity getAll()
    {
        List<ProductSubSubCategory> productSubSubCategoryList=new LinkedList<>();
        productSubSubCategoryList=productSubSubCategoryService.getAll();
        return new ResponseEntity( productSubSubCategoryList,HttpStatus.OK);
    }

    @GetMapping(value = "/getByProductSubCategoryId/{productSubCategoryId}")
    public  ResponseEntity getByProductSubCategoryId(@PathVariable("productSubCategoryId") String productSubCategoryId)
    {
        List<ProductSubSubCategory> productSubSubCategoryList=new ArrayList<>();
        productSubSubCategoryList=productSubSubCategoryService.getByProductSubCategoryId(productSubCategoryId);
        return new ResponseEntity( productSubSubCategoryList,HttpStatus.OK);
    }





}

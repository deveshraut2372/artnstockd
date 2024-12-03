package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.CategoryMasterReq;
import com.zplus.ArtnStockMongoDB.model.CategoryMaster;
import com.zplus.ArtnStockMongoDB.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/category_master")
public class CategoryMasterController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity createCategory(@RequestBody CategoryMasterReq categoryMasterReq)
    {
        Boolean flag = categoryService.createCategoryMaster(categoryMasterReq);

        if (Boolean.TRUE.equals(flag)) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity updateCategory(@RequestBody CategoryMasterReq categoryMasterReq)
    {
        Boolean flag = categoryService.updateCategoryMaster(categoryMasterReq);

        if (Boolean.TRUE.equals(flag)) {
            return new ResponseEntity(flag, HttpStatus.OK);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping
    public ResponseEntity getAll()
    {
        List<CategoryMaster> list=new ArrayList();
        list =categoryService.getAll();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping(value = "/getByCategoryId/{categoryId}")
    public ResponseEntity getByCategoryId(@PathVariable("categoryId") String categoryId)
    {
        CategoryMaster categoryMaster=new CategoryMaster();
        categoryMaster=categoryService.getByCategoryId(categoryId);
        return new ResponseEntity(categoryMaster,HttpStatus.OK);
    }

    @GetMapping(value = "/getAllByStatus/{status}")
    public ResponseEntity getAllByStatus(@PathVariable("status") String status)
    {
        List<CategoryMaster> categoryMasterList=new ArrayList<>();
        categoryMasterList=categoryService.getAllByStatus(status);
        return new ResponseEntity(categoryMasterList,HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteByCategoryId/{categoryId}")
    public ResponseEntity deleteByCategoryId(@PathVariable("categoryId") String categoryId)
    {
        Boolean flag=categoryService.deleteByCategoryId(categoryId);

        if (Boolean.TRUE.equals(flag)) {
            return new ResponseEntity(flag, HttpStatus.OK);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

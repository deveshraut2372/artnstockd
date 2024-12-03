package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.MainCategoryReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.UpdateMainCategoryReqDto;
import com.zplus.ArtnStockMongoDB.model.MainCategoryMaster;
import com.zplus.ArtnStockMongoDB.service.MainCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/main_category_master")
public class MainCategoryController {

    @Autowired
    private MainCategoryService mainCategoryService;

    @PostMapping("/create")
    public ResponseEntity createMainCategoryMaster(@RequestBody MainCategoryReqDto mainCategoryReqDto) {
        Boolean flag = mainCategoryService.createMainCategoryMaster(mainCategoryReqDto);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping
    public ResponseEntity updateMainCategoryMaster(@RequestBody UpdateMainCategoryReqDto updateMainCategoryReqDto) {
        Boolean flag = mainCategoryService.updateMainCategoryMaster(updateMainCategoryReqDto);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity getAllMainCategoryMaster() {
        List list = mainCategoryService.getAllMainCategoryMaster();
        return new ResponseEntity(list, HttpStatus.CREATED);
    }
    @GetMapping(value = "/editMainCategoryMaster/{mainCategoryId}")
    public ResponseEntity editMainCategoryMaster(@PathVariable String mainCategoryId)
    {
        MainCategoryMaster mainCategoryMaster = mainCategoryService.editMainCategoryMaster(mainCategoryId);
        if(mainCategoryMaster!=null)
        {
            return new ResponseEntity(mainCategoryMaster, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(mainCategoryMaster,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/getActiveMainCategory")
    public ResponseEntity getActiveMainCategory()
    {
        List list = mainCategoryService.getActiveMainCategory();
        return new ResponseEntity(list,HttpStatus.OK);

    }

    }

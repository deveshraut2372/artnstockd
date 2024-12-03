package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.DynamicHomepageContentReqDto;
import com.zplus.ArtnStockMongoDB.model.DynamicHomepageContentMaster;
import com.zplus.ArtnStockMongoDB.service.DynamicHomepageContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/dynamic_homepage_content_master")
public class DynamicHomepageContentMasterController {

    @Autowired
    private DynamicHomepageContentService dynamicHomepageContentService;

    @PostMapping
    public ResponseEntity createDynamicHomepageContentMaster(@RequestBody DynamicHomepageContentReqDto dynamicHomepageContentReqDto) {
        Boolean flag = dynamicHomepageContentService.createDynamicHomepageContentMaster(dynamicHomepageContentReqDto);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update")
    public ResponseEntity updateDynamicHomepageContentMaster(@RequestBody DynamicHomepageContentReqDto dynamicHomepageContentReqDto) {
        Boolean flag = dynamicHomepageContentService.updateDynamicHomepageContentMaster(dynamicHomepageContentReqDto);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity getAllDynamicHomepageContentMaster() {
        List list = dynamicHomepageContentService.getAllDynamicHomepageContentMaster();
        return new ResponseEntity(list, HttpStatus.CREATED);
    }
    @GetMapping(value = "/editDynamicHomepageContentMaster/{dynamicHomepageContentId}")
    public ResponseEntity editDynamicHomepageContentMaster(@PathVariable String dynamicHomepageContentId)
    {
        DynamicHomepageContentMaster dynamicHomepageContentMaster = dynamicHomepageContentService.editDynamicHomepageContentMaster(dynamicHomepageContentId);
        if(dynamicHomepageContentMaster!=null)
        {
            return new ResponseEntity(dynamicHomepageContentMaster, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(dynamicHomepageContentMaster,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getTypeWiseList/{type}")
    public ResponseEntity getTypeWiseList(@PathVariable String type) {
        DynamicHomepageContentMaster dynamicHomepageContentMaster = dynamicHomepageContentService.getTypeWiseList(type);
        return new ResponseEntity(dynamicHomepageContentMaster, HttpStatus.CREATED);
    }
}

package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.RecentlySearchRequest;
import com.zplus.ArtnStockMongoDB.dto.req.RecentlyViewRequest;
import com.zplus.ArtnStockMongoDB.model.RecentlySearchMaster;
import com.zplus.ArtnStockMongoDB.model.RecentlyViewMaster;
import com.zplus.ArtnStockMongoDB.service.RecentlySearchMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/recently_search_master")
public class RecentlySearchMasterController {

    @Autowired
    private RecentlySearchMasterService recentlySearchMasterService;

    @PostMapping
    public ResponseEntity createRecentlySearchMaster(@RequestBody RecentlySearchRequest recentlySearchRequest) {
        Boolean flag = recentlySearchMasterService.createRecentlySearchMaster(recentlySearchRequest);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/getAllRecentlySearchMaster")
    public ResponseEntity getAllRecentlySearchMaster() {
        List list = recentlySearchMasterService.getAllRecentlySearchMaster();
        return new ResponseEntity(list, HttpStatus.OK);
    }
//    recently keyword search
    @GetMapping(value = "/getUserIdWiseRecentlyKeywordSearch/{userId}")
    public ResponseEntity getUserIdWiseRecentlyKeywordSearch(@PathVariable String userId) {
        List list = recentlySearchMasterService.getUserIdWiseRecentlyKeywordSearch(userId);
        return new ResponseEntity(list, HttpStatus.OK);
    }
    @GetMapping(value = "/getByRecentlySearchId/{recentlySearchId}")
    public ResponseEntity getByRecentlySearchId(@PathVariable String recentlySearchId) {
        RecentlySearchMaster recentlySearchMaster = recentlySearchMasterService.getByRecentlySearchId(recentlySearchId);
        return new ResponseEntity(recentlySearchMaster, HttpStatus.OK);
    }
    @DeleteMapping(value = "/deleteCart/{userId}")
    public ResponseEntity deleteCart(@PathVariable String userId)
    {
        Boolean flag = recentlySearchMasterService.deleteRecentlySearch(userId);
        if(Boolean.TRUE.equals(flag))
        {
            return new ResponseEntity(flag, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(flag,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

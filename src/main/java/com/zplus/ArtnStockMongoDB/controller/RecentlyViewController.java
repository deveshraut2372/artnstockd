package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.RecentlyViewRequest;
import com.zplus.ArtnStockMongoDB.model.RecentlyViewMaster;
import com.zplus.ArtnStockMongoDB.service.RecentlyViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/recently_view_master")
public class RecentlyViewController {

    @Autowired
    private RecentlyViewService recentlyViewService;

    @PostMapping
    public ResponseEntity createRecentlyViewMaster(@RequestBody RecentlyViewRequest recentlyViewRequest) {
        Boolean flag = recentlyViewService.createRecentlyViewMaster(recentlyViewRequest);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getAllRecentlyViewMaster")
    public ResponseEntity getAllRecentlyViewMaster() {
        List list = recentlyViewService.getAllRecentlyViewMaster();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    //  recently view List
    @GetMapping(value = "/getUserIdWiseRecentlyViewMaster/{userId}")
    public ResponseEntity getUserIdWiseRecentlyViewMaster(@PathVariable String userId) {
        List list = recentlyViewService.getUserIdWiseRecentlyViewMaster(userId);
        return new ResponseEntity(list, HttpStatus.OK);
    }
    @GetMapping(value = "/getByRecentlyViewId/{recentlyViewId}")
    public ResponseEntity getByRecentlyViewId(@PathVariable String recentlyViewId) {
        RecentlyViewMaster recentlyViewMaster = recentlyViewService.getByRecentlyViewId(recentlyViewId);
        return new ResponseEntity(recentlyViewMaster, HttpStatus.OK);
    }
    @DeleteMapping(value = "/deleteByUserId/{userId}")
    public ResponseEntity deleteByUserId(@PathVariable String userId) {
        Boolean flag = recentlyViewService.deleteByUserId(userId);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

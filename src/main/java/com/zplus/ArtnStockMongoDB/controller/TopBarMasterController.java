package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.SubjectReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.TopBarMasterReq;
import com.zplus.ArtnStockMongoDB.model.TopBarMaster;
import com.zplus.ArtnStockMongoDB.service.TopBarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping(value = "/TopBarMaster")
@RestController
@CrossOrigin(origins = "*")
public class TopBarMasterController {


    @Autowired
    private TopBarService topBarService;

    @PostMapping
    public ResponseEntity createTopBar(@RequestBody TopBarMasterReq topBarMasterReq) {
        Boolean flag = topBarService.createTopBar(topBarMasterReq);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity updateTopBar(@RequestBody TopBarMasterReq topBarMasterReq) {
        Boolean flag = topBarService.updateTopBar(topBarMasterReq);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity getAllTopBars() {
        List<TopBarMaster> topBarMasterList=new ArrayList<>();
        topBarMasterList=topBarService.getAllTopBars();
        return new ResponseEntity(topBarMasterList, HttpStatus.CREATED);
    }

    @GetMapping("/editBytopBarId/{topBarId}")
    public ResponseEntity editBytopBarId(@PathVariable("topBarId") String topBarId) {
        TopBarMaster topBarMaster=new TopBarMaster();
        topBarMaster= topBarService.editBytopBarId(topBarId);
        return new ResponseEntity(topBarMaster, HttpStatus.CREATED);
    }

    @DeleteMapping("/DeleteBytopBarId/{topBarId}")
    public ResponseEntity DeleteBytopBarId(@PathVariable("topBarId") String topBarId) {
        Boolean flag = topBarService.DeleteBytopBarId(topBarId);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

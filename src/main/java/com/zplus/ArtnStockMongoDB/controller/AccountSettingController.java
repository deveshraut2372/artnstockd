package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.AccountSettingReq;

import com.zplus.ArtnStockMongoDB.model.AccountSettingMaster;

import com.zplus.ArtnStockMongoDB.service.AccountSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/accountMaster")
public class AccountSettingController {


    @Autowired
    private AccountSettingService accountSettingService;


    @PostMapping("/create")
    public ResponseEntity createAccountSettingMaster(@RequestBody AccountSettingReq accountSettingReq) {
        Boolean flag = accountSettingService.createAccountSettingMaster(accountSettingReq);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity updateAccountSettingMaster(@RequestBody AccountSettingReq accountSettingReq) {
        Boolean flag = accountSettingService.updateAccountSettingMaster(accountSettingReq);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.OK);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/editAccountSettingMaster/{accountSettingId}")
    public ResponseEntity accountSettingId(@PathVariable String accountSettingId)
    {
        AccountSettingMaster accountSettingMaster = accountSettingService.editAccountSettingMaster(accountSettingId);
        if(accountSettingMaster!=null)
        {
            return new ResponseEntity(accountSettingMaster, HttpStatus.OK);
        } else {
            return new ResponseEntity(accountSettingMaster,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/getAccountSettingMasters/{userId}")
    public ResponseEntity getAccountSettingMasters(@PathVariable String userId)
    {
        List<AccountSettingMaster> accountSettingMasterList=new ArrayList<>();
        accountSettingMasterList=accountSettingService.getAccountSettingMasters(userId);
            return new ResponseEntity(accountSettingMasterList,HttpStatus.OK);
    }







}

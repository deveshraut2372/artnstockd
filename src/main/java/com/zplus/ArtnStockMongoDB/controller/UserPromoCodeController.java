package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.AdminMasterRequest;
import com.zplus.ArtnStockMongoDB.dto.req.UserPromoCodeRequest;
import com.zplus.ArtnStockMongoDB.dto.res.Message;
import com.zplus.ArtnStockMongoDB.dto.res.UserPromoCodeResponse;
import com.zplus.ArtnStockMongoDB.model.AdminMaster;
import com.zplus.ArtnStockMongoDB.model.UserPromoCodeMaster;
import com.zplus.ArtnStockMongoDB.service.UserPromoCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/use_promo_code_master")
public class UserPromoCodeController {
    @Autowired
    private UserPromoCodeService userPromoCodeService;


    @PostMapping("/create")
    public ResponseEntity createUserPromoCodeMaster(@RequestBody UserPromoCodeRequest userPromoCodeRequest) {
        UserPromoCodeResponse userPromoCodeResponse = userPromoCodeService.createUserPromoCodeMaster(userPromoCodeRequest);
        return new ResponseEntity(userPromoCodeResponse, HttpStatus.OK);

    }
    @PutMapping("/update")
    public ResponseEntity updateUserPromoCodeMaster(@RequestBody UserPromoCodeRequest userPromoCodeRequest) {
        Boolean flag = userPromoCodeService.updateUserPromoCodeMaster(userPromoCodeRequest);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity getAllUserPromoCodeMaster() {
        List list = userPromoCodeService.getAllUserPromoCodeMaster();
        return new ResponseEntity(list, HttpStatus.CREATED);
    }
    @GetMapping(value = "/editUserPromoCodeMaster/{userPromoCodeId}")
    public ResponseEntity editUserPromoCodeMaster(@PathVariable String userPromoCodeId)
    {
        UserPromoCodeMaster userPromoCodeMaster = userPromoCodeService.editUserPromoCodeMaster(userPromoCodeId);
        if(userPromoCodeMaster!=null)
        {
            return new ResponseEntity(userPromoCodeMaster, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(userPromoCodeMaster,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getUserWisePromoCodeCodeMaster/{userId}")
    public ResponseEntity getUserWisePromoCodeCodeMaster(@PathVariable String userId) {
        List list = userPromoCodeService.getUserWisePromoCodeCodeMaster(userId);
        return new ResponseEntity(list, HttpStatus.CREATED);
    }
}

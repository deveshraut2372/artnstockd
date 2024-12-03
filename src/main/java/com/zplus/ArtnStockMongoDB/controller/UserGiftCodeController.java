package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.UserGiftCodeRequest;
import com.zplus.ArtnStockMongoDB.dto.req.UserPromoCodeRequest;
import com.zplus.ArtnStockMongoDB.dto.res.UserPromoCodeResponse;
import com.zplus.ArtnStockMongoDB.model.UserGiftCodeMaster;
import com.zplus.ArtnStockMongoDB.model.UserPromoCodeMaster;
import com.zplus.ArtnStockMongoDB.service.UserGiftCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/user_gift_code_master")
public class UserGiftCodeController {

    @Autowired
    private UserGiftCodeService userGiftCodeService;

    @PostMapping("/create")
    public ResponseEntity createUserGiftCodeMaster(@RequestBody UserGiftCodeRequest userGiftCodeRequest) {
        UserPromoCodeResponse userPromoCodeResponse = userGiftCodeService.createUserGiftCodeMaster(userGiftCodeRequest);
        return new ResponseEntity(userPromoCodeResponse, HttpStatus.CREATED);

    }
    @GetMapping("/getAllUserGiftCodeMaster")
    public ResponseEntity getAllUserGiftCodeMaster() {
        List list = userGiftCodeService.getAllUserGiftCodeMaster();
        return new ResponseEntity(list, HttpStatus.CREATED);
    }
    @GetMapping(value = "/editUserGiftCodeMaster/{userGiftCodeId}")
    public ResponseEntity editUserGiftCodeMaster(@PathVariable String userGiftCodeId)
    {
        UserGiftCodeMaster userGiftCodeMaster = userGiftCodeService.editUserGiftCodeMaster(userGiftCodeId);
        if(userGiftCodeMaster!=null)
        {
            return new ResponseEntity(userGiftCodeMaster, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(userGiftCodeMaster,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getUserWiseGiftCodeMaster/{userId}")
    public ResponseEntity getUserWiseGiftCodeMaster(@PathVariable String userId) {
        List list = userGiftCodeService.getUserWiseGiftCodeMaster(userId);
        return new ResponseEntity(list, HttpStatus.CREATED);
    }
}

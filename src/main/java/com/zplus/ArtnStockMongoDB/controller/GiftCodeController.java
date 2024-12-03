package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.GiftCodeRequest;
import com.zplus.ArtnStockMongoDB.dto.req.PromoCodeRequest;
import com.zplus.ArtnStockMongoDB.model.GiftCodeMaster;
import com.zplus.ArtnStockMongoDB.model.PromoCodeMaster;
import com.zplus.ArtnStockMongoDB.service.GiftCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/gift_code_master")
public class GiftCodeController {

    @Autowired
    private GiftCodeService giftCodeService;

    @PostMapping("/create")
    public ResponseEntity createGiftCodeMaster(@RequestBody GiftCodeRequest giftCodeRequest) {
        Boolean flag = giftCodeService.createGiftCodeMaster(giftCodeRequest);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update")
    public ResponseEntity updateGiftCodeMaster(@RequestBody GiftCodeRequest giftCodeRequest) {
        Boolean flag = giftCodeService.updateGiftCodeMaster(giftCodeRequest);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/getAllGiftCodeMaster")
    public ResponseEntity getAllGiftCodeMaster() {
        List list = giftCodeService.getAllGiftCodeMaster();
        return new ResponseEntity(list, HttpStatus.CREATED);
    }
    @GetMapping(value = "/getActiveGiftCodeMaster")
    public ResponseEntity getActiveGiftCodeMaster() {
        List list = giftCodeService.getActiveGiftCodeMaster();
        return new ResponseEntity(list, HttpStatus.CREATED);
    }
    @GetMapping(value = "/editGiftCodeMaster/{giftCodeId}")
    public ResponseEntity editGiftCodeMaster(@PathVariable String giftCodeId)
    {
        GiftCodeMaster giftCodeMaster = giftCodeService.editGiftCodeMaster(giftCodeId);
        if(giftCodeMaster!=null)
        {
            return new ResponseEntity(giftCodeMaster, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(giftCodeMaster,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getGiftCodeMasterByStatus/{status}")
    public ResponseEntity getGiftCodeMasterByStatus(@PathVariable("status") String status) {
        List list = giftCodeService.getGiftCodeMasterByStatus(status);
        return new ResponseEntity(list, HttpStatus.OK);
    }


    @DeleteMapping(value = "/deleteGiftCodeByGiftCodeId/{giftCodeId}")
    public ResponseEntity deleteGiftCodeByGiftCodeId(@PathVariable String giftCodeId)
    {
        Boolean flag= giftCodeService.deleteGiftCodeByGiftCodeId(giftCodeId);
        if(flag)
        {
            return new ResponseEntity(flag, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(flag,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}


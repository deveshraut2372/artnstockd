package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.AdminMasterRequest;
import com.zplus.ArtnStockMongoDB.dto.req.PromoCodeRequest;
import com.zplus.ArtnStockMongoDB.model.AdminMaster;
import com.zplus.ArtnStockMongoDB.model.PromoCodeMaster;
import com.zplus.ArtnStockMongoDB.service.PromoCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/promo_code_master")
public class PromoCodeController {

    @Autowired
    private PromoCodeService promoCodeService;

    @PostMapping("/create")
    public ResponseEntity createPromoCodeMaster(@RequestBody PromoCodeRequest promoCodeRequest) {
        Boolean flag = promoCodeService.createPromoCodeMaster(promoCodeRequest);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update")
    public ResponseEntity updatePromoCodeMaster(@RequestBody PromoCodeRequest promoCodeRequest) {
        Boolean flag = promoCodeService.updatePromoCodeMaster(promoCodeRequest);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/getAllPromoCodeMaster")
    public ResponseEntity getAllPromoCodeMaster() {
        List list = promoCodeService.getAllPromoCodeMaster();
        return new ResponseEntity(list, HttpStatus.CREATED);
    }

    @GetMapping(value = "/getActivePromoCodeMaster")
    public ResponseEntity getActivePromoCodeMaster() {
        List list = promoCodeService.getActivePromoCodeMaster();
        return new ResponseEntity(list, HttpStatus.CREATED);
    }


    @GetMapping(value = "/editPromoCodeMaster/{promoCodeId}")
    public ResponseEntity editPromoCodeMaster(@PathVariable String promoCodeId)
    {
        PromoCodeMaster promoCodeMaster = promoCodeService.editPromoCodeMaster(promoCodeId);
        if(promoCodeMaster!=null)
        {
            return new ResponseEntity(promoCodeMaster, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(promoCodeMaster,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/deletePromoCodeMaster/{promoCodeId}")
    public ResponseEntity deletePromoCodeMaster(@PathVariable String promoCodeId)
    {
      Boolean flag= promoCodeService.deletePromoCodeMaster(promoCodeId);
        if(flag!=null)
        {
            return new ResponseEntity(flag, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(flag,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

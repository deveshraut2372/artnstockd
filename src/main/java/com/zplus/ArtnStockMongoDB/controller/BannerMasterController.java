package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.BannerReqDto;
import com.zplus.ArtnStockMongoDB.model.BannerMaster;
import com.zplus.ArtnStockMongoDB.service.BannerMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/banner_master")
public class BannerMasterController {

    @Autowired
    private BannerMasterService bannerMasterService;

    @PostMapping("/create")
    public ResponseEntity createBannerMaster(@RequestBody BannerReqDto bannerReqDto) {
        Boolean flag = bannerMasterService.createBannerMaster(bannerReqDto);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update")
    public ResponseEntity updateBannerMaster(@RequestBody BannerReqDto bannerReqDto) {
        Boolean flag = bannerMasterService.updateBannerMaster(bannerReqDto);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity getAllBannerMaster() {
        List list = bannerMasterService.getAllBannerMaster();
        return new ResponseEntity(list, HttpStatus.CREATED);
    }
    @GetMapping(value = "/editBannerMaster/{bannerId}")
    public ResponseEntity editBannerMaster(@PathVariable String bannerId)
    {
        BannerMaster bannerMaster = bannerMasterService.editBannerMaster(bannerId);
        if(bannerMaster!=null)
        {
            return new ResponseEntity(bannerMaster, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(bannerMaster,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/getActiveBannerMaster")
    public ResponseEntity getActiveBannerMaster()
    {
        List list = bannerMasterService.getActiveBannerMaster();
        return new ResponseEntity(list,HttpStatus.OK);
    }

    @GetMapping(value="/getBannerTypeWiseList/{bannerType}")
    private ResponseEntity getBannerTypeWiseList(@PathVariable String bannerType){
        List list =bannerMasterService.getBannerTypeWiseList(bannerType);
        return new ResponseEntity(list,HttpStatus.OK);
    }



}

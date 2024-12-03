package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.BannerReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.MediumMasterReq;
import com.zplus.ArtnStockMongoDB.model.BannerMaster;
import com.zplus.ArtnStockMongoDB.model.MediumMaster;
import com.zplus.ArtnStockMongoDB.service.MediumMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin("*")
@RequestMapping(value = "/medium_Master")
public class MediumMasterController {

    @Autowired
    private MediumMasterService mediumMasterService;


    @PostMapping("/createMediumMaster")
    public ResponseEntity createMediumMaster(@RequestBody MediumMasterReq mediumMasterReq) {
        Boolean flag = mediumMasterService.createMediumMaster(mediumMasterReq);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateMediumMaster")
    public ResponseEntity updateMediumMaster(@RequestBody MediumMasterReq mediumMasterReq) {
        Boolean flag = mediumMasterService.updateMediumMaster(mediumMasterReq);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity getAllMediumMaster() {
        List list = mediumMasterService.getAllMediumMaster();
        return new ResponseEntity(list, HttpStatus.CREATED);
    }
    @GetMapping(value = "/editmediumMaster/{mediumId}")
    public ResponseEntity editmediumMaster(@PathVariable String mediumId)
    {
        MediumMaster mediumMaster = mediumMasterService.editmediumMaster(mediumId);
        if(mediumMaster!=null)
        {
            return new ResponseEntity(mediumMaster, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(mediumMaster,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/getActiveMediumMaster")
    public ResponseEntity getActiveMediumMaster()
    {
        List list = mediumMasterService.getActiveMediumMaster();
        return new ResponseEntity(list,HttpStatus.OK);
    }


    @GetMapping(value = "/getMediumMasterByStatus/{status}")
    public ResponseEntity getMediumMasterByStatus(@PathVariable("status") String status)
    {
        List list = mediumMasterService.getMediumMasterByStatus(status);
        return new ResponseEntity(list,HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteMediumMaster/{mediumId}")
    public ResponseEntity deleteMediumMaster(@PathVariable String mediumId)
    {
        Boolean flag = mediumMasterService.deleteMediumMaster(mediumId);
        if(flag)
        {
            return new ResponseEntity(flag, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(flag,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



//    @GetMapping(value="/getBannerTypeWiseList/{bannerType}")
//    private ResponseEntity getBannerTypeWiseList(@PathVariable String bannerType){
//        List list =bannerMasterService.getBannerTypeWiseList(bannerType);
//        return new ResponseEntity(list,HttpStatus.OK);
//    }





}

package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.res.ApprovedRatioRes;
import com.zplus.ArtnStockMongoDB.model.FileUploadLimitMaster;
import com.zplus.ArtnStockMongoDB.service.FileUploadLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/fileUploadLimit")
@CrossOrigin(origins = "*")
public class FileUploadLimitController {

    @Autowired
    private FileUploadLimitService fileUploadLimitService;


    // calculate weekly approved percentage  using sechedular
//    called every mondata
    @Scheduled(cron = "0 0 1 * * MON")
    @GetMapping("/getCalculatedValues")
    public ResponseEntity CalculateApprovedPercentage()
    {
       Boolean flag;
       flag=fileUploadLimitService.CalculateApprovedPercentage();
       return new ResponseEntity(flag, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAllFileUploadLimitMaster()
    {
        List<FileUploadLimitMaster> fileUploadLimitMasterList=new ArrayList<>();
        fileUploadLimitMasterList=fileUploadLimitService.getAllFileUploadLimitMaster();
        return new ResponseEntity( fileUploadLimitMasterList,HttpStatus.OK);
    }

    @GetMapping("/getUserIdWiseRatio/{userId}")
    public ResponseEntity<ApprovedRatioRes> getUserIdWiseRatio(@PathVariable("userId") String userId)
    {
        ApprovedRatioRes approvedRatioRes=new ApprovedRatioRes();
        approvedRatioRes=fileUploadLimitService.getUserIdWiseRatio(userId);
        return new ResponseEntity(approvedRatioRes, HttpStatus.OK);
    }
}

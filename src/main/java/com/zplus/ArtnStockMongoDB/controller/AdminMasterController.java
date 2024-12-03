package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.AdminMasterRequest;
import com.zplus.ArtnStockMongoDB.dto.req.ArtMasterFilterReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.BannerReqDto;
import com.zplus.ArtnStockMongoDB.model.AdminMaster;
import com.zplus.ArtnStockMongoDB.model.BannerMaster;
import com.zplus.ArtnStockMongoDB.service.AdminMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/admin_master")
public class AdminMasterController {

    @Autowired
    private AdminMasterService adminMasterService;

    @PostMapping("/create")
    public ResponseEntity createAdminMaster(@RequestBody AdminMasterRequest adminMasterRequest) {
        Boolean flag = adminMasterService.createAdminMaster(adminMasterRequest);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity updateAdminMaster(@RequestBody AdminMasterRequest adminMasterRequest) {
        Boolean flag = adminMasterService.updateAdminMaster(adminMasterRequest);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity getAllAdminMaster() {
        List list = adminMasterService.getAllAdminMaster();
        return new ResponseEntity(list, HttpStatus.CREATED);
    }


    @GetMapping(value = "/editAdminMaster/{adminId}")
    public ResponseEntity editAdminMaster(@PathVariable String adminId)
    {
        AdminMaster adminMaster = adminMasterService.editAdminMaster(adminId);
        if(adminMaster!=null)
        {
            return new ResponseEntity(adminMaster, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(adminMaster,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/changeTermsAndCondition")
    public ResponseEntity changeTermsAndCondition()
    {
        Boolean flag;
        flag=adminMasterService.changeTermsAndCondition();
        if(flag)
        {
          return new ResponseEntity("Success",HttpStatus.OK);
        }else
        {
            return new ResponseEntity("Unsuccess",HttpStatus.BAD_REQUEST);
        }

    }

//    filter

//    @PostMapping("/AdminArtMasterFilter")
//    public ResponseEntity AdminArtMasterFilter(@RequestBody ArtMasterFilterReqDto artMasterFilterReqDto) {
//        List list = adminMasterService.AdminArtMasterFilter(artMasterFilterReqDto);
//        return new ResponseEntity(list, HttpStatus.OK);
//    }




}

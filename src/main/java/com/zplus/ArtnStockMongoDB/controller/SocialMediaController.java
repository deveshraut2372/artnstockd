package com.zplus.ArtnStockMongoDB.controller;


import com.zplus.ArtnStockMongoDB.dto.req.SocialMediaMasterReq;
import com.zplus.ArtnStockMongoDB.model.SocialMediaAdminMaster;
import com.zplus.ArtnStockMongoDB.service.SocialMediaMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/social_media_Master")
@CrossOrigin(origins = "*")
public class SocialMediaController {

    @Autowired
    private SocialMediaMasterService socialMediaMasterService;


    @PostMapping
    public ResponseEntity createSocialMedia(@RequestBody SocialMediaMasterReq socialMediaMasterReq) {
        Boolean flag = socialMediaMasterService.createLinkMaster(socialMediaMasterReq);
        if (Boolean.TRUE.equals(flag)) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity updateSocialMedia(@RequestBody SocialMediaMasterReq socialMediaMasterReq) {
        Boolean flag = socialMediaMasterService.updateLinkMaster(socialMediaMasterReq);
        if (Boolean.TRUE.equals(flag)) {
            return new ResponseEntity(flag, HttpStatus.OK);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getActiveSocialMedia")
    public ResponseEntity getActiveSocialMedia() {
        List<SocialMediaAdminMaster> socialMediaAdminMasterList = new ArrayList();
        socialMediaAdminMasterList = socialMediaMasterService.getActiveSocialMedia();
        return new ResponseEntity(socialMediaAdminMasterList, HttpStatus.OK);
    }


    @GetMapping("/getAllSocialMedia")
    public ResponseEntity getAllSocialMedia() {
        List<SocialMediaAdminMaster> socialMediaAdminMasterList = new ArrayList();
        socialMediaAdminMasterList = socialMediaMasterService.getAllSocialMedia();
        return new ResponseEntity(socialMediaAdminMasterList, HttpStatus.OK);
    }

    @DeleteMapping("/deleteSocialMedia/{socialMediaAdminId}")
    public ResponseEntity deleteSocialMedia(@PathVariable("socialMediaAdminId")  String socialMediaAdminId) {
        Boolean flag = socialMediaMasterService.deleteSocialMedia(socialMediaAdminId);
        if (Boolean.TRUE.equals(flag)) {
            return new ResponseEntity(flag, HttpStatus.OK);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





}

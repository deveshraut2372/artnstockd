package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.UserMessageMasterRequest;
import com.zplus.ArtnStockMongoDB.dto.res.MainResDto;
import com.zplus.ArtnStockMongoDB.model.UserMessageMaster;
import com.zplus.ArtnStockMongoDB.service.UserMessageMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usermessage")
@CrossOrigin(origins = "*")
public class UserMessageMasterController {
    @Autowired
    private UserMessageMasterService userMessageMasterService;
    @PostMapping("/create")
    public ResponseEntity create(@RequestBody UserMessageMasterRequest userMessageMasterRequest){
        MainResDto mainResDto = this.userMessageMasterService.create(userMessageMasterRequest);
        if (Boolean.TRUE.equals(mainResDto.getFlag())){
            return new ResponseEntity(mainResDto, HttpStatus.OK);
        }else {
            return new ResponseEntity(mainResDto, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody UserMessageMasterRequest userMessageMasterRequest){
        MainResDto mainResDto = this.userMessageMasterService.update(userMessageMasterRequest);
        if (Boolean.TRUE.equals(mainResDto.getFlag())){
            return new ResponseEntity(mainResDto, HttpStatus.OK);
        }else {
            return new ResponseEntity(mainResDto, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getbyid/{userMessageId}")
    public ResponseEntity getById(@PathVariable("userMessageId") String userMessageId){
        UserMessageMaster userMessageMaster = this.userMessageMasterService.getById(userMessageId);
        return new ResponseEntity(userMessageMaster, HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity getAll(){
        List<UserMessageMaster> userMessageMasters = this.userMessageMasterService.getAll();
        return new ResponseEntity(userMessageMasters, HttpStatus.OK);
    }

    @GetMapping("/getUserIdWiseMessage/{userId}")
    public ResponseEntity getUserIdWiseMessage(@PathVariable("userId") String userId){
       UserMessageMaster userMessageMaster = this.userMessageMasterService.getUserIdWiseMessage(userId);
        System.out.println("  UserMessage Master ="+userMessageMaster.toString());
        return new ResponseEntity(userMessageMaster, HttpStatus.OK);
    }

    @DeleteMapping("/deletebyid/{userMessageId}")
    public ResponseEntity deleteById(@PathVariable("userMessageId") String userMessageId){
        MainResDto mainResDto = this.userMessageMasterService.deleteById(userMessageId);
        if (Boolean.TRUE.equals(mainResDto.getFlag())){
            return new ResponseEntity(mainResDto, HttpStatus.OK);
        }else {
            return new ResponseEntity(mainResDto, HttpStatus.BAD_REQUEST);
        }
    }





}
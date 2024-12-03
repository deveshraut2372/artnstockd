package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.LimitedEditionReqDto;
import com.zplus.ArtnStockMongoDB.model.LimitedEditionMaster;
import com.zplus.ArtnStockMongoDB.service.LimitedEditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/limited_edition_master")
public class LimitedEditionController {

    @Autowired
    private LimitedEditionService limitedEditionService;

    @PostMapping("/create")
    public ResponseEntity createLimitedEditionMaster(@RequestBody LimitedEditionReqDto limitedEditionReqDto) {
        Boolean flag = limitedEditionService.createLimitedEditionMaster(limitedEditionReqDto);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update")
    public ResponseEntity updateLimitedEditionMaster(@RequestBody LimitedEditionReqDto limitedEditionReqDto) {
        Boolean flag = limitedEditionService.updateLimitedEditionMaster(limitedEditionReqDto);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity getAllLimitedEditionMaster() {
        List list = limitedEditionService.getAllLimitedEditionMaster();
        return new ResponseEntity(list, HttpStatus.CREATED);
    }
    @GetMapping(value = "/editLimitedEditionMaster/{limitedEditionId}")
    public ResponseEntity editLimitedEditionMaster(@PathVariable String limitedEditionId)
    {
        LimitedEditionMaster limitedEditionMaster = limitedEditionService.editLimitedEditionMaster(limitedEditionId);
        if(limitedEditionMaster!=null)
        {
            return new ResponseEntity(limitedEditionMaster, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(limitedEditionMaster,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/getActiveList")
    public ResponseEntity getActiveLimitedEditionMaster()
    {
        List list = limitedEditionService.getActiveLimitedEditionMaster();
        return new ResponseEntity(list,HttpStatus.OK);

    }
    @GetMapping(value="/getTypeWiseList/{type}")
    private ResponseEntity getTypeWiseList(@PathVariable String type){
        LimitedEditionMaster limitedEditionMaster =limitedEditionService.getTypeWiseList(type);
        return new ResponseEntity(limitedEditionMaster,HttpStatus.OK);
    }
}

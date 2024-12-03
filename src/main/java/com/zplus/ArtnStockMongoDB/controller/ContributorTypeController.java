package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.ContributorTypeReq;
import com.zplus.ArtnStockMongoDB.dto.req.CountryMasterRequest;
import com.zplus.ArtnStockMongoDB.model.ContributorTypeMaster;
import com.zplus.ArtnStockMongoDB.model.CountryMaster;
import com.zplus.ArtnStockMongoDB.service.ContributorTypeService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/contributor_Type")
public class ContributorTypeController {

    @Autowired
    private ContributorTypeService contributorTypeService;

    @PostMapping("/create")
    public ResponseEntity createContributorTypeMaster(@RequestBody ContributorTypeReq contributorTypeReq) {
        Boolean flag = contributorTypeService.createContributorTypeMaster(contributorTypeReq);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity updateContributorTypeMaster(@RequestBody ContributorTypeReq contributorTypeReq) {
        Boolean flag = contributorTypeService.updateContributorTypeMaster(contributorTypeReq);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity getAllContributorTypeMaster() {
        List list = contributorTypeService.getAllContributorTypeMaster();
        return new ResponseEntity(list, HttpStatus.CREATED);
    }
    @GetMapping(value = "/editContributorTypeMaster/{contributorTypeId}")
    public ResponseEntity editContributorTypeMaster(@PathVariable String contributorTypeId)
    {
        ContributorTypeMaster contributorTypeMaster = contributorTypeService.editCountryMaster(contributorTypeId);
        if(contributorTypeMaster!=null)
        {
            return new ResponseEntity(contributorTypeMaster, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(contributorTypeMaster,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/getActiveContributorTypeMaster")
    public ResponseEntity getActiveContributorTypeMaster()
    {
        List list = contributorTypeService.getActiveContributorTypeMaster();
        return new ResponseEntity(list,HttpStatus.OK);

    }

    @DeleteMapping(value = "/deleteByContributorTypeId/{contributorTypeId}")
    public ResponseEntity deleteByContributorTypeId(@PathVariable("contributorTypeId") String contributorTypeId) {
        Boolean flag = contributorTypeService.deleteByContributorTypeId(contributorTypeId);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.OK);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

package com.zplus.ArtnStockMongoDB.controller;




// Add Geeta

import com.zplus.ArtnStockMongoDB.dto.req.ContributorImageFlagChangeReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.ContributorImageStatusChangeReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.ContributorImageUploadReq;
import com.zplus.ArtnStockMongoDB.dto.res.UserIdWiseContributorResDto;
import com.zplus.ArtnStockMongoDB.model.ContributorImageUploadMaster;
import com.zplus.ArtnStockMongoDB.service.ContributorImageUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/contributor_image_master")
public class ContributorImageUploadController {
@Autowired
    private ContributorImageUploadService contributorImageUploadService;



    @PostMapping("/createContributorImageUpload")
    public ResponseEntity createContributorImageUpload(@RequestBody List<ContributorImageUploadReq> contributorImageList) {
        Boolean flag = contributorImageUploadService.createContributorImageUpload(contributorImageList);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/update")
    public ResponseEntity updateContributorImageUpload(@RequestBody ContributorImageUploadReq contributorImageUploadReq) {
        Boolean flag = contributorImageUploadService.updateContributorImageUpload(contributorImageUploadReq);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity getAllContributorImageUpload() {
        List list = contributorImageUploadService.getAllContributorImageUpload();
        return new ResponseEntity(list, HttpStatus.CREATED);
    }
    @GetMapping(value = "/editContributorImageUpload/{contributorImageUploadId}")
    public ResponseEntity editContributorImageUploadr(@PathVariable String contributorImageUploadId)
    {
        ContributorImageUploadMaster contributorImageUploadMaster = contributorImageUploadService.editContributorImageUploadr(contributorImageUploadId);
        if(contributorImageUploadMaster!=null)
        {
            return new ResponseEntity(contributorImageUploadMaster, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(contributorImageUploadMaster,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/getFlagTrueContributorImageUpload/{userId}")
    public ResponseEntity getFlagTrueContributorImageUpload(@PathVariable String userId)
    {
        List list = contributorImageUploadService.getFlagTrueContributorImageUpload(userId);
        return new ResponseEntity(list,HttpStatus.OK);

    }
    @GetMapping(value = "/getUserIdWiseContributorMaster/{userId}")
    public ResponseEntity getUserIdWiseContributorMaster(@PathVariable String userId)
    {
        List<ContributorImageUploadMaster>  list = contributorImageUploadService.getUserIdWiseContributorMaster(userId);
        return new ResponseEntity(list,HttpStatus.OK);

    }
    @PostMapping(value = "/ContributorMaster/changeImageFlag")
    public ResponseEntity changeImageFlag(@RequestBody ContributorImageFlagChangeReqDto contributorImageFlagChangeReqDto)
    {
        Boolean flag = contributorImageUploadService.changeImageFlag(contributorImageFlagChangeReqDto);

        return new ResponseEntity(flag,HttpStatus.OK);

    }
    @PostMapping(value = "/ContributorMasterChangeStatus/")
    public ResponseEntity ContributorMasterChangeStatus(@RequestBody ContributorImageStatusChangeReqDto contributorImageStatusChangeReqDto)
    {
        Boolean flag = contributorImageUploadService.ContributorMasterChangeStatus(contributorImageStatusChangeReqDto);

        return new ResponseEntity(flag,HttpStatus.OK);

    }
    @GetMapping(value = "/getStatusWiseContributorMaster/{status}")
    public ResponseEntity getStatusWiseContributorMaster(@PathVariable String status)
    {
        List<ContributorImageUploadMaster>  list = contributorImageUploadService.getStatusWiseContributorMaster(status);
        return new ResponseEntity(list,HttpStatus.OK);

    }
}

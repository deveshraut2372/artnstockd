package com.zplus.ArtnStockMongoDB.controller;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.databind.ser.std.StdArraySerializers;
import com.zplus.ArtnStockMongoDB.dto.req.DeleteImageReq;
import com.zplus.ArtnStockMongoDB.dto.req.DraftMasterRequest;
import com.zplus.ArtnStockMongoDB.model.DraftMaster;
import com.zplus.ArtnStockMongoDB.service.DraftMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/draft_master")
public class DraftMasterController {

    @Autowired
    private DraftMasterService draftService;

    @PostMapping("/create")
    public ResponseEntity createDraftMaster(@RequestBody DraftMasterRequest draftMasterRequest) {
        Boolean flag = draftService.createDraftMaster(draftMasterRequest);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/update")
    public ResponseEntity updateDraftMaster(@RequestBody DraftMasterRequest draftMasterRequest) {
        Boolean flag = draftService.updateDraftMaster(draftMasterRequest);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/editDraftMaster/{draftId}")
    public ResponseEntity editDraftMaster(@PathVariable String draftId)
    {
        DraftMaster master = draftService.editDraftMaster(draftId);
        if(master!=null)
        {
            return new ResponseEntity(master, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(master,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/getDraftMasterList")
    public ResponseEntity getDraftMasterList() {
        List list = draftService.getDraftMasterList();
        return new ResponseEntity(list, HttpStatus.CREATED);
    }



    @GetMapping(value = "/getContributorWiseDraftMasterList/{userId}")
    public ResponseEntity getContributorWiseDraftMasterList( @PathVariable String userId) {
        List list = draftService.getContributorWiseDraftMasterList(userId);
        return new ResponseEntity(list, HttpStatus.CREATED);
    }


    @GetMapping(value = "/getDraftMasterByStatusAndActiveStatus/{status}/{activeStatus}/{userId}")
    public ResponseEntity getDraftMasterByStatusAndActiveStatus(@PathVariable("status") String status,@PathVariable("activeStatus") Boolean activeStatus,@PathVariable String userId)
    {
        DraftMaster draftMaster=new DraftMaster();
        draftMaster=draftService.getDraftMasterByStatusAndActiveStatus(status,activeStatus,userId);
        return new ResponseEntity( draftMaster,HttpStatus.OK);
    }

    @GetMapping(value = "/getDraftMasterByStatusAndActiveStatusCount/{status}/{activeStatus}/{userId}")
    public ResponseEntity getDraftMasterByStatusAndActiveStatusCount(@PathVariable("status") String status,@PathVariable("activeStatus") Boolean activeStatus,@PathVariable String userId)
    {
        Integer cnt =draftService.getDraftMasterByStatusAndActiveStatusCount(status,activeStatus,userId);
        return new ResponseEntity( cnt,HttpStatus.OK);
    }

    @GetMapping(value = "/getDraftMasterByStatus/{status}/{userId}")
    public ResponseEntity getDraftMasterByStatus(@PathVariable("status") String status,@PathVariable String userId)
    {
        DraftMaster draftMaster=new DraftMaster();
        draftMaster=draftService.getDraftMasterByStatus(status,userId);
        return new ResponseEntity( draftMaster,HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteDraftByDraftId/{draftId}")
    public ResponseEntity deleteDraftByDraftId(@PathVariable("draftId") String draftId)
    {
        Boolean flag=false;
        flag=draftService.deleteDraftByDraftId(draftId);
        if(flag)
        {
            return new ResponseEntity(flag, HttpStatus.OK);
        }else{
            return new ResponseEntity(flag,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/deleteImage")
    public ResponseEntity deleteImage(@RequestBody DeleteImageReq deleteImageReq)
    {
        Boolean flag=false;
        flag=draftService.deleteImage(deleteImageReq);
        if(flag)
        {
            return new ResponseEntity(flag, HttpStatus.OK);
        }else{
            return new ResponseEntity(flag,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

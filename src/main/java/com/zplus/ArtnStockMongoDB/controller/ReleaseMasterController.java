package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.ReleaseMasterRequest;
import com.zplus.ArtnStockMongoDB.dto.req.ReleaseSortReq;
import com.zplus.ArtnStockMongoDB.dto.req.ReleasesCountReq;
import com.zplus.ArtnStockMongoDB.model.ReleaseMaster;
import com.zplus.ArtnStockMongoDB.service.ReleaseMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/release_master")
public class ReleaseMasterController {


    @Autowired
    private ReleaseMasterService releaseMasterService;

    @PostMapping(value = "/createReleaseMaster")
    public ResponseEntity createReleaseMaster(@RequestBody ReleaseMasterRequest releaseMasterRequest) {
        Boolean flag = releaseMasterService.createReleaseMaster(releaseMasterRequest);
        if (Boolean.TRUE.equals(flag)) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/updateReleaseMaster")
    public ResponseEntity updateReleaseMaster(@RequestBody  ReleaseMasterRequest releaseMasterRequest) {
        Boolean flag = releaseMasterService.updateReleaseMaster(releaseMasterRequest);
        if (Boolean.TRUE.equals(flag)) {
            return new ResponseEntity(flag, HttpStatus.OK);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/getAllReleaseMaster")
    public ResponseEntity getAllReleaseMaster() {
        List list = releaseMasterService.getAllReleaseMaster();
        return new ResponseEntity(list, HttpStatus.CREATED);
    }
    @GetMapping(value = "/editReleaseMaster/{releaseId}")
    public ResponseEntity editReleaseMaster(@PathVariable String releaseId) {
        ReleaseMaster releaseMaster = releaseMasterService.editReleaseMaster(releaseId);
        if (releaseMaster != null) {
            return new ResponseEntity(releaseMaster, HttpStatus.OK);
        } else {
            return new ResponseEntity(releaseMaster, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/typeWiseReleaseMaster/{type}")
    public ResponseEntity typeWiseReleaseMaster(@PathVariable String type) {
        List<ReleaseMaster> list = releaseMasterService.typeWiseReleaseMaster(type);
        if (list != null) {
            return new ResponseEntity(list, HttpStatus.OK);
        } else {
            return new ResponseEntity(list, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getUserIdWiseReleaseMaster/{userId}")
    public ResponseEntity getUserIdWiseReleaseMaster(@PathVariable String userId) {
        List<ReleaseMaster> list = releaseMasterService.getUserIdWiseReleaseMaster(userId);
        if (list != null) {
            return new ResponseEntity(list, HttpStatus.OK);
        } else {
            return new ResponseEntity(list, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getReleasesByArtDetailsId/{artDetailsId}")
    public ResponseEntity getReleasesByArtDetailsId(@PathVariable String artDetailsId)
    {
        List<ReleaseMaster> releaseMasterList=new ArrayList<>();
        releaseMasterList=releaseMasterService.getReleasesByArtDetailsId(artDetailsId);
        return new ResponseEntity(releaseMasterList, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteByReleaseId/{releaseId}")
    public ResponseEntity deleteByRelasesId(@PathVariable String releaseId)
    {
        Boolean flag;
        flag=releaseMasterService.deleteByRelasesId(releaseId);
        if(flag)
        {
            return new ResponseEntity(flag,HttpStatus.OK);
        }else {
            return new ResponseEntity(flag,HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/DeleteReleasesByArtDetailsId/{artDetailsId}")
    public ResponseEntity DeleteReleasesByArtDetailsId(@PathVariable String artDetailsId)
    {
        Boolean flag;
        flag=releaseMasterService.deleteReleasesByArtDetailsId(artDetailsId);
        System.out.println( flag);
        if(flag)
        {
            return new ResponseEntity(flag,HttpStatus.OK);
        }else {
            return new ResponseEntity(flag,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/getReleasesByUserIdAndType")
    public ResponseEntity getReleasesByUserIdAndType(@RequestParam("userId") String userId, @RequestParam("type") String type)
    {
        List<ReleaseMaster> releaseMasterList=releaseMasterService.getReleasesByUserIdAndType(userId,type);
        return new ResponseEntity(releaseMasterList,HttpStatus.OK);
    }

    @GetMapping(value = "/getReleasesByFileName/{fileName}")
    public ResponseEntity getReleasesByFileName(@PathVariable("fileName") String fileName)
    {
        List<ReleaseMaster> releaseMasterList=releaseMasterService.getReleasesByFileName(fileName);
        return new ResponseEntity(releaseMasterList,HttpStatus.OK);
    }

    @PostMapping(value = "/getReleasesByFileNameAndSortTypeByUserId")
    public ResponseEntity getReleasesByFileNameAndSortType(@RequestBody ReleaseSortReq releaseSortReq)
    {
        List<ReleaseMaster> releaseMasterList=releaseMasterService.getReleasesByFileNameAndSortType(releaseSortReq.getFileName(),releaseSortReq.getSortType(),releaseSortReq.getUserId());
        return new ResponseEntity(releaseMasterList,HttpStatus.OK);
    }

    @PostMapping(value = "/getReleasesBySortTypeAndUserId")
    public ResponseEntity getReleasesBySortTypeAndUserId(@RequestBody ReleaseSortReq releaseSortReq)
    {
//      List<ReleaseMaster> releaseMasterList=releaseMasterService.gerReleasesBySortTypeAndUserId(releaseSortReq);
        List<ReleaseMaster> releaseMasterList=releaseMasterService.getReleasesBySortTypeAndUserId(releaseSortReq);
        return new ResponseEntity<>(releaseMasterList,HttpStatus.OK);
    }

    @PostMapping(value = "/getReleasesCountByUserIdAndType")
    public ResponseEntity getReleasesCountByUserIdAndType(@RequestBody ReleasesCountReq releasesCountReq )
    {
        int cnt =releaseMasterService.getReleasesCountByUserIdAndType(releasesCountReq.getUserId(),releasesCountReq.getType());
        return new ResponseEntity(cnt,HttpStatus.OK);
    }

    @GetMapping("/getReleasesByArtId/{artId}")
    public ResponseEntity getReleasesByArtId(@PathVariable("artId") String artId)
    {
        List<ReleaseMaster> releaseMasterList=releaseMasterService.getReleasesByArtId(artId);
        return new ResponseEntity<>(releaseMasterList,HttpStatus.OK);
    }



}

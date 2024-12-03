package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.ArtDimensionMasterReqDto;
import com.zplus.ArtnStockMongoDB.model.ArtDimensionMaster;
import com.zplus.ArtnStockMongoDB.service.ArtDimensionMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/art_dimension_master")
public class ArtDimensionMasterController {

    @Autowired
    private ArtDimensionMasterService artDimensionMasterService;

    @PostMapping("/create")
    public ResponseEntity createArtDimensionMasterMaster(@RequestBody ArtDimensionMasterReqDto artDimensionMasterReqDto) {
        Boolean flag = artDimensionMasterService.createArtDimensionMasterMaster(artDimensionMasterReqDto);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update")
    public ResponseEntity updateArtDimensionMasterMaster(@RequestBody ArtDimensionMasterReqDto artDimensionMasterReqDto) {
        Boolean flag = artDimensionMasterService.updateArtDimensionMasterMaster(artDimensionMasterReqDto);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity getAllArtDimensionMaster() {
        List list = artDimensionMasterService.getAllArtDimensionMaster();
        return new ResponseEntity(list, HttpStatus.CREATED);
    }
    @GetMapping(value = "/editArtDimensionMaster/{artDimensionId}")
    public ResponseEntity editArtDimensionMaster(@PathVariable String artDimensionId)
    {
        ArtDimensionMaster artDimensionMaster = artDimensionMasterService.editArtDimensionMaster(artDimensionId);
        if(artDimensionMaster!=null)
        {
            return new ResponseEntity(artDimensionMaster, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(artDimensionMaster,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/getActiveList")
    public ResponseEntity getActiveArtDimensionMaster()
    {
        List list = artDimensionMasterService.getActiveArtDimensionMaster();
        return new ResponseEntity(list,HttpStatus.OK);

    }
}


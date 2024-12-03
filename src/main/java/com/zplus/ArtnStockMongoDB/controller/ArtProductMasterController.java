package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.ArtMasterFilterReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.ArtMasterReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.ArtProductReqDto;
import com.zplus.ArtnStockMongoDB.model.ArtProductMaster;
import com.zplus.ArtnStockMongoDB.service.ArtProductMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/art_product_master")
public class ArtProductMasterController {

    @Autowired
    private ArtProductMasterService artProductMasterService;

    @PostMapping("/create")
    public ResponseEntity createArtProductMaster(@RequestBody ArtProductReqDto artProductReqDto) {
        Boolean flag = artProductMasterService.createArtProductMaster(artProductReqDto);
        return new ResponseEntity(flag, HttpStatus.OK);
    }

    @PostMapping("/createD")
    public ResponseEntity createArtProductMasterD(@RequestBody ArtProductReqDto artProductReqDto) {
        ArtProductMaster artProductMaster=new ArtProductMaster();
        Boolean flag = artProductMasterService.createArtProductMasterD(artProductReqDto);
        return new ResponseEntity(flag, HttpStatus.OK);
    }

    @GetMapping(value = "/getActiveArtProductMaster")
    public ResponseEntity getActiveArtProductMaster()
    {
        List list = artProductMasterService.getActiveArtProductMaster();
        return new ResponseEntity(list,HttpStatus.OK);
    }

    @GetMapping(value = "/getArtProductIdData/{artProductId}")
    public ResponseEntity getArtProductIdData(@PathVariable String artProductId)
    {
        ArtProductMaster artProductMaster = artProductMasterService.getArtProductIdData(artProductId);
        return new ResponseEntity(artProductMaster,HttpStatus.OK);
    }

    @GetMapping(value = "/getAllArtProductMaster")
    public ResponseEntity getAllArtProductMaster()
    {
        List list = artProductMasterService.getAllArtProductMaster();
        return new ResponseEntity(list,HttpStatus.OK);
    }



}

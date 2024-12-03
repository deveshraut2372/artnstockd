//package com.zplus.ArtnStockMongoDB.controller;
//
//import com.zplus.ArtnStockMongoDB.dto.req.CommonMasterReqDto;
//import com.zplus.ArtnStockMongoDB.dto.req.UpdateCommonMasterReqDto;
//import com.zplus.ArtnStockMongoDB.model.CommonMaster;
//import com.zplus.ArtnStockMongoDB.service.CommonMasterService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@CrossOrigin("*")
//@RequestMapping(value = "/common_master")
//public class CommonMasterController {
//
//    @Autowired
//    private CommonMasterService commonMasterService;
//
//    @PostMapping("/create")
//    public ResponseEntity createCommonMaster(@RequestBody CommonMasterReqDto commonMasterReqDto) {
//        Boolean flag = commonMasterService.createCommonMaster(commonMasterReqDto);
//        if (flag) {
//            return new ResponseEntity(flag, HttpStatus.CREATED);
//        } else {
//            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @PutMapping("/update")
//    public ResponseEntity updateCommonMaster(@RequestBody UpdateCommonMasterReqDto updateCommonMasterReqDto) {
//        Boolean flag = commonMasterService.updateCommonMaster(updateCommonMasterReqDto);
//        if (flag) {
//            return new ResponseEntity(flag, HttpStatus.CREATED);
//        } else {
//            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @GetMapping
//    public ResponseEntity getAllCommonMaster() {
//        List list = commonMasterService.getAllCommonMaster();
//        return new ResponseEntity(list, HttpStatus.CREATED);
//    }
//    @GetMapping(value = "/editCommonMaster/{commonId}")
//    public ResponseEntity editCommonMaster(@PathVariable String commonId)
//    {
//        CommonMaster commonMaster = commonMasterService.editCommonMaster(commonId);
//        if(commonMaster!=null)
//        {
//            return new ResponseEntity(commonMaster, HttpStatus.OK);
//        }
//        else
//        {
//            return new ResponseEntity(commonMaster,HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @GetMapping(value = "/getActiveCommonMaster")
//    public ResponseEntity getActiveCommonMaster()
//    {
//        List list = commonMasterService.getActiveCommonMaster();
//        return new ResponseEntity(list,HttpStatus.OK);
//
//    }
//    @GetMapping(value = "/getArtIddWiseCommonMaster/{artId}")
//    public ResponseEntity getArtIddWiseCommonMaster(@PathVariable String artId)
//    {
//        List list = commonMasterService.getArtIddWiseCommonMaster(artId);
//        return new ResponseEntity(list,HttpStatus.OK);
//
//    }
//    @GetMapping(value = "/getProductIddWiseCommonMaster/{productId}")
//    public ResponseEntity getProductIddWiseCommonMaster(@PathVariable String productId)
//    {
//        List list = commonMasterService.getProductIddWiseCommonMaster(productId);
//        return new ResponseEntity(list,HttpStatus.OK);
//
//    }
//
//
//}

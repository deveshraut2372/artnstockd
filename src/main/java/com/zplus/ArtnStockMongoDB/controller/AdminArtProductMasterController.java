package com.zplus.ArtnStockMongoDB.controller;


import com.zplus.ArtnStockMongoDB.dto.req.AdminArtProductRequest;
import com.zplus.ArtnStockMongoDB.dto.req.ArtMasterFilterReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.ArtProductReq;
import com.zplus.ArtnStockMongoDB.model.AdminArtProductMaster;
import com.zplus.ArtnStockMongoDB.service.AdminArtProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/admin_Art_Product_master")
public class AdminArtProductMasterController {

    @Autowired
    private AdminArtProductService adminArtProductService;

    @PostMapping
    public ResponseEntity createAdminArtProductMaster(@RequestBody AdminArtProductRequest adminArtProductRequest) {
        Boolean flag = adminArtProductService.createAdminArtProductMaster(adminArtProductRequest);
        return new ResponseEntity(flag, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity updateAdminArtProductMaster(@RequestBody AdminArtProductRequest adminArtProductRequest) {
        Boolean flag = adminArtProductService.updateAdminArtProductMaster(adminArtProductRequest);
        return new ResponseEntity(flag, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAllAdminArtProductMaster() {
        List<AdminArtProductMaster> adminArtProductMasterList=new ArrayList<>();
        adminArtProductMasterList = adminArtProductService.getAllAdminArtProductMaster();
        return new ResponseEntity(adminArtProductMasterList, HttpStatus.OK);
    }

    @GetMapping("/getActiveArtProducts")
    private ResponseEntity getActiveArtProducts()
    {
        List<AdminArtProductMaster> adminArtProductMasterList=new ArrayList<>();
        adminArtProductMasterList = adminArtProductService.getActiveArtProducts();
        return new ResponseEntity(adminArtProductMasterList, HttpStatus.OK);
    }


    @GetMapping(value = "/getByAdminArtProductId/{adminArtProductId}")
    private ResponseEntity getByAdminArtProductId(@PathVariable("adminArtProductId") String adminArtProductId) {

        adminArtProductId="6659b9154b9d20536fa64113";
       Optional<AdminArtProductMaster> adminArtProductMaster= Optional.of(new AdminArtProductMaster());
       adminArtProductMaster=adminArtProductService.getByAdminArtProductId(adminArtProductId);
        return new ResponseEntity(adminArtProductMaster,HttpStatus.OK);
    }


    @GetMapping("/getAdminArtProductsByStatus/{status}")
    private ResponseEntity getAdminArtProductsByStatus(@PathVariable("status") String status)
    {
        List<AdminArtProductMaster> adminArtProductMasterList=new ArrayList<>();
        adminArtProductMasterList = adminArtProductService.getAdminArtProductsByStatus(status);
        return new ResponseEntity(adminArtProductMasterList, HttpStatus.OK);
    }

    @GetMapping("/getAdminArtProductsBySubCatagoryIdWise/{productSubCategoryId}")
    public  ResponseEntity getAdminArtProductsBySubCatagoryIdWise(@PathVariable("productSubCategoryId")String productSubCategoryId)
    {
        List<AdminArtProductMaster> adminArtProductMasterList=new ArrayList<>();
        adminArtProductMasterList=adminArtProductService.getAdminArtProductsBySubCatagoryIdWise(productSubCategoryId);
        return new ResponseEntity(adminArtProductMasterList,HttpStatus.OK);
    }


    @GetMapping(value = "/{adminArtProductId}")
    private ResponseEntity getAdminArtProductMaster(@PathVariable("adminArtProductId") String adminArtProductId)
    {
        System.out.println("  adminArtProductId "+adminArtProductId);
        AdminArtProductMaster adminArtProductMaster=new AdminArtProductMaster();
        adminArtProductMaster=adminArtProductService.getAdminArtProductMaster(adminArtProductId);
        return new ResponseEntity(adminArtProductMaster,HttpStatus.OK);
    }


    @PostMapping("/AdminArtProductFilter/{type}/{text}")
    public ResponseEntity AdminArtProductFilter(@RequestBody ArtMasterFilterReqDto artMasterFilterReqDto, @PathVariable String type, @PathVariable String text) {
        System.out.println(" artMasterFilterReqDto  ===="+artMasterFilterReqDto.toString());
        List list = adminArtProductService.AdminArtProductFilter(artMasterFilterReqDto, type, text);
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @PostMapping("/getAllAdminArtProducts")
    public ResponseEntity getAllAdminArtProducts(@RequestBody ArtProductReq artProductReq)
    {
        List<AdminArtProductMaster> adminArtProductMasterList=new ArrayList<>();
        adminArtProductMasterList=adminArtProductService.getAllAdminArtProducts(artProductReq);
        return new ResponseEntity(adminArtProductMasterList, HttpStatus.OK);
    }




//    @PostMapping("/saveCartAdminArtProduct")
//    public ResponseEntity saveCartAdminArtProduct()
//    {
//
//
//    }




//
//    @GetMapping(value = "/getActiveArtProductMaster")
//    public ResponseEntity getActiveArtProductMaster()
//    {
//        List list = artProductMasterService.getActiveArtProductMaster();
//        return new ResponseEntity(list,HttpStatus.OK);
//
//    }
//    @GetMapping(value = "/getArtProductIdData/{artProductId}")
//    public ResponseEntity getArtProductIdData(@PathVariable String artProductId)
//    {
//        ArtProductMaster artProductMaster = artProductMasterService.getArtProductIdData(artProductId);
//        return new ResponseEntity(artProductMaster,HttpStatus.OK);
//
//    }
//
//    @GetMapping(value = "/getAllArtProductMaster")
//    public ResponseEntity getAllArtProductMaster()
//    {
//        List list = artProductMasterService.getAllArtProductMaster();
//        return new ResponseEntity(list,HttpStatus.OK);
//    }



}

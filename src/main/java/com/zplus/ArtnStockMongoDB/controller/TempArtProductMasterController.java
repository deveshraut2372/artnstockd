package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.ArtProductReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.CheckArtProductReq;
import com.zplus.ArtnStockMongoDB.dto.req.TempArtProductReq;
import com.zplus.ArtnStockMongoDB.dto.res.*;
import com.zplus.ArtnStockMongoDB.model.ArtProductMaster;
import com.zplus.ArtnStockMongoDB.model.TempArtProductMaster;
import com.zplus.ArtnStockMongoDB.service.TempArtProductService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/TempArt_Product_Master")
@CrossOrigin(origins = "*")
public class TempArtProductMasterController {

    @Autowired
    private TempArtProductService tempArtProductService;


    @PostMapping("/create")
    public ResponseEntity createTempArtProductMaster(@RequestBody TempArtProductReq tempArtProductReq) {
        Boolean flag = tempArtProductService.createTempArtProductMaster(tempArtProductReq);
        return new ResponseEntity(flag, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity updateTempArtProductMaster(@RequestBody TempArtProductReq tempArtProductReq) {
        Boolean flag = tempArtProductService.updateTempArtProductMaster(tempArtProductReq);
        return new ResponseEntity(flag, HttpStatus.OK);
    }


    @GetMapping(value = "/getActiveTempArtProductMaster")
    public ResponseEntity getActiveTempArtProductMaster()
    {
        List list = tempArtProductService.getActiveTempArtProductMaster();
        return new ResponseEntity(list,HttpStatus.OK);
    }


    @GetMapping(value = "/getTempArtProductIdData/{tempArtProductId}")
    public ResponseEntity getTempArtProductIdData(@PathVariable String tempArtProductId)
    {
        TempArtProductMaster tempArtProductMaster = tempArtProductService.getTempArtProductIdData(tempArtProductId);
        return new ResponseEntity(tempArtProductMaster,HttpStatus.OK);
    }

    @GetMapping(value = "/getAllTempArtProductIdByUserIdData/{userId}")
    public ResponseEntity getAllTempArtProductIdByUserIdData(@PathVariable String userId)
    {
        List list = tempArtProductService.getAllTempArtProductIdByUserIdData(userId);
        return new ResponseEntity(list,HttpStatus.OK);
    }

    @GetMapping(value = "/getAllTempArtProductMaster")
    public ResponseEntity getAllTempArtProductMaster()
    {
        List list = tempArtProductService.getAllTempArtProductMaster();
        return new ResponseEntity(list,HttpStatus.OK);
    }

    @PostMapping(value = "/checkArtProductIsCreate")
    public ResponseEntity checkArtProductIsCreate(@RequestBody CheckArtProductReq checkArtProductReq)
    {
        Boolean flag=tempArtProductService.checkArtProductIsCreate(checkArtProductReq);
        System.out.println("  flag "+flag);
        if(flag) {
            return new ResponseEntity(flag, HttpStatus.OK);
        }
        else {
            return new ResponseEntity(flag,HttpStatus.OK);
        }
    }


    @PostMapping(value = "/getArtProductIsCreate")
    public ResponseEntity getArtProductIsCreate(@RequestBody CheckArtProductReq checkArtProductReq)
    {
        TempArtProductRes tempArtProductRes=tempArtProductService.getArtProductIsCreate(checkArtProductReq);
            return new ResponseEntity(tempArtProductRes, HttpStatus.OK);
    }


    @DeleteMapping(value = "/DeleteTempArtProductIdData/{tempArtProductId}")
    public ResponseEntity DeleteTempArtProductIdData(@PathVariable String tempArtProductId)
    {
        Boolean flag  = tempArtProductService.DeleteTempArtProductIdData(tempArtProductId);
        if(flag) {
            return new ResponseEntity(flag, HttpStatus.OK);
        }
        else {
            return new ResponseEntity(flag,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getTempArtProductByProductId/{productId}/{productSubCategoryId}/{userId}")
    public ResponseEntity getTempProductByProductId(@PathVariable("productId") String productId,@PathVariable("productSubCategoryId") String productSubCategoryId,@PathVariable("userId") String userId)
    {
        List<TempArtProductMaster> tempArtProductMasterList=new ArrayList<>();
        tempArtProductMasterList=tempArtProductService.getTempProductByProductId(productId,productSubCategoryId,userId);
        return new ResponseEntity(tempArtProductMasterList,HttpStatus.OK);
    }

    @GetMapping("/getTempArtProductByProductIdAndSubCategory/{productSubSubCategoryId}/{userId}")
    public ResponseEntity getTempArtProductByProductIdAndSubCategory(@PathVariable("productSubSubCategoryId") String productSubSubCategoryId,@PathVariable("userId") String userId)
    {
        TempArtProductMasterRes tempArtProductMasterRes=new TempArtProductMasterRes();
        tempArtProductMasterRes=tempArtProductService.getTempArtProductByProductIdAndSubCategory(productSubSubCategoryId,userId);
        return new ResponseEntity(tempArtProductMasterRes,HttpStatus.OK);
    }


    @GetMapping("/getTempArtProductByProductIdAndSubCategoryExitOrNot/{productSubSubCategoryId}/{userId}")
    public ResponseEntity getTempArtProductByProductIdAndSubCategoryExitOrNot(@PathVariable("productSubSubCategoryId") String productSubSubCategoryId,@PathVariable("userId") String userId)
    {
      Boolean flag;
      flag=tempArtProductService.getTempArtProductByProductIdAndSubCategoryExitOrNot(productSubSubCategoryId,userId);

        MainResDto mainResDto=new MainResDto();
        mainResDto.setFlag(flag);
        mainResDto.setResponseCode(200);

        if(flag)
        {
            mainResDto.setMessage("Already Exit TempArtProduct ");
        }else {
            mainResDto.setMessage("TempArtProduct Does Not Exit ");
        }

        return new ResponseEntity(mainResDto,HttpStatus.OK);
    }

    // show only unique data of color in selected block
    @GetMapping("/getTempArtProductByArtDetailsId/{artDetailsId}/{userId}")
    public ResponseEntity getTempArtProductByArtDetailsId(@PathVariable("artDetailsId") String artDetailsId,@PathVariable("userId") String userId)
    {
        TempArtProductMasterResDto tempArtProductMasterResDto=new TempArtProductMasterResDto();
        tempArtProductMasterResDto =tempArtProductService.getTempArtProductByArtDetailsId(artDetailsId,userId);
        return new ResponseEntity(tempArtProductMasterResDto,HttpStatus.OK);
    }


    // show only unique data of color in selected block
    @GetMapping("/getTempArtProductByArtDetailsId/{artDetailsId}/{userId}/{productStyleId}")
    public ResponseEntity getTempArtProductByArtDetailsId(@PathVariable("artDetailsId") String artDetailsId,@PathVariable("userId") String userId,@PathVariable("productStyleId") String productStyleId)
    {
        TempArtProductMasterResDto tempArtProductMasterResDto=new TempArtProductMasterResDto();
        tempArtProductMasterResDto =tempArtProductService.getTempArtProductByArtDetailsIds(artDetailsId,userId,productStyleId);
        return new ResponseEntity(tempArtProductMasterResDto,HttpStatus.OK);
    }

    // pending
    @GetMapping("/getSeeAllProductsCount/{artDetailsId}/{userId}")
    public ResponseEntity getSeeAllProductsCount(@PathVariable("artDetailsId") String artDetailsId,@PathVariable("userId") String userId)
    {
        SeeAllProductRes seeAllProductRes=new SeeAllProductRes();
        seeAllProductRes=tempArtProductService.getSeeAllProductsCount(artDetailsId,userId);
        return new ResponseEntity(seeAllProductRes,HttpStatus.OK);
    }

}

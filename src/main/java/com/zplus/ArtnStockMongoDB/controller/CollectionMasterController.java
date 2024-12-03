package com.zplus.ArtnStockMongoDB.controller;

import com.amazonaws.services.pinpoint.model.MessageResponse;
import com.zplus.ArtnStockMongoDB.dto.req.*;
import com.zplus.ArtnStockMongoDB.dto.res.*;
import com.zplus.ArtnStockMongoDB.model.ArtMaster;
import com.zplus.ArtnStockMongoDB.model.CollectionMaster;
import com.zplus.ArtnStockMongoDB.model.FileManagerMaster;
import com.zplus.ArtnStockMongoDB.service.CollectionMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/collection_master")
public class CollectionMasterController {

    @Autowired
    private CollectionMasterService collectionMasterService;
    @PostMapping("/create")

    public ResponseEntity createCollectionMaster(@RequestBody CollectionMasterReqDto collectionMasterReqDto) {
        Boolean flag = collectionMasterService.createCollectionMaster(collectionMasterReqDto);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/update")
    public ResponseEntity updateCollectionMaster(@RequestBody CollectionMasterReqDto collectionMasterReqDto) {
        Boolean flag = collectionMasterService.updateCollectionMaster(collectionMasterReqDto);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateCollectionCoverImage")
    public ResponseEntity updateCollectionCoverImage(@RequestBody UpdateCollectionCoverImageReq updateCollectionCoverImageReq)
    {
        Boolean flag=collectionMasterService.updateCollectionCoverImage(updateCollectionCoverImageReq);
        if(flag)
        {
            return new ResponseEntity(flag,HttpStatus.OK);
        }else
        {
            return new ResponseEntity(flag,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity getAllCollectionMaster() {
        List list = collectionMasterService.getAllCollectionMaster();
        return new ResponseEntity(list, HttpStatus.CREATED);
    }


    @GetMapping(value = "/editCollectionMaster/{collectionId}")
    public ResponseEntity editCollectionMaster(@PathVariable String collectionId)
    {
        CollectionMaster collectionMaster = collectionMasterService.editCollectionMaster(collectionId);
        if(collectionMaster!=null)
        {
            return new ResponseEntity(collectionMaster, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(collectionMaster,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/getActiveCollectionMaster")
    public ResponseEntity getActiveCollectionMaster()
    {
        List list = collectionMasterService.getActiveCollectionMaster();
        return new ResponseEntity(list,HttpStatus.OK);
    }


    @GetMapping(value = "/getUserIdWiseCollectionMasterList/{userId}")
    public ResponseEntity getUserIdWiseCollectionMasterList(@PathVariable String userId)
    {
        List<CollectionMasterRes> list = collectionMasterService.getUserIdWiseCollectionMasterList(userId);
        return new ResponseEntity(list,HttpStatus.OK);
    }



    // NO Change
    @GetMapping(value = "/getCollectionIdWiseArtList/{collectionId}")
    public ResponseEntity getCollectionIdWiseArtList(@PathVariable String collectionId)
    {
        List<ArtMaster> list = collectionMasterService.getCollectionIdWiseArtList(collectionId);
        return new ResponseEntity(list,HttpStatus.OK);
    }


    // NO change
    @GetMapping(value = "/getUserIdWiseCollectionData/{userId}")
    public ResponseEntity getUserIdWiseCollectionData(@PathVariable String userId)
    {
        List<CollectionRes> list = collectionMasterService.getUserIdWiseCollectionData(userId);
        return new ResponseEntity(list,HttpStatus.OK);
    }



    // No Change Collcection Id wise Add single art and AdminartProduct
    @PostMapping(value = "/CollectionIdWiseAddArt")
        public ResponseEntity CollectionIdWiseAddArt(@RequestBody AddArtInCollectionRequestDto addArtInCollectionRequestDto)
    {
        Boolean flag = collectionMasterService.CollectionIdWiseAddArt(addArtInCollectionRequestDto);
        return new ResponseEntity(flag,HttpStatus.OK);
    }



    // NoChange  update collection Titile
    @PutMapping(value = "/updateIdWiseCollectionTitle")
    public ResponseEntity updateIdWiseCollectionTitle(@RequestBody UpdateCollectionTitleReqDto updateCollectionTitleReqDto)
    {
        Boolean flag = collectionMasterService.updateIdWiseCollectionTitle(updateCollectionTitleReqDto);
        return new ResponseEntity(flag,HttpStatus.OK);
    }


    // No Change NO used
    @PostMapping(value = "/addArtOrProductInCollection")
    public ResponseEntity addArtOrProductInCollection(@RequestBody AddArtOrProductCollectionRequestDto addArtOrProductCollectionRequestDto)
    {
        Boolean flag = collectionMasterService.addArtOrProductInCollection(addArtOrProductCollectionRequestDto);
        return new ResponseEntity(flag,HttpStatus.OK);
    }


    // Delete Collection by Id
    @DeleteMapping(value = "/deleteCollection/{collectionId}")
    public ResponseEntity deleteCollection(@PathVariable String collectionId)
    {
        Boolean flag = collectionMasterService.deleteCollection(collectionId);
        if(Boolean.TRUE.equals(flag))
        {
            return new ResponseEntity(flag, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(flag,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // No change No Used
    @PostMapping("/CollectionFilter")
    public ResponseEntity CollectionFilter(@RequestBody FilterReqDto filterReqDto) {
        List list = collectionMasterService.CollectionFilter(filterReqDto);
        return new ResponseEntity(list, HttpStatus.OK);
    }


    // No change but No Used add Multiple Art And AdminArtProducts
    @PostMapping(value = "/CollectionIdWiseAddArtAndAdminProduct")
    public ResponseEntity CollectionIdWiseAddArtAndAdminProduct(@RequestBody AddCollectionArtAndAdminProductRequestDto addCollectionArtAndAdminProductRequestDto)
    {
        Boolean flag = collectionMasterService.CollectionIdWiseAddArtAndAdminProduct(addCollectionArtAndAdminProductRequestDto);
        return new ResponseEntity(flag,HttpStatus.OK);
    }


    // No Change  used For Add Multiple Art And AdminArtProducts
    @PostMapping(value = "/CollectionIdWiseAddArtAndAdminProduct1")
    public ResponseEntity CollectionIdWiseAddArtAndAdminProduct1(@RequestBody AddCollArtAndAdminProductRequest addCollArtAndAdminProductRequest)
    {
        Boolean flag = collectionMasterService.CollectionIdWiseAddArtAndAdminProduct1(addCollArtAndAdminProductRequest);
        return new ResponseEntity(flag,HttpStatus.OK);
    }



    // No change Used For Sort The Collection Folder   yes want to change
    @PostMapping("/getCollectionsByUserIdAndSort")
    public ResponseEntity getCollectionsByUserIdAndSort(@RequestBody CollectionSortRequest collectionSortRequest)
    {
        List<CollectionMaster> collectionMasterList=new ArrayList<>();
        collectionMasterList=collectionMasterService.getCollectionsByUserIdAndSort(collectionSortRequest);
        return new ResponseEntity(collectionMasterList,HttpStatus.OK);
    }

    @PostMapping("/getCollectionsByUserIdAndSor")
    public ResponseEntity getCollectionsByUserIdAndSor(@RequestBody CollectionSortRequest collectionSortRequest)
    {
        List<CollectionMasterRes> collectionMasterList=new ArrayList<>();
        collectionMasterList=collectionMasterService.getCollectionsByUserIdAndSor(collectionSortRequest);
        return new ResponseEntity(collectionMasterList,HttpStatus.OK);
    }


    // No change
    @PostMapping(value = "/getCollectionByListSort")
    public ResponseEntity getCollectionByListSort(@RequestBody CollectionOrderReq collectionOrderReq)
    {
       List list=new ArrayList();
       list=collectionMasterService.getCollectionByListSort(collectionOrderReq);
        return new ResponseEntity(list,HttpStatus.OK);
    }

    @PostMapping(value = "/deleteCollectionIdWiseAddArtAndAdminProduct")
    public ResponseEntity deleteCollectionIdWiseAddArtAndAdminProduct(@RequestBody CollectionAddArtAndAdminProductRequestDto collectionAddArtAndAdminProductRequestDto)
    {
        Boolean flag=collectionMasterService.deleteCollectionIdWiseAddArtAndAdminProduct(collectionAddArtAndAdminProductRequestDto);
        if(flag)
        {
            return new ResponseEntity(flag,HttpStatus.OK);
        }else {
            return new ResponseEntity(flag,HttpStatus.BAD_REQUEST);
        }
    }



    // workning
    @PostMapping(value = "/getAllCollectionsArtAndAdmiAProducts")
    public ResponseEntity getAllCollectionsArtAndAdmiAProducts(@RequestBody CollectionSortReq collectionSortReq) {
        List list = new ArrayList();
        list = collectionMasterService.getAllCollectionsArtAndAdmiAProducts(collectionSortReq);
        return new ResponseEntity(list, HttpStatus.OK);
    }


    @PostMapping(value = "/getAllCollectionsArtAndAdminProducts")
    public ResponseEntity getAllCollectionsArtAndAdminProducts(@RequestBody CollectionSortReq collectionSortReq) {
        List list = new ArrayList();
        list = collectionMasterService.getAllCollectionsArtAndAdminProducts(collectionSortReq);
        return new ResponseEntity(list, HttpStatus.OK);
    }


    // used for folder sort
    @GetMapping("/getCollectionListByUserIdAndSort/{collectionId}")
    public ResponseEntity getCollectionListByUserIdAndSort(@PathVariable String collectionId )
    {
        CollectionMasterRes collectionMasterRes=new CollectionMasterRes();
        List list=new ArrayList();
        list=collectionMasterService.getCollectionListByUserIdAndSort(collectionId);
        System.out.println("  List size ="+list.size());
        return new ResponseEntity(list,HttpStatus.OK);
    }


    // get Collecton collectionArtMaster list and CollectionAdminArtMaster List by Using sort conditions
   @PostMapping (value = "/getCollectionResponseByCollectionIdWise")
    public ResponseEntity getCollectionResponseByCollectionIdWise(CollectionOrderReq collectionOrderReq)
   {
        List list=new ArrayList();
        list=collectionMasterService.getCollectionResponseByCollectionIdWise(collectionOrderReq);
        return new ResponseEntity(list,HttpStatus.OK);
   }

   @GetMapping("/getAllCollectionCountRes/{userId}")
    public  ResponseEntity c(@PathVariable("userId") String userId)
   {
       CollectionCountRes collectionCountRes=new CollectionCountRes();
       collectionCountRes=collectionMasterService.CollectionCountRes(userId);
       return new ResponseEntity(collectionCountRes,HttpStatus.OK);
   }






}

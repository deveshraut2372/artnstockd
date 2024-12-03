package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.WishListAddReq;
import com.zplus.ArtnStockMongoDB.dto.req.WishListReqDto;
import com.zplus.ArtnStockMongoDB.dto.res.AddWishListResDto;
import com.zplus.ArtnStockMongoDB.model.WishListMaster;
import com.zplus.ArtnStockMongoDB.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/wishlist_master")
public class WishListController {

    @Autowired
    private WishListService wishListService;

    @PostMapping("/save")
    private ResponseEntity saveWishList(@RequestBody WishListReqDto wishListReqDto) {

        AddWishListResDto addWishListResDto = wishListService.saveWishList(wishListReqDto);

        if (addWishListResDto != null) {
            return new ResponseEntity(addWishListResDto, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(addWishListResDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/add")
    private ResponseEntity addWishList(@RequestBody WishListAddReq wishListAddReq) {

        AddWishListResDto addWishListResDto = wishListService.addWishList(wishListAddReq);

        if (addWishListResDto != null) {
            return new ResponseEntity(addWishListResDto, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(addWishListResDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping(value = "delete/{wishListId}")
    private ResponseEntity deleteListData(@PathVariable String wishListId){

        Boolean flag = wishListService.deleteListData(wishListId);

        if(flag){
            return new ResponseEntity(flag, HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity(flag,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "getByUserId/{userId}")
    private ResponseEntity wishListCount(@PathVariable String userId)
    {
        Long flag = wishListService. wishListCount(userId);

            return new ResponseEntity(flag, HttpStatus.OK);
    }
    @GetMapping(value = "/getUserIdWiseWishListCount /{userId}")
    public ResponseEntity getUserIdWiseWishListCount(@PathVariable String userId)
    {
        Long count = wishListService.getUserIdWiseWishListCount(userId);
        if(count!=0l)
        {
            return new ResponseEntity(count,HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(count,HttpStatus.OK);
        }
    }
    @GetMapping(value = "getByUserIdList/{userId}")
    private ResponseEntity getByUserIdList(@PathVariable String userId)
    {
        List<WishListMaster> list = wishListService.getByUserIdList(userId);

        return new ResponseEntity(list, HttpStatus.OK);
    }
}

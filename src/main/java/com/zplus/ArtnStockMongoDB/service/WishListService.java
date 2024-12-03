package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.WishListAddReq;
import com.zplus.ArtnStockMongoDB.dto.req.WishListReqDto;
import com.zplus.ArtnStockMongoDB.dto.res.AddWishListResDto;
import com.zplus.ArtnStockMongoDB.model.WishListMaster;

import java.util.List;

public interface WishListService {
    AddWishListResDto saveWishList(WishListReqDto wishListReqDto);

    Boolean deleteListData(String wishListId);

    Long getUserIdWiseWishListCount(String userId);

    Long wishListCount(String id);

    List<WishListMaster> getByUserIdList(String userId);

    AddWishListResDto addWishList(WishListAddReq wishListAddReq);
}

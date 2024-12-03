package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.*;
import com.zplus.ArtnStockMongoDB.dto.req.WishListAddReq;
import com.zplus.ArtnStockMongoDB.dto.req.WishListReqDto;
import com.zplus.ArtnStockMongoDB.dto.res.AddWishListResDto;
import com.zplus.ArtnStockMongoDB.model.*;
import com.zplus.ArtnStockMongoDB.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class WishListServiceImpl implements WishListService {

    @Autowired
    private WishListDao wishListDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AdminArtProductMasterDao adminArtProductMasterDao;
    @Autowired
    private ArtMasterDao artMasterDao;
    @Autowired
    private ArtProductMasterDao artProductMasterDao;



    @Override
    public AddWishListResDto saveWishList(WishListReqDto wishListReqDto) {
        AddWishListResDto addWishListResDto = new AddWishListResDto();
        WishListMaster wishListMaster = new WishListMaster();
        UserMaster userMaster = new UserMaster();
        String userId = wishListReqDto.getId();
        if (userId != null) {
            userMaster.setUserId(userId);
            wishListMaster.setUserMaster(userMaster);
        } else {
            addWishListResDto.setFlag(false);
            return addWishListResDto;
        }
        wishListMaster.setWishListDate(new Date());
        wishListMaster.setWishListStatus("Active");

        String artProductId = wishListReqDto.getArtProductId();
        if (artProductId != null) {
            ArtProductMaster artProductMaster = new ArtProductMaster();
            artProductMaster.setArtProductId(artProductId);
            wishListMaster.setArtProductMaster(artProductMaster);
        }
        String artId = wishListReqDto.getArtId();
        if (artId != null) {
            ArtMaster artMaster = new ArtMaster();
            artMaster.setArtId(artId);
            wishListMaster.setArtMaster(artMaster);
        }
        String adminArtProductId=wishListReqDto.getAdminArtProductId();
        if(adminArtProductId!=null)
        {
            AdminArtProductMaster adminArtProductMaster= new AdminArtProductMaster();
            adminArtProductMaster.setAdminArtProductId(adminArtProductId);
            wishListMaster.setAdminArtProductMaster(adminArtProductMaster);
        }

        try {
            WishListMaster wishList1 = wishListDao.save(wishListMaster);
            addWishListResDto.setFlag(true);
            addWishListResDto.setWishListId(wishList1.getWishListId());
        } catch (Exception e) {
            e.printStackTrace();
            addWishListResDto.setFlag(false);
        }
        return addWishListResDto;
    }

    @Override
    public Boolean deleteListData(String wishListId) {
        try {
            wishListDao.deleteById(wishListId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Long wishListCount(String userId) {
//        List<WishListMaster> wishListMasterList = wishListDao.findAll();
//        final Query query = new Query();
//        final List<Criteria> criteria = new ArrayList<>();
//        for (WishListMaster wishListMaster : wishListMasterList) {
//            if (wishListMaster.getUserMaster() != null) {
//                criteria.add(Criteria.where("userMaster.userId").is(wishListMaster.getUserMaster().getUserId()));
//            }
//        }
//        if (!criteria.isEmpty())
//            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
//        return mongoTemplate.count(query, WishListMaster.class);
        return wishListDao.countByUserMaster_UserId(userId);

    }

    @Override
    public List<WishListMaster> getByUserIdList(String userId) {
        List<WishListMaster> list=wishListDao.findByUserMaster_UserId(userId);
        return list;
    }

    @Override
    public AddWishListResDto addWishList(WishListAddReq wishListAddReq) {
        AddWishListResDto addWishListResDto=new AddWishListResDto();
        WishListMaster wishListMaster=new WishListMaster();

        UserMaster userMaster=new UserMaster();
        userMaster=userDao.findByUserId(wishListAddReq.getId()).get();

        try {
            switch (wishListAddReq.getType()) {
                case "cartProduct":
                    ArtProductMaster artProductMaster=new ArtProductMaster();
                    artProductMaster=artProductMasterDao.getArtProductMaster(wishListAddReq.getDeleteId());
                    wishListMaster.setArtProductMaster(artProductMaster);
                    break;
                case "cartArtFrame":
                    ArtMaster artMaster=new ArtMaster();
                   artMaster=artMasterDao.getArtId(wishListAddReq.getDeleteId());
                    wishListMaster.setArtMaster(artMaster);
                    break;
                case "cartAdminArtProduct":
                    AdminArtProductMaster adminArtProductMaster=new AdminArtProductMaster();
                    adminArtProductMaster=adminArtProductMasterDao.getAdminArtProductMaster(wishListAddReq.getDeleteId());
                    wishListMaster.setAdminArtProductMaster(adminArtProductMaster);
                    break;
            }
            wishListMaster.setWishListDate(new Date());
            wishListMaster.setWishListStatus("Active");
           WishListMaster wishListMaster1= wishListDao.save(wishListMaster);

            addWishListResDto.setFlag(true);
            addWishListResDto.setWishListId(wishListMaster1.getWishListId());
        return addWishListResDto;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            addWishListResDto.setFlag(false);
            addWishListResDto.setWishListId(null);
            return addWishListResDto;
        }
    }

    @Override
    public Long getUserIdWiseWishListCount(String userId) {
        UserMaster userMaster=new UserMaster();
        userMaster.setUserId(userId);
        return wishListDao.countByUserMaster(userMaster);
    }
}

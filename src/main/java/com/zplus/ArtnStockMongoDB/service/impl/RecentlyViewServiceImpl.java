package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.RecentlyViewDao;
import com.zplus.ArtnStockMongoDB.dto.req.RecentlyViewRequest;
import com.zplus.ArtnStockMongoDB.model.*;
import com.zplus.ArtnStockMongoDB.service.RecentlyViewService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecentlyViewServiceImpl implements RecentlyViewService {

    @Autowired
    private RecentlyViewDao recentlyViewDao;

    @Autowired
    private MongoTemplate mongoTemplate;


//    @Override
//    public Boolean createRecentlyViewMaster(RecentlyViewRequest recentlyViewRequest) {
//        Boolean flag = false;
//        RecentlyViewMaster recentlyViewMaster1 = recentlyViewDao.findByUserMasterUserIdAndArtMasterArtId(recentlyViewRequest.getUserId(), recentlyViewRequest.getArtId());
//
//        if (recentlyViewMaster1 != null) {
//            recentlyViewMaster1.setDate(new Date());
//            recentlyViewDao.save(recentlyViewMaster1);
//        } else {
//            RecentlyViewMaster recentlyViewMaster = new RecentlyViewMaster();
//            recentlyViewMaster.setDate(new Date());
//            BeanUtils.copyProperties(recentlyViewRequest, recentlyViewMaster);
//
//            UserMaster userMaster = new UserMaster();
//            userMaster.setUserId(recentlyViewRequest.getUserId());
//            recentlyViewMaster.setUserMaster(userMaster);
//
//            ArtMaster artMaster = new ArtMaster();
//            artMaster.setArtId(recentlyViewRequest.getArtId());
//            recentlyViewMaster.setArtMaster(artMaster);
//            try {
//                RecentlyViewMaster master = recentlyViewDao.save(recentlyViewMaster);
//                System.out.println("master" + master.getDate().getTime());
//
//                flag = true;
//            } catch (Exception e) {
//                e.printStackTrace();
//                flag = false;
//            }
//        }
//        return flag;
//    }

  @Override
  public Boolean createRecentlyViewMaster(RecentlyViewRequest recentlyViewRequest) {
    Boolean flag = false;

      System.out.println("recentlyViewRequest ==="+recentlyViewRequest.toString());
    RecentlyViewMaster recentlyViewMaster1 = recentlyViewDao.findByUserMaster_UserIdAndArtMaster_ArtId(recentlyViewRequest.getUserId(), recentlyViewRequest.getArtId());

    if (recentlyViewMaster1 != null) {
      recentlyViewMaster1.setDate(new Date());
      recentlyViewDao.save(recentlyViewMaster1);
      flag = true;
    } else {
      RecentlyViewMaster recentlyViewMaster = new RecentlyViewMaster();
      recentlyViewMaster.setDate(new Date());
      BeanUtils.copyProperties(recentlyViewRequest, recentlyViewMaster);

      UserMaster userMaster = new UserMaster();
      userMaster.setUserId(recentlyViewRequest.getUserId());
      recentlyViewMaster.setUserMaster(userMaster);

      ArtMaster artMaster = new ArtMaster();
      artMaster.setArtId(recentlyViewRequest.getArtId());
      recentlyViewMaster.setArtMaster(artMaster);

      try {
        recentlyViewDao.save(recentlyViewMaster);
        flag = true;
      } catch (Exception e) {
        e.printStackTrace();
        flag = false;
      }
    }

    return flag;
  }


  @Override
    public List<RecentlyViewMaster> getAllRecentlyViewMaster() {
        List list = recentlyViewDao.findAll();
        System.out.println("list" + list.size());
        return list;
    }

    @Override
    public List getUserIdWiseRecentlyViewMaster(String userId) {
        Criteria criteria = Criteria.where("userMaster.userId").is(userId);
        Query query = new Query(criteria).with(Sort.by(Sort.Direction.DESC, "date"));
        List<RecentlyViewMaster> list = mongoTemplate.find(query, RecentlyViewMaster.class).stream().limit(10).collect(Collectors.toList());
        return list;
    }

    @Override
    public RecentlyViewMaster getByRecentlyViewId(String recentlyViewId) {
        RecentlyViewMaster recentlyViewMaster = new RecentlyViewMaster();
        try {
            Optional<RecentlyViewMaster> recentlyViewMaster1 = recentlyViewDao.findById(recentlyViewId);
            recentlyViewMaster1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, recentlyViewMaster));
            return recentlyViewMaster;
        } catch (Exception e) {
            e.printStackTrace();
            return recentlyViewMaster;
        }
    }

    @Override
    public Boolean deleteByUserId(String userId) {
        try {
            recentlyViewDao.deleteByUserMaster_UserId(userId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }





        
    }
}

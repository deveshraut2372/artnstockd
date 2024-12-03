package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.RecentlySearchMasterDao;
import com.zplus.ArtnStockMongoDB.dto.req.RecentlySearchRequest;
import com.zplus.ArtnStockMongoDB.model.ArtMaster;
import com.zplus.ArtnStockMongoDB.model.RecentlySearchMaster;
import com.zplus.ArtnStockMongoDB.model.RecentlyViewMaster;
import com.zplus.ArtnStockMongoDB.model.UserMaster;
import com.zplus.ArtnStockMongoDB.service.RecentlySearchMasterService;
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
public class RecentlySearchMasterServiceImpl implements RecentlySearchMasterService {

    @Autowired
    private RecentlySearchMasterDao recentlySearchMasterDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Boolean createRecentlySearchMaster(RecentlySearchRequest recentlySearchRequest) {
        try {
            Optional<RecentlySearchMaster> optionalRecentlySearchMaster =
                    recentlySearchMasterDao.findByUserMasterUserIdAndText(recentlySearchRequest.getUserId(), recentlySearchRequest.getText());

            if (optionalRecentlySearchMaster.isPresent()) {
                RecentlySearchMaster existingSearchMaster = optionalRecentlySearchMaster.get();
                existingSearchMaster.setDate(new Date());
                recentlySearchMasterDao.save(existingSearchMaster);
            } else {
                RecentlySearchMaster recentlySearchMaster = new RecentlySearchMaster();
                recentlySearchMaster.setDate(new Date());
                BeanUtils.copyProperties(recentlySearchRequest, recentlySearchMaster);

                UserMaster userMaster = new UserMaster();
                userMaster.setUserId(recentlySearchRequest.getUserId());
                recentlySearchMaster.setUserMaster(userMaster);

                recentlySearchMasterDao.save(recentlySearchMaster);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List getAllRecentlySearchMaster() {
        List list = recentlySearchMasterDao.findAll();
        System.out.println("list" + list.size());
        return list;
    }

    @Override
    public List getUserIdWiseRecentlyKeywordSearch(String userId) {
        Criteria criteria = Criteria.where("userMaster.userId").is(userId);
        Query query = new Query(criteria).with(Sort.by(Sort.Direction.DESC, "recentlySearchId"));
        List<RecentlySearchMaster> list = mongoTemplate.find(query, RecentlySearchMaster.class).stream().limit(10).collect(Collectors.toList());
        return list;
    }

    @Override
    public RecentlySearchMaster getByRecentlySearchId(String recentlySearchId) {
        RecentlySearchMaster recentlySearchMaster = new RecentlySearchMaster();
        try {
            Optional<RecentlySearchMaster> recentlySearchMaster1 = recentlySearchMasterDao.findById(recentlySearchId);
            recentlySearchMaster1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, recentlySearchMaster));
            return recentlySearchMaster;
        } catch (Exception e) {
            e.printStackTrace();
            return recentlySearchMaster;
        }
    }

    @Override
    public Boolean deleteRecentlySearch(String userId) {
        Boolean flag = false;
        try {
            recentlySearchMasterDao.deleteByUserMasterUserId(userId);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
}

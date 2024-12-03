package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.DynamicHomepageContentDao;
import com.zplus.ArtnStockMongoDB.dto.req.DynamicHomepageContentReqDto;
import com.zplus.ArtnStockMongoDB.model.DynamicHomepageContentMaster;
import com.zplus.ArtnStockMongoDB.service.DynamicHomepageContentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DynamicHomepageContentServiceImpl implements DynamicHomepageContentService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private DynamicHomepageContentDao dynamicHomepageContentDao;

    @Override
    public Boolean createDynamicHomepageContentMaster(DynamicHomepageContentReqDto dynamicHomepageContentReqDto) {
        Boolean flag = false;

        DynamicHomepageContentMaster dynamicHomepageContentMaster = dynamicHomepageContentDao.getType(dynamicHomepageContentReqDto.getType());
        if (dynamicHomepageContentMaster != null) {
            if (dynamicHomepageContentReqDto.getType().equals(dynamicHomepageContentMaster.getType())) {

                final Query query = new Query().addCriteria(Criteria.where("gridTitle").is(dynamicHomepageContentMaster.getGridTitle())).addCriteria(Criteria.where("gridDesc").is(dynamicHomepageContentMaster.getGridDesc()))
                        .addCriteria(Criteria.where("popularProductTitle").is(dynamicHomepageContentMaster.getPopularProductTitle())).addCriteria(Criteria.where("popularProductDesc").is(dynamicHomepageContentMaster.getPopularProductDesc()))
                        .addCriteria(Criteria.where("smallLogo").is(dynamicHomepageContentMaster.getSmallLogo())).addCriteria(Criteria.where("bigLogo").is(dynamicHomepageContentMaster.getBigLogo()))
                        .addCriteria(Criteria.where("comboTextImg").is(dynamicHomepageContentMaster.getComboTextImg())).addCriteria(Criteria.where("signInLeftImg").is(dynamicHomepageContentMaster.getSignInLeftImg()))
                        .addCriteria(Criteria.where("signInBackground").is(dynamicHomepageContentMaster.getSignInBackground())).addCriteria(Criteria.where("signInLeftMain").is(dynamicHomepageContentMaster.getSignInLeftMain()))
                        .addCriteria(Criteria.where("signInLeftDesc").is(dynamicHomepageContentMaster.getSignInLeftDesc())).addCriteria(Criteria.where("signInLeftBtn").is(dynamicHomepageContentMaster.getSignInLeftBtn()))
                        .addCriteria(Criteria.where("signInBottomText").is(dynamicHomepageContentMaster.getSignInBottomText())).addCriteria(Criteria.where("type").is(dynamicHomepageContentMaster.getType()));

                Update update = new Update().set("gridTitle", dynamicHomepageContentReqDto.getGridTitle()).set("gridDesc", dynamicHomepageContentReqDto.getGridDesc()).set("popularProductTitle", dynamicHomepageContentReqDto.getPopularProductTitle())
                        .set("popularProductDesc", dynamicHomepageContentReqDto.getPopularProductDesc()).set("smallLogo", dynamicHomepageContentReqDto.getSmallLogo()).set("bigLogo", dynamicHomepageContentReqDto.getBigLogo()).set("comboTextImg", dynamicHomepageContentReqDto.getComboTextImg())
                        .set("signInLeftImg", dynamicHomepageContentReqDto.getSignInLeftImg()).set("signInBackground", dynamicHomepageContentReqDto.getSignInBackground()).set("signInLeftMain", dynamicHomepageContentReqDto.getSignInLeftMain()).set("signInLeftDesc", dynamicHomepageContentReqDto.getSignInLeftDesc())
                        .set("signInLeftBtn", dynamicHomepageContentReqDto.getSignInLeftBtn()).set("signInBottomText", dynamicHomepageContentReqDto.getSignInBottomText()).set("type", dynamicHomepageContentReqDto.getType());

                FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(true);
                mongoTemplate.findAndModify(query, update, options, DynamicHomepageContentMaster.class);
            }
        } else {
            DynamicHomepageContentMaster dynamicHomepageContentMaster1 = new DynamicHomepageContentMaster();
            BeanUtils.copyProperties(dynamicHomepageContentReqDto, dynamicHomepageContentMaster1);
            DynamicHomepageContentMaster dynamicHomepageContentMaster2 = dynamicHomepageContentDao.save(dynamicHomepageContentMaster1);
        }
        return true;
    }

    @Override
    public Boolean updateDynamicHomepageContentMaster(DynamicHomepageContentReqDto dynamicHomepageContentReqDto) {
        DynamicHomepageContentMaster dynamicHomepageContentMaster = new DynamicHomepageContentMaster();
        dynamicHomepageContentMaster.setDynamicHomepageContentId(dynamicHomepageContentReqDto.getDynamicHomepageContentId());
        BeanUtils.copyProperties(dynamicHomepageContentReqDto, dynamicHomepageContentMaster);
        try {
            dynamicHomepageContentDao.save(dynamicHomepageContentMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List getAllDynamicHomepageContentMaster() {
        return dynamicHomepageContentDao.findAll();
    }

    @Override
    public DynamicHomepageContentMaster editDynamicHomepageContentMaster(String dynamicHomepageContentId) {
        DynamicHomepageContentMaster dynamicHomepageContentMaster = new DynamicHomepageContentMaster();
        try {
            Optional<DynamicHomepageContentMaster> dynamicHomepageContentMaster1 = dynamicHomepageContentDao.findById(dynamicHomepageContentId);
            dynamicHomepageContentMaster1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, dynamicHomepageContentMaster));
            return dynamicHomepageContentMaster;
        } catch (Exception e) {
            e.printStackTrace();
            return dynamicHomepageContentMaster;
        }
    }

    @Override
    public DynamicHomepageContentMaster getTypeWiseList(String type) {
        DynamicHomepageContentMaster dynamicHomepageContentMaster = dynamicHomepageContentDao.getType(type);
        return dynamicHomepageContentMaster;
    }
}

package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.FrequentlyAskedQuestionsDao;
import com.zplus.ArtnStockMongoDB.dto.req.FrequentlyAskedQuestionsReqDto;
import com.zplus.ArtnStockMongoDB.model.FrequentlyAskedQuestionsMaster;
import com.zplus.ArtnStockMongoDB.service.FrequentlyAskedQuestionsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FrequentlyAskedQuestionsServicesImpl implements FrequentlyAskedQuestionsService {

    @Autowired
    private FrequentlyAskedQuestionsDao frequentlyAskedQuestionsDao;

    @Override
    public Boolean saveFrequentlyAskedQuestions(FrequentlyAskedQuestionsReqDto frequentlyAskedQuestionsReqDto) {
        FrequentlyAskedQuestionsMaster frequentlyAskedQuestionsMaster = new FrequentlyAskedQuestionsMaster();

        BeanUtils.copyProperties(frequentlyAskedQuestionsReqDto, frequentlyAskedQuestionsMaster);
        frequentlyAskedQuestionsMaster.setStatus("InActive");
        try {
            frequentlyAskedQuestionsDao.save(frequentlyAskedQuestionsMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateFrequentlyAskedQuestions(FrequentlyAskedQuestionsReqDto frequentlyAskedQuestionsReqDto) {
        FrequentlyAskedQuestionsMaster frequentlyAskedQuestionsMaster = new FrequentlyAskedQuestionsMaster();

        frequentlyAskedQuestionsMaster.setFaqId(frequentlyAskedQuestionsReqDto.getFaqId());
        BeanUtils.copyProperties(frequentlyAskedQuestionsReqDto, frequentlyAskedQuestionsMaster);
        try {
            frequentlyAskedQuestionsDao.save(frequentlyAskedQuestionsMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<FrequentlyAskedQuestionsMaster> getAllListFrequentlyAskedQuestions() {
        return  frequentlyAskedQuestionsDao.findAll();
    }

    @Override
    public FrequentlyAskedQuestionsMaster getByFaqId(String faqId) {
        FrequentlyAskedQuestionsMaster frequentlyAskedQuestionsMaster = new FrequentlyAskedQuestionsMaster();
        try {
            Optional<FrequentlyAskedQuestionsMaster> frequentlyAskedQuestionsMaster1 = frequentlyAskedQuestionsDao.findById(faqId);
            frequentlyAskedQuestionsMaster1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, frequentlyAskedQuestionsMaster));
            return frequentlyAskedQuestionsMaster;
        } catch (Exception e) {
            e.printStackTrace();
            return frequentlyAskedQuestionsMaster;
        }
    }

    @Override
    public List getActiveListFrequentlyAskedQuestions() {
        List list = frequentlyAskedQuestionsDao.findAllByStatus("Active");
        return list;
    }

    @Override
    public List<FrequentlyAskedQuestionsMaster> getTypeWiseFaq(String type) {
        List<FrequentlyAskedQuestionsMaster> list=frequentlyAskedQuestionsDao.findByType(type);
        return list;
    }

    @Override
    public Boolean DeleteByFaqId(String faqId) {

        try
        {
            frequentlyAskedQuestionsDao.deleteById(faqId);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }


}

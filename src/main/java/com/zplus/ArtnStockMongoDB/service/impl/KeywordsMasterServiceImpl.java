package com.zplus.ArtnStockMongoDB.service.impl;

import com.google.auto.value.AutoAnnotation;
import com.zplus.ArtnStockMongoDB.dao.KeywordsMasterDao;
import com.zplus.ArtnStockMongoDB.dto.req.KeywordsMasterReq;
import com.zplus.ArtnStockMongoDB.model.KeywordsMaster;
import com.zplus.ArtnStockMongoDB.service.KeywordsMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class KeywordsMasterServiceImpl implements KeywordsMasterService {

    @Autowired
    private KeywordsMasterDao keywordsMasterDao;


    @Override
    public Boolean addKeywords(KeywordsMasterReq keywordsMasterReq) {

        KeywordsMaster keywordsMaster=new KeywordsMaster();
        Set<String> keywordsMasterSets=new HashSet<>();
        List<KeywordsMaster> list= keywordsMasterDao.findAll();

        if(!list.isEmpty())
        {
            keywordsMaster=list.get(0);
            keywordsMasterSets=keywordsMaster.getKeywords();
        }

        keywordsMasterSets.addAll(keywordsMasterReq.getKeywords());
        KeywordsMaster keywordsMaster1=new KeywordsMaster();
        keywordsMaster1.setKeywords(keywordsMasterSets);
        keywordsMaster1.setKeywordId(keywordsMaster.getKeywordId());

        try
        {
         keywordsMasterDao.save(keywordsMaster);
            return true;
        }catch (Exception e)
        {
            return false;
        }
    }

    @Override
    public Set getKeywords() {
        int n=50;
        Set<String> keywords=new HashSet<>();
        KeywordsMaster keywordsMaster=new KeywordsMaster();
        List<KeywordsMaster> keywordlist= keywordsMasterDao.findAll();
        keywordsMaster=keywordlist.get(0);

        System.out.println("  keyword Master ="+keywordsMaster.toString());

        List keywordslist= keywordsMaster.getKeywords().stream().collect(Collectors.toList());

        System.out.println(" keywordsSet == "+keywordslist.toString());

        for(int i=0;i<=n;i++)
        {
            Random random=new Random();
            int j= random.nextInt(keywordslist.size());
            keywords.add((String) keywordslist.get(j));
        }

//        keywords=keywordsMaster.getKeywords();

        return keywords;
    }

    @Override
    public Set<String> getKeywordsByNumber(Integer number) {
        int n=number;
        Set<String> keywords=new HashSet<>();
        KeywordsMaster keywordsMaster=new KeywordsMaster();
        List<KeywordsMaster> keywordlist= keywordsMasterDao.findAll();
        keywordsMaster=keywordlist.get(0);

        List keywordslist= keywordsMaster.getKeywords().stream().collect(Collectors.toList());

        System.out.println(" keywordsSet == "+keywordslist.toString());

        for(int i=0;i<=n;i++)
        {
            Random random=new Random();
            int j= random.nextInt(keywordslist.size());
            keywords.add((String) keywordslist.get(j));
        }

//        keywords=keywordsMaster.getKeywords();

        return keywords;
    }

    @Override
    public Boolean deleteByKeyword(String keyword) {
        KeywordsMaster keywordsMaster=new KeywordsMaster();
        try
        {
            List<KeywordsMaster> keywordlist= keywordsMasterDao.findAll();
            keywordsMaster=keywordlist.get(0);
            List<String> keywords=  keywordsMaster.getKeywords().stream().collect(Collectors.toList());
            if(keywords.contains(keyword))
            {
                keywords.remove(keyword);
                keywordsMaster.setKeywords(keywords.stream().collect(Collectors.toSet()));
                keywordsMasterDao.save(keywordsMaster);
                return true;
            }else {
                return false;
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}

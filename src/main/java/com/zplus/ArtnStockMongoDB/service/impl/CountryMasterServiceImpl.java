package com.zplus.ArtnStockMongoDB.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zplus.ArtnStockMongoDB.dao.AccountSettingDao;
import com.zplus.ArtnStockMongoDB.dao.CountryMasterDao;
import com.zplus.ArtnStockMongoDB.dto.req.CountryMasterRequest;
import com.zplus.ArtnStockMongoDB.dto.res.GeoName;
import com.zplus.ArtnStockMongoDB.dto.res.GeoNameResponse;
import com.zplus.ArtnStockMongoDB.model.ArtMaster;
import com.zplus.ArtnStockMongoDB.model.CountryMaster;
import com.zplus.ArtnStockMongoDB.model.CustomerReviewMaster;
import com.zplus.ArtnStockMongoDB.model.UserMaster;
import com.zplus.ArtnStockMongoDB.service.CountryMasterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.DataInput;
import java.net.http.HttpResponse;
import java.util.*;

@Service
public class CountryMasterServiceImpl implements CountryMasterService {

    @Autowired
    private CountryMasterDao countryMasterDao;


    @Value("geonames.username")
    public String username;

    @Autowired
    private AccountSettingDao accountSettingDao;

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public Boolean createCountryMaster(CountryMasterRequest countryMasterRequest) {
        CountryMaster countryMaster = new CountryMaster();
        BeanUtils.copyProperties(countryMasterRequest, countryMaster);
        countryMaster.setStatus("Active");
        try {
            countryMasterDao.save(countryMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateCountryMaster(CountryMasterRequest countryMasterRequest) {
        CountryMaster countryMaster = new CountryMaster();
        countryMaster.setCountryId(countryMasterRequest.getCountryId());
        BeanUtils.copyProperties(countryMasterRequest, countryMaster);
        countryMaster.setStatus(countryMasterRequest.getStatus());
        try {
            countryMasterDao.save(countryMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List getAllCountryMaster() {
        return countryMasterDao.findAll();
    }

    @Override
    public CountryMaster editCountryMaster(String countryId) {
        CountryMaster countryMaster=new CountryMaster();
        try {
            Optional<CountryMaster> customerReviewMaster1=countryMasterDao.findById(countryId);
            customerReviewMaster1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, countryMaster));
            return countryMaster;
        }
        catch (Exception e) {
            e.printStackTrace();
            return countryMaster;
        }
    }

    @Override
    public List getActiveCountryMaster() {
        return countryMasterDao.findByStatus("Active");
    }

    @Override
    public Boolean deleteByCountryId(String countryId) {
        try {
            countryMasterDao.deleteById(countryId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Map<String, String> getAllCountryNames() {
        Map<String,String > countryNames=new HashMap<>();
        String[] isCountries= Locale.getISOCountries();

        System.out.println(" isCountries  "+isCountries);

        for(String countryCode:isCountries)
        {
            Locale locale =new Locale("",countryCode);
            String countryName=locale.getDisplayCountry();
            countryNames.put(countryCode,countryName);
        }
        return countryNames;
    }

    @Override
    public Map<String, String> getAllStatesNames() {
        return null;
    }

    @Override
    public HttpResponse getAllCountryNameList() {

        RestTemplate template = new RestTemplate();
        Map<String, String> countriesList = new HashMap<>();
        String url = "http://api.geonames.org/countryInfoJSON?username=devesh11111";
        HttpResponse response = template.getForObject(url, HttpResponse.class);

        System.out.println("  response ="+response.toString());
        return response != null ?  response : null;

    }

                /*
                 String response = restTemplate.getForObject(url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(response, GeoNamesResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
                 */
    }


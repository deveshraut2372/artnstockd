package com.zplus.ArtnStockMongoDB.controller;

import com.amazonaws.services.pinpoint.model.MessageResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zplus.ArtnStockMongoDB.dao.AccountSettingDao;
import com.zplus.ArtnStockMongoDB.dto.req.CountryMasterRequest;
import com.zplus.ArtnStockMongoDB.dto.req.CustomerReviewMasterReq;
import com.zplus.ArtnStockMongoDB.dto.res.*;
import com.zplus.ArtnStockMongoDB.model.AccountSettingMaster;
import com.zplus.ArtnStockMongoDB.model.CountryMaster;
import com.zplus.ArtnStockMongoDB.model.CustomerReviewMaster;
import com.zplus.ArtnStockMongoDB.service.CountryMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/country_master")
public class CountryMasterController {

    @Autowired
    private CountryMasterService countryMasterService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AccountSettingDao accountSettingDao;



    @PostMapping("/create")
    public ResponseEntity createCountryMaster(@RequestBody CountryMasterRequest countryMasterRequest) {
        Boolean flag = countryMasterService.createCountryMaster(countryMasterRequest);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity updateCountryMaster(@RequestBody CountryMasterRequest countryMasterRequest) {
        Boolean flag = countryMasterService.updateCountryMaster(countryMasterRequest);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity getAllCountryMaster() {
        List list = countryMasterService.getAllCountryMaster();
        return new ResponseEntity(list, HttpStatus.CREATED);
    }
    @GetMapping(value = "/editCountryMaster/{countryId}")
    public ResponseEntity editCountryMaster(@PathVariable String countryId)
    {
        CountryMaster countryMaster = countryMasterService.editCountryMaster(countryId);
        if(countryMaster!=null)
        {
            return new ResponseEntity(countryMaster, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(countryMaster,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/getActiveCountryMaster")
    public ResponseEntity getActiveCountryMaster()
    {
        List list = countryMasterService.getActiveCountryMaster();
        return new ResponseEntity(list,HttpStatus.OK);

    }

    @DeleteMapping(value = "/deleteByCountryId/{countryId}")
    public ResponseEntity deleteByCountryId(@PathVariable("countryId") String countryId) {
        Boolean flag = countryMasterService.deleteByCountryId(countryId);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.OK);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getAllCountryNames")
    public ResponseEntity getAllCountryNames() throws JsonProcessingException {
            RestTemplate template=new RestTemplate();
            String url="http://api.geonames.org/countryInfoJSON?username=devesh11111";
            String response=template.getForObject(url,String.class);

            ObjectMapper objectMapper=new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode countriesArray = rootNode.path("geonames");

            List<GeoName> geoNameList=new ArrayList<>();
            GeoNameResponse geoNameResponse=new GeoNameResponse();

            for (JsonNode jsonNode : countriesArray) {
                GeoName geoName=new GeoName();
                geoName.setGeonameId(jsonNode.path("geonameId").asText());
                geoName.setCountryName(jsonNode.path("countryName").asText());
                geoName.setCountryCode(jsonNode.path("countryCode").asText());
                geoNameList.add(geoName);
            }

        geoNameList.sort(Comparator.comparing(GeoName::getCountryName));

//            List<GeoName> countryDeleteList=new ArrayList<>();
//            AccountSettingMaster accountSettingMaster=new AccountSettingMaster();
//            accountSettingMaster=accountSettingDao.findByStatus("Active");
//            countryDeleteList=accountSettingMaster.getCountryList();
//            geoNameList.removeAll(countryDeleteList);

        geoNameResponse.setCountries(geoNameList);


        if(!geoNameList.isEmpty())
        {
            return new ResponseEntity<>(geoNameResponse,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/getAllStateNames/{geonameId}")
    public ResponseEntity getAllStateNames(String geoNameId) throws JsonProcessingException {

        RestTemplate template=new RestTemplate();
        String url="http://api.geonames.org/childrenJSON?geonameId="+geoNameId+"&username=devesh11111";
        String response=template.getForObject(url,String.class);

        ObjectMapper objectMapper=new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response);
        JsonNode statesArray = rootNode.path("geonames");

        List<GeoStateName> geoStateNameList=new ArrayList<>();
        GeoNameStateResponse geoNameStateResponse=new GeoNameStateResponse();

        for (JsonNode jsonNode : statesArray) {
            GeoStateName geoStateName=new GeoStateName();
            geoStateName.setGeonameId(jsonNode.path("geonameId").asText());
            geoStateName.setName(jsonNode.path("name").asText());
            geoStateNameList.add(geoStateName);
        }
        geoStateNameList.sort(Comparator.comparing(GeoStateName::getName));

//        List<GeoStateName> stateDeleteList=new ArrayList<>();
//        AccountSettingMaster accountSettingMaster=new AccountSettingMaster();
//        accountSettingMaster=accountSettingDao.findByStatus("Active");
//        stateDeleteList=accountSettingMaster.getStatesList();
//        geoStateNameList.removeAll(stateDeleteList);


        geoNameStateResponse.setStateNameList(geoStateNameList);

        if(!geoStateNameList.isEmpty())
        {
            return new ResponseEntity<>(geoNameStateResponse,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/getAllCitysNames/{geonameId}")
    public ResponseEntity getAllCitysNames(String geoNameId) throws JsonProcessingException {
        RestTemplate template=new RestTemplate();
        String url="http://api.geonames.org/childrenJSON?geonameId="+geoNameId+"&username=devesh11111";
        String response=template.getForObject(url,String.class);

        ObjectMapper objectMapper=new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response);
        JsonNode citysArray = rootNode.path("geonames");

        List<GeoCityName> geoCityNameList=new ArrayList<>();
        GeoCityNameResponse geoCityNameResponse=new GeoCityNameResponse();

        for (JsonNode jsonNode : citysArray) {
            GeoCityName geoCityName=new GeoCityName();
            geoCityName.setGeonameId(jsonNode.path("geonameId").asText());
            geoCityName.setName(jsonNode.path("name").asText());
            geoCityNameList.add(geoCityName);
        }

        geoCityNameList.sort(Comparator.comparing(GeoCityName::getName));

//        List<GeoCityName> citysDeleteList=new ArrayList<>();
//        AccountSettingMaster accountSettingMaster=new AccountSettingMaster();
//        accountSettingMaster=accountSettingDao.findByStatus("Active");
//        citysDeleteList=accountSettingMaster.getCitysList();
//        geoCityNameList.removeAll(citysDeleteList);

        geoCityNameResponse.setCityNameList(geoCityNameList);

        if(!geoCityNameList.isEmpty())
        {
            return new ResponseEntity<>(geoCityNameResponse,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }



}
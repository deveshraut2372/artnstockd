package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.CountryMasterRequest;
import com.zplus.ArtnStockMongoDB.dto.res.GeoName;
import com.zplus.ArtnStockMongoDB.dto.res.GeoNameResponse;
import com.zplus.ArtnStockMongoDB.model.CountryMaster;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public interface CountryMasterService {
    Boolean createCountryMaster(CountryMasterRequest countryMasterRequest);

    Boolean updateCountryMaster(CountryMasterRequest countryMasterRequest);

    List getAllCountryMaster();

    CountryMaster editCountryMaster(String countryId);

    List getActiveCountryMaster();

    Boolean deleteByCountryId(String countryId);

    Map<String, String> getAllCountryNames();

    Map<String, String> getAllStatesNames();

    HttpResponse getAllCountryNameList();
}

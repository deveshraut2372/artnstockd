package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.OrderArtFrameMasterDao;
import com.zplus.ArtnStockMongoDB.service.OrderArtFrameMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderArtFrameMasterServiceImpl implements OrderArtFrameMasterService {

    @Autowired
    private OrderArtFrameMasterDao orderArtFrameMasterDao;


}

package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.service.OrderArtFrameMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/Order_Art_Frame_Master")
public class OrderArtFrameMasterController {

    @Autowired
    private OrderArtFrameMasterService orderArtFrameMasterService;



}

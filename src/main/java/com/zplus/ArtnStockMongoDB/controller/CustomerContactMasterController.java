package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.CustomerContactMasterReq;
import com.zplus.ArtnStockMongoDB.model.CustomerContactMaster;
import com.zplus.ArtnStockMongoDB.service.CustomerContactMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/customer_contact_master")
public class CustomerContactMasterController {

    @Autowired
    private CustomerContactMasterService customerContactMasterService;

    @PostMapping("/create")
    public ResponseEntity createCustomerContactMaster(@RequestBody CustomerContactMasterReq customerContactMasterReq) {
        Boolean flag = customerContactMasterService.createCustomerContactMaster(customerContactMasterReq);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/update")
    public ResponseEntity updateCustomerContactMaster(@RequestBody CustomerContactMasterReq customerContactMasterReq) {
        Boolean flag = customerContactMasterService.updateCustomerContactMaster(customerContactMasterReq);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/editCustomerContactMaster/{customerContactId}")
    public ResponseEntity editCustomerContactMaster(@PathVariable String customerContactId)
    {
        CustomerContactMaster customerContactMaster = customerContactMasterService.editCustomerContactMaster(customerContactId);
        if(customerContactMaster!=null)
        {
            return new ResponseEntity(customerContactMaster, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(customerContactMaster,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/getCustomerContactMasterList")
    public ResponseEntity getCustomerContactMasterList() {
        List list = customerContactMasterService.getCustomerContactMasterList();
        return new ResponseEntity(list, HttpStatus.CREATED);
    }
}

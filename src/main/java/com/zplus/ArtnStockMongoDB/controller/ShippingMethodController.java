package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.ShippingMethodRequest;
import com.zplus.ArtnStockMongoDB.dto.req.StyleReqDto;
import com.zplus.ArtnStockMongoDB.model.ShippingMethod;
import com.zplus.ArtnStockMongoDB.model.StyleMaster;
import com.zplus.ArtnStockMongoDB.service.ShippingMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/shipping_method")
public class ShippingMethodController {

    @Autowired
    private ShippingMethodService shippingMethodService;

    @PostMapping
    public ResponseEntity createShippingMethod(@RequestBody ShippingMethodRequest shippingMethodRequest) {
        Boolean flag = shippingMethodService.createShippingMethod(shippingMethodRequest);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update")
    public ResponseEntity updateShippingMethod(@RequestBody ShippingMethodRequest shippingMethodRequest) {
        Boolean flag = shippingMethodService.updateShippingMethod(shippingMethodRequest);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity getAllShippingMethod() {
        List list = shippingMethodService.getAllShippingMethod();
        return new ResponseEntity(list, HttpStatus.OK);
    }
    @GetMapping(value = "/editShippingMethod/{shippingMethodId}")
    public ResponseEntity editShippingMethod(@PathVariable String shippingMethodId)
    {
        ShippingMethod shippingMethod = shippingMethodService.editShippingMethod(shippingMethodId);
        if(shippingMethod!=null)
        {
            return new ResponseEntity(shippingMethod, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(shippingMethod,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

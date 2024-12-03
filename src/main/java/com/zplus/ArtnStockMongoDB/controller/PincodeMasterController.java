package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.PincodeMasterRequest;
import com.zplus.ArtnStockMongoDB.dto.res.Message;
import com.zplus.ArtnStockMongoDB.dto.res.PincodeResDto;
import com.zplus.ArtnStockMongoDB.model.PincodeMaster;
import com.zplus.ArtnStockMongoDB.service.PincodeMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/pinCode_master")
public class PincodeMasterController {

    @Autowired
    private PincodeMasterService pincodeMasterService;

    @PostMapping("/create")
    public ResponseEntity createPincodeMaster(@RequestBody PincodeMasterRequest pincodeMasterRequest) {
        Boolean flag = pincodeMasterService.createPincodeMaster(pincodeMasterRequest);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update")
    public ResponseEntity updatePincodeMaster(@RequestBody PincodeMasterRequest pincodeMasterRequest) {
        Boolean flag = pincodeMasterService.updatePincodeMaster(pincodeMasterRequest);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity getAllPincodeMaster() {
        List list = pincodeMasterService.getAllPincodeMaster();
        return new ResponseEntity(list, HttpStatus.CREATED);
    }
    @GetMapping(value = "/editPincodeMaster/{pinCodeId}")
    public ResponseEntity editPincodeMaster(@PathVariable String pinCodeId)
    {
        PincodeMaster pincodeMaster = pincodeMasterService.editPincodeMaster(pinCodeId);
        if(pincodeMaster!=null)
        {
            return new ResponseEntity(pincodeMaster, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(pincodeMaster,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/getActivePincodeMaster")
    public ResponseEntity getActivePincodeMaster()
    {
        List list = pincodeMasterService.getActivePincodeMaster();
        return new ResponseEntity(list,HttpStatus.OK);

    }
    @GetMapping(value = "/getPinCode/{pinCode}")
    public ResponseEntity getPinCode(@PathVariable Integer pinCode)
    {
        Message message = pincodeMasterService.getPinCode(pinCode);
        return new ResponseEntity(message,HttpStatus.OK);

    }

    @GetMapping("/getPincodesActive")
    public ResponseEntity getPincodesActive()
    {
        List<PincodeResDto> pincodeResDtos=new ArrayList<>();
        pincodeResDtos=pincodeMasterService.getPincodesActive();
        return new ResponseEntity(pincodeResDtos,HttpStatus.OK);
    }


    @DeleteMapping(value = "/deleteByPinCodeId/{pinCodeId}")
    public ResponseEntity deleteByPinCodeId(@PathVariable("pinCodeId") String pinCodeId) {
        Boolean flag = pincodeMasterService.deleteByPinCodeId(pinCodeId);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.OK);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

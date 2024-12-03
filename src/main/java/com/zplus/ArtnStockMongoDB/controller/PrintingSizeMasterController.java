package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.PrintingSizeRequest;
import com.zplus.ArtnStockMongoDB.model.PrintingSizeMaster;
import com.zplus.ArtnStockMongoDB.service.PrintingSizeMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/printing_size_master")
public class PrintingSizeMasterController {

    @Autowired
    private PrintingSizeMasterService printingSizeMasterService;

    @PostMapping("/create")
    public ResponseEntity createPrintingSizeMaster(@RequestBody PrintingSizeRequest printingSizeRequest) {
        Boolean flag = printingSizeMasterService.createPrintingSizeMaster(printingSizeRequest);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update")
    public ResponseEntity updatePrintingSizeMaster(@RequestBody PrintingSizeRequest printingSizeRequest) {
        Boolean flag = printingSizeMasterService.updatePrintingSizeMaster(printingSizeRequest);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity getAllPrintingSizeMaster() {
        List list = printingSizeMasterService.getAllPrintingSizeMaster();
        return new ResponseEntity(list, HttpStatus.CREATED);
    }
    @GetMapping(value = "/editPrintingSizeMaster/{printingSizeId}")
    public ResponseEntity editPrintingMaterialMaster(@PathVariable String printingSizeId)
    {
        PrintingSizeMaster printingSizeMaster = printingSizeMasterService.editPrintingMaterialMaster(printingSizeId);
        if(printingSizeMaster!=null)
        {
            return new ResponseEntity(printingSizeMaster, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(printingSizeMaster,HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @GetMapping(value = "/getActivePrintingSizeMasterList")
    public ResponseEntity getActivePrintingSizeMasterList()
    {
        List list = printingSizeMasterService.getActivePrintingSizeMasterList();
        return new ResponseEntity(list,HttpStatus.OK);

    }
}

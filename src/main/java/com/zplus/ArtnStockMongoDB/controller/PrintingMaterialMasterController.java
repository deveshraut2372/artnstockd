package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.MatReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.PrintingMaterialServiceReqDto;
import com.zplus.ArtnStockMongoDB.model.MatMaster;
import com.zplus.ArtnStockMongoDB.model.PrintingMaterialMaster;
import com.zplus.ArtnStockMongoDB.service.PrintingMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/printing_material_master")
public class PrintingMaterialMasterController {

    @Autowired
    private PrintingMaterialService printingMaterialService;

    @PostMapping("/create")
    public ResponseEntity createPrintingMaterialMaster(@RequestBody PrintingMaterialServiceReqDto printingMaterialServiceReqDto) {
        Boolean flag = printingMaterialService.createPrintingMaterialMaster(printingMaterialServiceReqDto);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update")
    public ResponseEntity updatePrintingMaterialMaster(@RequestBody PrintingMaterialServiceReqDto printingMaterialServiceReqDto) {
        Boolean flag = printingMaterialService.updatePrintingMaterialMaster(printingMaterialServiceReqDto);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity getAllPrintingMaterialMaster() {
        List list = printingMaterialService.getAllPrintingMaterialMaster();
        return new ResponseEntity(list, HttpStatus.CREATED);
    }
    @GetMapping(value = "/editPrintingMaterialMaster/{printingMaterialId}")
    public ResponseEntity editPrintingMaterialMaster(@PathVariable String printingMaterialId)
    {
        PrintingMaterialMaster printingMaterialMaster = printingMaterialService.editPrintingMaterialMaster(printingMaterialId);
        if(printingMaterialMaster!=null)
        {
            return new ResponseEntity(printingMaterialMaster, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(printingMaterialMaster,HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @GetMapping(value = "/getActivePrintingMaterialMasterList")
    public ResponseEntity getActivePrintingMaterialMasterList()
    {
        List list = printingMaterialService.getActivePrintingMaterialMasterList();
        return new ResponseEntity(list,HttpStatus.OK);

    }



}

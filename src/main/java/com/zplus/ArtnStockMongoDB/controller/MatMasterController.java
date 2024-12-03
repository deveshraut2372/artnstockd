package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.LimitedEditionReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.MatReqDto;
import com.zplus.ArtnStockMongoDB.model.LimitedEditionMaster;
import com.zplus.ArtnStockMongoDB.model.MatMaster;
import com.zplus.ArtnStockMongoDB.service.MatMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/mat_master")
public class MatMasterController {

    @Autowired
    private MatMasterService matMasterService;

    @PostMapping("/create")
    public ResponseEntity createMatMaster(@RequestBody MatReqDto matReqDto) {
        Boolean flag = matMasterService.createMatMaster(matReqDto);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update")
    public ResponseEntity updateMatMaster(@RequestBody MatReqDto matReqDto) {
        Boolean flag = matMasterService.updateMatMaster(matReqDto);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity getAllMatMaster() {
        List list = matMasterService.getAllMatMaster();
        return new ResponseEntity(list, HttpStatus.CREATED);
    }
    @GetMapping(value = "/editMatMaster/{matId}")
    public ResponseEntity editMatMaster(@PathVariable String matId)
    {
        MatMaster matMaster = matMasterService.editMatMaster(matId);
        if(matMaster!=null)
        {
            return new ResponseEntity(matMaster, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(matMaster,HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @GetMapping(value = "/getActiveList")
    public ResponseEntity getActiveMatMaster()
    {
        List list = matMasterService.getActiveMatMaster();
        return new ResponseEntity(list,HttpStatus.OK);

    }
    @GetMapping(value = "/getTypeWiseList/{matType}")
    public ResponseEntity getTypeWiseList(@PathVariable String matType)
    {
        MatMaster matMaster = matMasterService.getTypeWiseList(matType);
        return new ResponseEntity(matMaster,HttpStatus.OK);

    }
}

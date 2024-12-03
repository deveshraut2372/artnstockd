package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.ContributerMarkupRes;
import com.zplus.ArtnStockMongoDB.dto.req.OrientationMasterRequest;
import com.zplus.ArtnStockMongoDB.model.OrientationMaster;
import com.zplus.ArtnStockMongoDB.service.OrientationMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/shape_master")
public class OrientationMasterController {

    @Autowired
    private OrientationMasterService orientationMasterService;

    @PostMapping("/create")
    public ResponseEntity createOrientationMaster(@RequestBody OrientationMasterRequest orientationMasterRequest) {
        Boolean flag = orientationMasterService.createOrientationMaster(orientationMasterRequest);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update")
    public ResponseEntity updateOrientationMaster(@RequestBody OrientationMasterRequest orientationMasterRequest) {
        Boolean flag = orientationMasterService.updateOrientationMaster(orientationMasterRequest);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity getAllOrientationMaster() {
        List list = orientationMasterService.getAllOrientationMaster();
        return new ResponseEntity(list, HttpStatus.CREATED);
    }
    @GetMapping(value = "/editOrientationMaster/{orientationId}")
    public ResponseEntity editShapeMaster(@PathVariable String orientationId)
    {
        OrientationMaster orientationMaster = orientationMasterService.editOrientationMaster(orientationId);
        if(orientationMaster !=null)
        {
            return new ResponseEntity(orientationMaster, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(orientationMaster,HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @GetMapping(value = "/getActiveOrientationMasterList")
    public ResponseEntity getActiveOrientationMasterList()
    {
        List list = orientationMasterService.getActiveOrientationMasterList();
        return new ResponseEntity(list,HttpStatus.OK);

    }
    @GetMapping(value = "/getShapeWiseList/{shape}")
    public ResponseEntity getShapeWiseList(@PathVariable String shape)
    {
        List list = orientationMasterService.getShapeWiseList(shape);
        return new ResponseEntity(list,HttpStatus.OK);
    }

    @GetMapping(value = "/getContributerEarning100percentage/{markup}")
    public ResponseEntity getContributerEarning100percentage(@PathVariable("markup") Double markup )
    {
        ContributerMarkupRes contributerMarkupRes=new ContributerMarkupRes();
        contributerMarkupRes =orientationMasterService.getContributerEarning100percentage(markup);
        return new ResponseEntity(contributerMarkupRes,HttpStatus.OK);
    }


}

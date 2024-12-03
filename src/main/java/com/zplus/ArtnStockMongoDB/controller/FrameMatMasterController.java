package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.FrameMatMasterReqDto;
import com.zplus.ArtnStockMongoDB.model.FrameMatMaster;
import com.zplus.ArtnStockMongoDB.service.FrameMatMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/frame_mat_master")
public class FrameMatMasterController {

    @Autowired
    private FrameMatMasterService frameMatMasterService;

    @PostMapping("/create")
    public ResponseEntity createFrameMatMaster(@RequestBody FrameMatMasterReqDto frameMatMasterReqDto) {
        Boolean flag = frameMatMasterService.createFrameMatMaster(frameMatMasterReqDto);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity updateFrameMatMaster(@RequestBody FrameMatMasterReqDto frameMatMasterReqDto)
    {
        Boolean flag = frameMatMasterService.updateFrameMatMaster(frameMatMasterReqDto);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getAllFrameMatMaster")
    public ResponseEntity getAllFrameMatMaster() {
        List<FrameMatMaster> list = frameMatMasterService.getAllFrameMatMaster();
        return new ResponseEntity(list, HttpStatus.CREATED);
    }

    @GetMapping(value = "/editFrameMatMaster/{frameMatId}")
    public ResponseEntity editFrameMatMaster(@PathVariable String frameMatId) {
        FrameMatMaster frameMatMaster = frameMatMasterService.editFrameMatMaster(frameMatId);
        if (frameMatMaster != null) {
            return new ResponseEntity(frameMatMaster, HttpStatus.OK);
        } else {
            return new ResponseEntity(frameMatMaster, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getActiveFrameMatMaster")
    public ResponseEntity getActiveFrameMatMaster() {
        List list = frameMatMasterService.getActiveFrameMatMaster();
        return new ResponseEntity(list, HttpStatus.OK);

    }


}

package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.FrameReqDto;
import com.zplus.ArtnStockMongoDB.model.FrameMaster;
import com.zplus.ArtnStockMongoDB.service.FrameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/frame_master")
public class FrameController {

    @Autowired
    private FrameService frameService;

    @PostMapping("/create")
    public ResponseEntity createFrameMaster(@RequestBody FrameReqDto frameReqDto) {
        Boolean flag = frameService.createFrameMaster(frameReqDto);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity updateFrameMaster(@RequestBody FrameReqDto frameReqDto) {
        Boolean flag = frameService.updateFrameMaster(frameReqDto);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity getAllFrameMaster() {
        List list = frameService.getAllFrameMaster();
        return new ResponseEntity(list, HttpStatus.CREATED);
    }

    @GetMapping(value = "/editFrameMaster/{frameId}")
    public ResponseEntity editProductTypeMaster(@PathVariable String frameId)
    {
        FrameMaster frameMaster = frameService.editProductTypeMaster(frameId);
        if(frameMaster!=null)
        {
            return new ResponseEntity(frameMaster, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(frameMaster,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getActiveFrameMaster")
    public ResponseEntity getActiveFrameMaster()
    {
        List list = frameService.getActiveFrameMaster();
        return new ResponseEntity(list,HttpStatus.OK);
    }

}

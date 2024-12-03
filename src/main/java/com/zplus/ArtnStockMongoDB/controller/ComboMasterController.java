package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.ComboReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.UpdateComboReqDto;
import com.zplus.ArtnStockMongoDB.model.ComboMaster;
import com.zplus.ArtnStockMongoDB.service.ComboMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/combo_master")
public class ComboMasterController {

    @Autowired
    private ComboMasterService comboMasterService;

    @PostMapping
    public ResponseEntity createComboMaster(@RequestBody ComboReqDto comboReqDto) {
        Boolean flag = comboMasterService.createComboMaster(comboReqDto);
        if (Boolean.TRUE.equals(flag)) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity updateComboMaster(@RequestBody UpdateComboReqDto updateComboReqDto) {
        Boolean flag = comboMasterService.updateComboMaster(updateComboReqDto);
        if (Boolean.TRUE.equals(flag)) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity getAllComboMaster() {
        List list = comboMasterService.getAllComboMaster();
        return new ResponseEntity(list, HttpStatus.CREATED);
    }

    @GetMapping(value = "/editComboMaster/{comboId}")
    public ResponseEntity editComboMaster(@PathVariable String comboId) {
        ComboMaster comboMaster = comboMasterService.editComboMaster(comboId);
        if (comboMaster != null) {
            return new ResponseEntity(comboMaster, HttpStatus.OK);
        } else {
            return new ResponseEntity(comboMaster, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getActiveList")
    public ResponseEntity getActiveComboMaster()
    {
        List list = comboMasterService.getActiveComboMaster();
        return new ResponseEntity(list,HttpStatus.OK);
    }

    @GetMapping(value = "/getArtProductIdWiseComboMasterList/{artProductId}")
    public ResponseEntity getArtProductIdWiseComboMasterList(@PathVariable String artProductId)
    {
        List list=comboMasterService.getArtProductIdWiseComboMasterList(artProductId);
        return new ResponseEntity(list,HttpStatus.OK);
    }


    @GetMapping(value = "/getAdminArtProductIdWiseComboMasterList/{adminArtProductId}")
    public ResponseEntity getAdminArtProductIdWiseComboMasterList(@PathVariable String adminArtProductId)
    {
        List list=comboMasterService.getAdminArtProductIdWiseComboMasterList(adminArtProductId);
        return new ResponseEntity(list,HttpStatus.OK);
    }

}

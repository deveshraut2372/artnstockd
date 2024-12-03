package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.StyleReqDto;
import com.zplus.ArtnStockMongoDB.model.StyleMaster;
import com.zplus.ArtnStockMongoDB.service.StyleMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/style_master")
public class StyleMasterController {
    @Autowired
    private StyleMasterService styleMasterService;

    @PostMapping
    public ResponseEntity createStyleMaster(@RequestBody StyleReqDto styleReqDto) {
        Boolean flag = styleMasterService.createStyleMaster(styleReqDto);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update")
    public ResponseEntity updateStyleMaster(@RequestBody StyleReqDto styleReqDto) {
        Boolean flag = styleMasterService.updateStyleMaster(styleReqDto);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity getAllStyleMaster() {
        List list = styleMasterService.getAllStyleMaster();
        return new ResponseEntity(list, HttpStatus.CREATED);
    }
    @GetMapping(value = "/editStyleMaster/{styleId}")
    public ResponseEntity editStyleMaster(@PathVariable String styleId)
    {
        StyleMaster styleMaster = styleMasterService.editStyleMaster(styleId);
        if(styleMaster!=null)
        {
            return new ResponseEntity(styleMaster, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(styleMaster,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/getActiveStyleMaster")
    public ResponseEntity getActiveStyleMaster()
    {
        List list = styleMasterService.getActiveStyleMaster();
        return new ResponseEntity(list,HttpStatus.OK);

    }
    @GetMapping(value = "/getArtDropdownTrue")
    public ResponseEntity getArtDropdownTrue()
    {
        List list = styleMasterService.getArtDropdownTrue();
        return new ResponseEntity(list,HttpStatus.OK);
    }



    @DeleteMapping(value = "/deleteByStyleId/{styleId}")
    public ResponseEntity deleteByStyleId(@PathVariable String styleId)
    {
        Boolean flag= styleMasterService.deleteByStyleId(styleId);
        if(flag)
        {
            return new ResponseEntity(flag, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(flag,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

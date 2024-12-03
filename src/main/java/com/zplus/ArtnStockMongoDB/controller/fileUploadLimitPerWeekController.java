package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.FileUploadLimitPerWeekReqDto;
import com.zplus.ArtnStockMongoDB.model.FileUploadLimitPerWeekMaster;
import com.zplus.ArtnStockMongoDB.service.FileUploadLimitPerWeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/file_upload_limit_per_week_master")
public class fileUploadLimitPerWeekController {

    @Autowired
    private FileUploadLimitPerWeekService fileUploadLimitPerWeekService;

    @PostMapping("/save")
    public ResponseEntity FileUploadLimitPerWeekMaster(@RequestBody FileUploadLimitPerWeekReqDto fileUploadLimitPerWeekReqDto) {
        Boolean flag = fileUploadLimitPerWeekService.saveFileUploadLimitPerWeekMaster(fileUploadLimitPerWeekReqDto);
        return new ResponseEntity(flag, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity updateFileUploadLimitPerWeekMaster(@RequestBody FileUploadLimitPerWeekReqDto fileUploadLimitPerWeekReqDto) {
        Boolean flag = fileUploadLimitPerWeekService.updateFrequentlyAskedQuestions(fileUploadLimitPerWeekReqDto);
        return new ResponseEntity(flag, HttpStatus.OK);
    }

    @GetMapping("/getAllListFileUploadLimitPerWeekMaster")
    public ResponseEntity getAllListFileUploadLimitPerWeekMaster() {
        List<FileUploadLimitPerWeekMaster> list = fileUploadLimitPerWeekService.getAllListFileUploadLimitPerWeekMaster();
        return new ResponseEntity(list, HttpStatus.OK);

    }
    @GetMapping("/getByFileUploadLimitPerWeekId/{fileUploadLimitPerWeekId}")
    public ResponseEntity getByFileUploadLimitPerWeekId(@PathVariable String fileUploadLimitPerWeekId) {
        FileUploadLimitPerWeekMaster fileUploadLimitPerWeekMaster = fileUploadLimitPerWeekService.getByFileUploadLimitPerWeekId(fileUploadLimitPerWeekId);
        return new ResponseEntity(fileUploadLimitPerWeekMaster, HttpStatus.OK);
    }
}

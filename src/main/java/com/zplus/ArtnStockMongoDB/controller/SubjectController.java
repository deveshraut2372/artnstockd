package com.zplus.ArtnStockMongoDB.controller;


import com.zplus.ArtnStockMongoDB.dto.req.SubjectReqDto;
import com.zplus.ArtnStockMongoDB.model.SubjectMaster;
import com.zplus.ArtnStockMongoDB.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/subject_master")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @PostMapping
    public ResponseEntity createSubjectMaster(@RequestBody SubjectReqDto subjectReqDto) {
        Boolean flag = subjectService.createSubjectMaster(subjectReqDto);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping
    public ResponseEntity updateSubjectMaster(@RequestBody SubjectReqDto subjectReqDto) {
        Boolean flag = subjectService.updateSubjectMaster(subjectReqDto);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity getAllSubject() {
        List list = subjectService.getAllSubject();
        return new ResponseEntity(list, HttpStatus.CREATED);
    }

    @GetMapping(value = "/editSubjectMaster/{subjectId}")
    public ResponseEntity editMainCategoryMaster(@PathVariable String subjectId)
    {
        SubjectMaster subjectMaster = subjectService.editSubjectMaster(subjectId);
        if(subjectMaster!=null)
        {
            return new ResponseEntity(subjectMaster, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(subjectMaster,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getActiveSubject")
    public ResponseEntity getActiveSubject()
    {
        List list = subjectService.getActiveSubject();
        return new ResponseEntity(list,HttpStatus.OK);

    }
    @GetMapping(value = "/getTypeWiseSubjectList/{type}")
    public ResponseEntity getTypeWiseSubjectList(@PathVariable String type)
    {
        List list = subjectService.getTypeWiseSubjectList(type);
        return new ResponseEntity(list,HttpStatus.OK);

    }
    @GetMapping(value = "/searchByText/{searchText}")
    private ResponseEntity searchByText(@PathVariable String searchText) {
        List<SubjectMaster> subjectMasterList = subjectService.searchByText(searchText);

        return new ResponseEntity(subjectMasterList, HttpStatus.OK);
    }
    @GetMapping(value = "/getArtDropdownTrue")
    public ResponseEntity getArtDropdownTrue()
    {
        List list = subjectService.getArtDropdownTrue();
        return new ResponseEntity(list,HttpStatus.OK);

    }
//    @GetMapping(value = "/getSubjectIdAndArtIdWiseList/{subjectId}")
//    public ResponseEntity getSubjectIdAndArtIdWiseList(@PathVariable String subjectId)
//    {
//        List list = subjectService.getSubjectIdAndArtIdWiseList(subjectId);
//        return new ResponseEntity(list,HttpStatus.OK);
//
//    }



    @DeleteMapping(value = "/deleteBySubjectId/{subjectId}")
    public ResponseEntity deleteBySubjectId(@PathVariable String subjectId)
    {
        Boolean flag= subjectService.deleteBySubjectId(subjectId);
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

package com.zplus.ArtnStockMongoDB.controller;


import com.zplus.ArtnStockMongoDB.dto.req.KeywordsMasterReq;
import com.zplus.ArtnStockMongoDB.dto.res.MainResDto;
import com.zplus.ArtnStockMongoDB.service.KeywordsMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/Keywords_Master")
public class KeywordsMasterController {

    @Autowired
    KeywordsMasterService keywordsMasterService;

    @PostMapping
    public ResponseEntity addKeywords(@RequestBody KeywordsMasterReq keywordsMasterReq)
    {
        MainResDto mainResDto=new MainResDto();
        Boolean flag=keywordsMasterService.addKeywords(keywordsMasterReq);
        if(flag)
        {
            mainResDto.setFlag(flag);
            mainResDto.setResponseCode(HttpStatus.OK.value());
            mainResDto.setMessage(" Keywords add  Succesfully ");
            return new ResponseEntity( mainResDto, HttpStatus.OK);
        }
        else {
            mainResDto.setFlag(flag);
            mainResDto.setResponseCode(HttpStatus.BAD_REQUEST.value());
            mainResDto.setMessage(" Does Not add keywords ");
            return new ResponseEntity( mainResDto, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity getKeywords()
    {
        Set<String> set=keywordsMasterService.getKeywords();
        return new ResponseEntity( set,HttpStatus.OK);

    }

    @GetMapping("/getKeywordsByNumber/{number}")
    public ResponseEntity getKeywordsByNumber(@PathVariable Integer number)
    {
        Set<String> set=keywordsMasterService.getKeywordsByNumber(number);
        return new ResponseEntity( set,HttpStatus.OK);
    }


    @DeleteMapping(value = "/deleteByKeyword/{keyword}")
    public ResponseEntity deleteByKeyword(@PathVariable("keyword") String keyword)
    {
        MainResDto mainResDto=new MainResDto();
        Boolean flag=keywordsMasterService.deleteByKeyword(keyword);
        if(flag)
        {
            mainResDto.setFlag(flag);
            mainResDto.setResponseCode(HttpStatus.OK.value());
            mainResDto.setMessage(" Keywords Delete  Succesfully ");
            return new ResponseEntity( mainResDto, HttpStatus.OK);
        }
        else {
            mainResDto.setFlag(flag);
            mainResDto.setResponseCode(HttpStatus.BAD_REQUEST.value());
            mainResDto.setMessage(" Does Not Delete keywords ");
            return new ResponseEntity( mainResDto, HttpStatus.BAD_REQUEST);
        }
    }
}

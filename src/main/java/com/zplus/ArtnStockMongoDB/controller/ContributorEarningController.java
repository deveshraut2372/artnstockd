package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.ContributorEarningRequestDto;
import com.zplus.ArtnStockMongoDB.dto.res.ContributorEarningDateResponseDto;
import com.zplus.ArtnStockMongoDB.dto.res.ContributorEarningResponseDto;
import com.zplus.ArtnStockMongoDB.service.ContributorEarningService;
import com.zplus.ArtnStockMongoDB.service.impl.ContributorEarningServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/contributorearning/")
public class ContributorEarningController {
    @Autowired  
    public ContributorEarningServiceImpl contributorEaringService;
    @Autowired
    public ContributorEarningService contributorEarningService;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody ContributorEarningRequestDto contributorEarningRequestDto){
        contributorEaringService.CalculateEarning(contributorEarningRequestDto);
        return new ResponseEntity("record saved", HttpStatus.OK);
    }
    @GetMapping("/get")
    public ResponseEntity get(){
        return new ResponseEntity(contributorEarningService.getAll(),HttpStatus.OK);
    }

    @GetMapping("/get/{userid}")
    public ResponseEntity get(@PathVariable("userid") String userid)
    {
        ContributorEarningResponseDto contributorEarningResponseDtoList=  contributorEarningService.getRecordByUser(userid);
        return new ResponseEntity(contributorEarningResponseDtoList,HttpStatus.OK);
    }

    @GetMapping("/get/{userid}/{month}")
    public ResponseEntity getData(@PathVariable("userid") String userid,@PathVariable("month") String month)
    {
        ContributorEarningResponseDto contributorEarningResponseDtoList=  contributorEarningService.getDateWiseData(userid,month);
        return new ResponseEntity(contributorEarningResponseDtoList,HttpStatus.OK);
    }

    @GetMapping("/year/{userid}")
    public ResponseEntity getYear(@PathVariable("userid") String userid)
    {
        List<Integer> years=  contributorEarningService.getYear(userid);
        return new ResponseEntity(years,HttpStatus.OK);
    }

    @GetMapping("/get/{userid}/{month}/{year}")
    public ResponseEntity getData(@PathVariable("userid") String userid,@PathVariable("month") String month,@PathVariable("year") int year)
    {
        List<ContributorEarningDateResponseDto> contributorEarningDateResponseDto=  contributorEarningService.getData(userid,month,year);
        return new ResponseEntity(contributorEarningDateResponseDto,HttpStatus.OK);
    }
}

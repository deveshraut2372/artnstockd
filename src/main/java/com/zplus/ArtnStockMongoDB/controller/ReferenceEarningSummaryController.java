package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.ReferenceEarningSummaryReqDto;
import com.zplus.ArtnStockMongoDB.dto.res.ContributorEarningDateResponseDto;
import com.zplus.ArtnStockMongoDB.dto.res.ReferenceEarningSummaryResDto;
import com.zplus.ArtnStockMongoDB.dto.res.TotalRefeEarningResDto;
import com.zplus.ArtnStockMongoDB.model.ReferenceEarningSummary;
import com.zplus.ArtnStockMongoDB.service.ReferenceEarningSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/reference_earning")
public class ReferenceEarningSummaryController {

    @Autowired
    private ReferenceEarningSummaryService referenceEarningSummaryService;

    @PostMapping("/save")
    public ResponseEntity saveRefer(@RequestBody ReferenceEarningSummaryReqDto referenceEarningSummaryReqDto){
        Boolean flag= referenceEarningSummaryService.saveRefe(referenceEarningSummaryReqDto);
        return new ResponseEntity(flag, HttpStatus.OK);
    }
    @GetMapping("/getreference/{userid}")
    public TotalRefeEarningResDto getdata(@PathVariable("userid") String userid)
    {
        TotalRefeEarningResDto res=referenceEarningSummaryService.getReferenceData(userid);//; //referenceEarningSummaryService.getReferenceData(userid);
        return res;
    }
//    @GetMapping("/getByUserIdAndMonthYear/{userId}/{month}/{year}")
//    public ResponseEntity<List<ReferenceEarningSummaryResDto>> getByUserIdAndMonthYear(@PathVariable("userId") String userId, @PathVariable("month") int month, @PathVariable("year") int year) {
//        List<ReferenceEarningSummaryResDto> resultList = referenceEarningSummaryService.getReferenceEarningSummaryData(userId, month, year);
//        return new ResponseEntity<>(resultList, HttpStatus.OK);
//    }

    @GetMapping("/getByUserIdAndMonthYear/{userId}/{month}/{year}")
    public ResponseEntity<List<ReferenceEarningSummaryResDto>> getByUserIdAndMonthYear(@PathVariable("userId") String userId, @PathVariable("month") int month, @PathVariable("year") int year) {
        List<ReferenceEarningSummaryResDto> resultList = referenceEarningSummaryService.getReferenceEarningSummaryData(userId, month, year);
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }


}


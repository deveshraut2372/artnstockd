package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.FrequentlyAskedQuestionsReqDto;
import com.zplus.ArtnStockMongoDB.model.FrequentlyAskedQuestionsMaster;
import com.zplus.ArtnStockMongoDB.service.FrequentlyAskedQuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/frequently_asked_master")
public class FrequentlyAskedQuestionsController {

    @Autowired
    private FrequentlyAskedQuestionsService frequentlyAskedQuestionsService;

    @PostMapping("/save")
    public ResponseEntity saveFrequentlyAskedQuestions(@RequestBody FrequentlyAskedQuestionsReqDto frequentlyAskedQuestionsReqDto) {
        Boolean flag = frequentlyAskedQuestionsService.saveFrequentlyAskedQuestions(frequentlyAskedQuestionsReqDto);
        return new ResponseEntity(flag, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity updateFrequentlyAskedQuestions(@RequestBody FrequentlyAskedQuestionsReqDto frequentlyAskedQuestionsReqDto) {
        Boolean flag = frequentlyAskedQuestionsService.updateFrequentlyAskedQuestions(frequentlyAskedQuestionsReqDto);
        return new ResponseEntity(flag, HttpStatus.OK);
    }

    @GetMapping("/getAllListFrequentlyAskedQuestions")
    public ResponseEntity getAllListFrequentlyAskedQuestions() {
        List<FrequentlyAskedQuestionsMaster> frequentlyAskedQuestionsMasterList = frequentlyAskedQuestionsService.getAllListFrequentlyAskedQuestions();
        return new ResponseEntity(frequentlyAskedQuestionsMasterList, HttpStatus.OK);

    }

    @GetMapping("/getByFaqId/{faqId}")
    public ResponseEntity getByFaqId(@PathVariable String faqId) {
        FrequentlyAskedQuestionsMaster frequentlyAskedQuestionsMaster = frequentlyAskedQuestionsService.getByFaqId(faqId);
        return new ResponseEntity(frequentlyAskedQuestionsMaster, HttpStatus.OK);
    }

    @GetMapping(value = "/getActiveListFrequentlyAskedQuestions")
    private ResponseEntity activeFrequentlyAskedQuestions() {
        List list = frequentlyAskedQuestionsService.getActiveListFrequentlyAskedQuestions();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/getTypeWiseFaq/{type}")
    public ResponseEntity getTypeWiseFaq(@PathVariable String type) {
        List<FrequentlyAskedQuestionsMaster> list = frequentlyAskedQuestionsService.getTypeWiseFaq(type);
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @DeleteMapping("/DeleteByFaqId/{faqId}")
    public ResponseEntity DeleteByFaqId(@PathVariable String faqId) {
        Boolean flag= frequentlyAskedQuestionsService.DeleteByFaqId(faqId);
        return new ResponseEntity(flag, HttpStatus.OK);
    }
}

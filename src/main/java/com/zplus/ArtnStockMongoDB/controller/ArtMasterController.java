package com.zplus.ArtnStockMongoDB.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zplus.ArtnStockMongoDB.dao.KeywordsMasterDao;
import com.zplus.ArtnStockMongoDB.dao.StyleMasterDao;
import com.zplus.ArtnStockMongoDB.dao.UserDao;
import com.zplus.ArtnStockMongoDB.dto.req.ArtMasterFilterReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.ArtMasterReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.ArtMasterUpdateReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.SortRequest;
import com.zplus.ArtnStockMongoDB.dto.res.ApprovedRatioRes;
import com.zplus.ArtnStockMongoDB.dto.res.ArtMasterRes;
import com.zplus.ArtnStockMongoDB.model.ArtMaster;
import com.zplus.ArtnStockMongoDB.model.ContributorArtMarkupMaster;
import com.zplus.ArtnStockMongoDB.model.KeywordCountMaster;
import com.zplus.ArtnStockMongoDB.service.ArtMasterService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/art_master")
public class ArtMasterController {


    @Autowired
    private ArtMasterService artMasterService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private StyleMasterDao styleMasterDao;

    @PostMapping("/create")
    public ResponseEntity createArtMaster(@RequestBody ArtMasterReqDto artMasterReqDto) {

        ArtMasterRes artMasterRes=new ArtMasterRes();
        artMasterRes = artMasterService.createArtMaster(artMasterReqDto);
        return new ResponseEntity(artMasterRes, HttpStatus.OK);
    }


    @PutMapping("/update")
    public ResponseEntity updateArtMaster(@RequestBody ArtMasterReqDto artMasterReqDto) {
        Boolean flag = artMasterService.updateArtMaster(artMasterReqDto);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.UNAUTHORIZED);
        }
    }

    // Devesh Update ArtMaster
    @PutMapping("/updateDArtMaster")
    public ResponseEntity updateDArtMaster(@RequestBody ArtMasterUpdateReqDto artMasterUpdateReqDto) {
        Boolean flag = artMasterService.updateDArtMaster(artMasterUpdateReqDto);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/updateDsArtMaster")
    public ResponseEntity updateDsArtMaster() {
        Boolean flag = artMasterService.updateDsArtMaster();
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(value = "/editArtMaster/{artId}")
    public ResponseEntity editArtMaster(@PathVariable String artId) {
        ArtMaster artMaster = artMasterService.editArtMaster(artId);
        if (artMaster != null) {
            return new ResponseEntity(artMaster, HttpStatus.OK);
        } else {
            return new ResponseEntity(artMaster, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(value = "/getActiveArtMasterList")
    public ResponseEntity getActiveArtMasterList() {
        List list = artMasterService.getActiveArtMasterList();
        return new ResponseEntity(list, HttpStatus.OK);

    }

    @GetMapping(value = "/getUserIdWiseArtMasterList/{userId}")
    public ResponseEntity getUserIdWiseArtMasterList(@PathVariable String userId) {
        List list = artMasterService.getUserIdWiseArtMasterList(userId);
        return new ResponseEntity(list, HttpStatus.OK);

    }

    @GetMapping(value = "/getStyleIdIdWiseStyleMaster/{styleId}")
    public ResponseEntity getStyleIdIdWiseStyleMaster(@PathVariable String styleId) {
        List list = artMasterService.getStyleIdIdWiseStyleMaster(styleId);
        return new ResponseEntity(list, HttpStatus.OK);

    }

    @GetMapping(value = "/getSubjectIdWiseSubjectMaster/{subjectId}")
    public ResponseEntity getSubjectIdWiseSubjectMaster(@PathVariable String subjectId) {
        List list = artMasterService.getSubjectIdWiseSubjectMaster(subjectId);
        return new ResponseEntity(list, HttpStatus.OK);

    }

    @PostMapping("/ArtMasterFilter")
    public ResponseEntity ArtMasterFilter(@RequestBody ArtMasterFilterReqDto artMasterFilterReqDto) {
        List list = artMasterService.ArtMasterFilter(artMasterFilterReqDto);
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping(value = "/getByCountUser")
    private ResponseEntity getByCountUser() {
        Long cnt = artMasterService.getByCountUser();

        return new ResponseEntity(cnt, HttpStatus.OK);
    }

    @GetMapping(value = "/searchByText/{searchText}")
    private ResponseEntity searchByText(@PathVariable String searchText) {
        List<ArtMaster> artMasterList = artMasterService.searchByText(searchText);

        return new ResponseEntity(artMasterList, HttpStatus.OK);
    }

    @GetMapping(value = "/searchTextByArtName/{searchText}")
    private ResponseEntity searchTextByArtName(@PathVariable String searchText) {
        List<ArtMaster> artMasterList = artMasterService.searchTextByArtName(searchText);

        return new ResponseEntity(artMasterList, HttpStatus.OK);
    }

    @PutMapping(value = "/getArtIdWiseChangeStatus/{artId}")
    public ResponseEntity getArtIdWiseChangeStatus(@PathVariable String artId) throws MessagingException, IOException {
        Boolean flag = artMasterService.getArtIdWiseChangeStatus(artId);
        return new ResponseEntity(flag, HttpStatus.OK);
    }

    @PutMapping(value = "/getUserIdWiseApprovedPerChange/{userId}")
    public ResponseEntity getUserIdWisePerChange(@PathVariable String userId) throws MessagingException, IOException {
        Boolean flag = artMasterService.getUserIdWiseApprovedPerChange(userId);
        return new ResponseEntity(flag, HttpStatus.OK);
    }

    @GetMapping(value = "/searchTextByUserFirstName/{userFirstName}")
    private ResponseEntity searchTextByUserFirstName(@PathVariable String userFirstName) {
        List<ArtMaster> artMasterList = artMasterService.searchTextByUserFirstName(userFirstName);

        return new ResponseEntity(artMasterList, HttpStatus.OK);
    }

    @GetMapping(value = "/getSimilarImage/{image}")
    private ResponseEntity getSimilarImage(@PathVariable String image) {
        List<ArtMaster> artMasterList = artMasterService.getSimilarImage(image);
        return new ResponseEntity(artMasterList, HttpStatus.OK);
    }
    @GetMapping(value = "/getKeywordWiseArtMasterList/{keyword}")
    private ResponseEntity getKeywordWiseArtMasterList(@PathVariable String keyword) {

        List list = artMasterService.getKeywordWiseArtMasterList(keyword);
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping(value = "/FindSimilarImageList/{image}")
    private ResponseEntity FindSimilarImageList(
            @ApiParam(value = "The URL of the image to find similar images for", required = true)
            @RequestParam String image) throws MalformedURLException, JsonProcessingException {
        List<ArtMaster> artMasterList = artMasterService.FindSimilarImageList(image);

        return new ResponseEntity(artMasterList, HttpStatus.OK);
    }

    //    Subject Name Filter Api
    @GetMapping(value = "/subjectNameWiseArtList/{subjectName}")
    public ResponseEntity subjectNameWiseArtList(@PathVariable String subjectName) {
        List list = artMasterService.subjectNameWiseArtList(subjectName);
        return new ResponseEntity(list, HttpStatus.OK);
    }

    //    Style Name Filter Api
    @GetMapping(value = "/styleNameWiseArtList/{name}")
    public ResponseEntity styleNameWiseArtList(@PathVariable String name) {
        List list = artMasterService.styleNameWiseArtList(name);
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @PostMapping("/ArtFilter/{type}/{text}")
    public ResponseEntity ArtFilter(@RequestBody ArtMasterFilterReqDto artMasterFilterReqDto, @PathVariable String type, @PathVariable String text) {
        System.out.println(" artMasterFilterReqDto  ===="+artMasterFilterReqDto.toString());
        List list = artMasterService.ArtFilter(artMasterFilterReqDto, type, text);
        return new ResponseEntity(list, HttpStatus.OK);
    }

    // ner ArtFilter Api
    @PostMapping("/ArtFilterNew/{type}/{text}/{page}/{number}")
    public ResponseEntity ArtFilterNew(@RequestBody ArtMasterFilterReqDto artMasterFilterReqDto, @PathVariable String type, @PathVariable String text,@PathVariable("page") Integer page,@PathVariable("number") Integer number) {
        System.out.println(" artMasterFilterReqDto  ===="+artMasterFilterReqDto.toString());
        List list = artMasterService.ArtFilterNew(artMasterFilterReqDto, type, text,page,number);
        return new ResponseEntity(list, HttpStatus.OK);
    }

    //    KeywordCountMasterList
    @GetMapping(value = "/getKeywordMasterList")
    private ResponseEntity getKeywordMasterList() {
        List list = artMasterService.getKeywordMasterList();
        return new ResponseEntity(list, HttpStatus.OK);
    }

//search api in keywordCountMaster
    @GetMapping(value = "/searchKeywordCountMaster/{keyword}")
    private ResponseEntity searchKeywordCountMaster(@PathVariable String keyword)
    {
        List list = artMasterService.searchKeywordCountMaster(keyword);
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping(value = "/getKeywordCountIdWiseData/{keywordCountId}")
    private ResponseEntity getKeywordCountIdWiseData(@PathVariable String keywordCountId) {
        KeywordCountMaster keywordCountMaster = artMasterService.getKeywordCountIdWiseData(keywordCountId);
        return new ResponseEntity(keywordCountMaster, HttpStatus.OK);
    }

    @GetMapping(value = "/getUserIdAndStatusWiseUserMaster/{userId}/{status}")
    public ResponseEntity getUserIdAndStatusWiseUserMaster(@PathVariable String userId, @PathVariable String status) {
        List<ArtMaster> list = artMasterService.getUserIdAndStatusWiseUserMaster(userId, status);
        return new ResponseEntity(list, HttpStatus.OK);
    }

    //    update Status into admin panel
    @PutMapping(value = "/updateUserIdWiseStatus/{userId}/{status}")
    public ResponseEntity updateUserIdWiseStatus(@PathVariable String userId, @PathVariable String status) throws MessagingException, IOException {
        Boolean flag = artMasterService.updateUserIdWiseStatus(userId, status);
        return new ResponseEntity(flag, HttpStatus.OK);
    }
    @GetMapping(value = "/getArtIdWiseContributorArtMarkup/{artId}")
    private ResponseEntity getArtIdWiseContributorArtMarkup(@PathVariable String artId) {

        List<ContributorArtMarkupMaster> contributorArtMarkupMaster = artMasterService.getArtIdWiseContributorArtMarkup(artId);
        return new ResponseEntity(contributorArtMarkupMaster, HttpStatus.OK);
    }
    @GetMapping(value = "/getArtIdAndShapeWiseContributorArtMarkup/{artId}/{shape}")
    private ResponseEntity getArtIdAndShapeWiseContributorArtMarkup(@PathVariable String artId ,@PathVariable String shape) {

        List<ContributorArtMarkupMaster> contributorArtMarkupMaster = artMasterService.getArtIdAndShapeWiseContributorArtMarkup(artId,shape);
        return new ResponseEntity(contributorArtMarkupMaster, HttpStatus.OK);
    }

    @GetMapping(value = "/getAllArtMasterByStatus/{status}")
    private ResponseEntity getAllArtMasterByStatus(@PathVariable String status) {

        List<ArtMaster> artMasterList = artMasterService.getAllArtMasterByStatus(status);
        return new ResponseEntity(artMasterList, HttpStatus.OK);
    }

    @GetMapping(value = "/gerReleaseStatusByArtMasterId/{artId}")
    public ResponseEntity gerReleaseStatusByArtMasterId(@PathVariable("artId") String artId)
    {
        List list=new ArrayList();
        list=artMasterService.gerReleaseStatusByArtMasterId(artId);
        return new ResponseEntity(list,HttpStatus.OK);
    }


    @GetMapping("/getContrubuterMarkupByArtId/{artId}")
    public ResponseEntity getContrubuterMarkupByArtId(@PathVariable("artId") String artId)
    {
        ContributorArtMarkupMaster contributorArtMarkupMaster=new ContributorArtMarkupMaster();
        contributorArtMarkupMaster=artMasterService.getContrubuterMarkupByArtId(artId);
        return new ResponseEntity( contributorArtMarkupMaster,HttpStatus.OK);
    }


    @GetMapping("/getAllArtsAndProductByUserId/{userId}")
    public ResponseEntity getAllArtsAndProductByUserId(@PathVariable("userId") String userId )
    {
        List list=new ArrayList();
        list=artMasterService.getAllArtsAndProductByUserId(userId);
        return new ResponseEntity(list,HttpStatus.OK);
    }

    @PostMapping("/getAllArtsAndProductBySort")
    public ResponseEntity getAllArtsAndProductBySort(@RequestBody SortRequest sortRequest)
    {
        List list=new ArrayList();
        list=artMasterService.getAllArtsAndProductBySort(sortRequest);
        return new ResponseEntity(list,HttpStatus.OK);
    }

    @GetMapping(value = "/getAllArtMasterByStatusandUserId/{status}")
    private ResponseEntity getAllArtMasterByStatusandUserId(@PathVariable String status,@PathVariable String userId) {
        List<ArtMaster> artMasterList = artMasterService.getAllArtMasterByStatusandUserId(status,userId);
        return new ResponseEntity(artMasterList, HttpStatus.OK);
    }


    @GetMapping(value = "/getCountOfArtByStatus/{status}/{userId}")
    private ResponseEntity getCountOfArtByStatus(@PathVariable String status,@PathVariable String userId) {
        Integer cnt = artMasterService.getCountOfArtByStatus(status,userId);
        return new ResponseEntity(cnt, HttpStatus.OK);
    }


    @GetMapping(value = "/getArtByKeyword/{keyword}/{userId}")
    private ResponseEntity getArtByKeyword(@PathVariable String keyword,@PathVariable String userId) {
        List<ArtMaster> artMasterList = artMasterService.getArtByKeyword(keyword,userId);
        return new ResponseEntity(artMasterList, HttpStatus.OK);
    }

    @GetMapping("/getAllKeywordByArtName/{artName}")
    public ResponseEntity getAllKeywordByArtName(@PathVariable("artName") String artName)
    {
        Set<String> keywordsList=new HashSet<>();
        keywordsList=artMasterService.getAllKeywordByArtName(artName);
        return new ResponseEntity(keywordsList, HttpStatus.OK);
    }

    @GetMapping(value = "/getArtCountsByUserId/{userId}")
    public ResponseEntity getCountsByUserId(String userId)
    {
        int count=artMasterService.getAllArtsByUserId(userId);
        return new ResponseEntity(count, HttpStatus.OK);
    }








}

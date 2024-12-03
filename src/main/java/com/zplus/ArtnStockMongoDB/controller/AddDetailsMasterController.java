package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.AddDetailsGetReq;
import com.zplus.ArtnStockMongoDB.dto.req.AddDetailsMasterReq;
import com.zplus.ArtnStockMongoDB.dto.req.DeleteAddDetailsreq;
import com.zplus.ArtnStockMongoDB.dto.res.MainResDto;
import com.zplus.ArtnStockMongoDB.model.AddDetailsMaster;
import com.zplus.ArtnStockMongoDB.service.AddDetailsMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/Add_Details_Master")
public class AddDetailsMasterController {

    @Autowired
    private AddDetailsMasterService addDetailsMasterService;

    @PostMapping
    private ResponseEntity createArtDetails(@RequestBody AddDetailsMasterReq addDetailsMasterReq)
    {
        MainResDto mainResDto=new MainResDto();
        Boolean flag= addDetailsMasterService.createArtDetails(addDetailsMasterReq);
        if(flag)
        {
            mainResDto.setMessage(" ArtDetails created ");
            mainResDto.setResponseCode(HttpStatus.OK.value());
            mainResDto.setFlag(flag);
            return new ResponseEntity(mainResDto, HttpStatus.OK);
        }else {
            mainResDto.setMessage("ArtDetails Does Not Created ");
            mainResDto.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            mainResDto.setFlag(flag);
            return new ResponseEntity(mainResDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    private ResponseEntity updateArtDetails(@RequestBody AddDetailsMasterReq addDetailsMasterReq)
    {
        MainResDto mainResDto=new MainResDto();
        Boolean flag= addDetailsMasterService.updateArtDetails(addDetailsMasterReq);

        if(flag)
        {
            mainResDto.setMessage(" ArtDetails Updated ");
            mainResDto.setResponseCode(HttpStatus.OK.value());
            mainResDto.setFlag(flag);
            return new ResponseEntity(mainResDto, HttpStatus.OK);
        }else {
            mainResDto.setMessage("ArtDetails Does Not Updated ");
            mainResDto.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            mainResDto.setFlag(flag);
            return new ResponseEntity(mainResDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/getArtDetails")
    private ResponseEntity getArtDetails(@RequestBody AddDetailsGetReq addDetailsGetReq)
    {
        MainResDto mainResDto=new MainResDto();
        AddDetailsMaster addDetailsMaster =new AddDetailsMaster();
        addDetailsMaster = addDetailsMasterService.getArtDetails(addDetailsGetReq);
            return new ResponseEntity(addDetailsMaster, HttpStatus.OK);
    }

    @PostMapping("/checkExistOrNot")
    private ResponseEntity checkExistOrNot(@RequestBody AddDetailsGetReq addDetailsGetReq)
    {
        MainResDto mainResDto=new MainResDto();
       Boolean flag;
               flag= addDetailsMasterService.checkExistOrNot(addDetailsGetReq);

        if(flag)
        {
            mainResDto.setFlag(flag);
            return new ResponseEntity(flag, HttpStatus.OK);
        }else {
            return new ResponseEntity(flag, HttpStatus.OK);
        }
    }

    @GetMapping("/getAllAddDetails")
    public ResponseEntity getAllAddDetails()
    {
        List<AddDetailsMaster> addDetailsMasterList=new ArrayList<>();
        addDetailsMasterList=addDetailsMasterService.getAllAddDetails();
        return new ResponseEntity(addDetailsMasterList,HttpStatus.OK);
    }


    @DeleteMapping("/deleteAddDetails")
    public ResponseEntity  deleteAddDetails(@RequestBody DeleteAddDetailsreq deleteAddDetailsreq)
    {
        MainResDto mainResDto=new MainResDto();
        Boolean flag= addDetailsMasterService.deleteAddDetails(deleteAddDetailsreq);

        if(flag)
        {
            mainResDto.setMessage(" ArtDetails Deleted ");
            mainResDto.setResponseCode(HttpStatus.OK.value());
            mainResDto.setFlag(flag);
            return new ResponseEntity(mainResDto, HttpStatus.OK);
        }else {
            mainResDto.setMessage("ArtDetails Does Not Deleted ");
            mainResDto.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            mainResDto.setFlag(flag);
            return new ResponseEntity(mainResDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}

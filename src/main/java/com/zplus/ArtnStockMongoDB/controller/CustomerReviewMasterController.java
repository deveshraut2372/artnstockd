package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.CustomerReviewMasterReq;
import com.zplus.ArtnStockMongoDB.dto.req.LikeRequestDto;
import com.zplus.ArtnStockMongoDB.dto.req.ReviewReplyRequestDto;
import com.zplus.ArtnStockMongoDB.dto.req.UpdateCustomerReviewMasterReq;
import com.zplus.ArtnStockMongoDB.dto.res.Message;
import com.zplus.ArtnStockMongoDB.model.CustomerReviewMaster;
import com.zplus.ArtnStockMongoDB.service.CustomerReviewMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/customer_review_master")
public class CustomerReviewMasterController {

    @Autowired
    private CustomerReviewMasterService customerReviewMasterService;

    @PostMapping("/create")
    public ResponseEntity createCustomerReviewMaster(@RequestBody CustomerReviewMasterReq customerReviewMasterReq) {
        Boolean flag = customerReviewMasterService.createCustomerReviewMaster(customerReviewMasterReq);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update")
    public ResponseEntity updateCustomerReviewMaster(@RequestBody UpdateCustomerReviewMasterReq updateCustomerReviewMasterReq) {
        Boolean flag = customerReviewMasterService.updateCustomerReviewMaster(updateCustomerReviewMasterReq);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity getAllCustomerReviewMaster() {
        List list = customerReviewMasterService.getAllCustomerReviewMaster();
        return new ResponseEntity(list, HttpStatus.CREATED);
    }
    @GetMapping(value = "/editCustomerReviewMaster/{customerReviewId}")
    public ResponseEntity editCustomerReviewMaster(@PathVariable String customerReviewId)
    {
        CustomerReviewMaster customerReviewMaster = customerReviewMasterService.editCustomerReviewMaster(customerReviewId);
        if(customerReviewMaster!=null)
        {
            return new ResponseEntity(customerReviewMaster, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(customerReviewMaster,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/getActiveCustomerReviewMaster")
    public ResponseEntity getActiveCustomerReviewMaster()
    {
        List list = customerReviewMasterService.getActiveCustomerReviewMaster();
        return new ResponseEntity(list,HttpStatus.OK);
    }

    @GetMapping(value = "/getArtIdWiseCustomerReviewMaster/{artId}")
    public ResponseEntity getArtIdWiseCustomerReviewMaster(@PathVariable String artId)
    {
        List list = customerReviewMasterService.getArtIdWiseCustomerReviewMaster(artId);
        return new ResponseEntity(list,HttpStatus.OK);
    }
    @PutMapping(value = "/addAdminReviewReplyToUser")
    public ResponseEntity addAdminReviewReplyToUser(@RequestBody ReviewReplyRequestDto reviewReplyRequestDto)
    {
        Message message = customerReviewMasterService.addAdminReviewReplyToUser(reviewReplyRequestDto);
        return new ResponseEntity(message,HttpStatus.OK);
    }
    @GetMapping(value = "/todayReviewList")
    public ResponseEntity todayReviewList() {
        List<CustomerReviewMaster> list = customerReviewMasterService.todayReviewList();
        return new ResponseEntity(list, HttpStatus.OK);
    }
    @GetMapping(value = "/YearReviewList/{year}")
    public ResponseEntity YearReviewList(@PathVariable Integer year) {
        List<CustomerReviewMaster> list = customerReviewMasterService.YearReviewList(year);
        return new ResponseEntity(list, HttpStatus.OK);
    }
    @GetMapping(value = "/MonthReviewList/{month}")
    public ResponseEntity MonthReviewList(@PathVariable Integer month) {
        List<CustomerReviewMaster> list = customerReviewMasterService.MonthReviewList(month);
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @PostMapping("/like")
    public ResponseEntity increseLikecount(@RequestBody LikeRequestDto likeRequestDto) {
       Boolean flag = customerReviewMasterService.increseLikecount(likeRequestDto);
        return new ResponseEntity(flag, HttpStatus.OK);
    }

    @PostMapping("/likeminus")
    public ResponseEntity minusLikecount(@RequestBody LikeRequestDto likeRequestDto) {
        Boolean flag = customerReviewMasterService.minusLikecount(likeRequestDto);
        return new ResponseEntity(flag, HttpStatus.OK);
    }

  @PostMapping("/checkUserLike")
  public ResponseEntity checkUserLike(@RequestBody LikeRequestDto likeRequestDto) {
      Boolean flag = customerReviewMasterService.checkUserLike(likeRequestDto);
      if (flag) {
          return new ResponseEntity(flag, HttpStatus.OK);
      }else {
          return new ResponseEntity(flag, HttpStatus.BAD_REQUEST);
      }
  }


//    @GetMapping(value = "/getArtProductIdWiseCustomerReviewMaster/{artProductId}")
//    public ResponseEntity getArtProductIdWiseCustomerReviewMaster(@PathVariable String artProductId)
//    {
//        List list = customerReviewMasterService.getArtProductIdWiseCustomerReviewMaster(artProductId);
//        return new ResponseEntity(list,HttpStatus.OK);
//
//    }
}

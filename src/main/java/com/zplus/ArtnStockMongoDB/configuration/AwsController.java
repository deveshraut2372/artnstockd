//package com.zplus.ArtnStockMongoDB.configuration;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//
//@RestController
//@CrossOrigin(origins = "*")
//public class AwsController {
//    @Autowired
//    private AmazonClient amazonClient;
//
//    public void setAmazonClient(AmazonClient amazonClient)
//    {
//        this.amazonClient = amazonClient;
//    }
//
//    @RequestMapping(value = "/uploadImage",method = RequestMethod.POST)
//    public String uploadNotesCtrl(@RequestParam(value = "uploadImage") MultipartFile file) {
//        String str= this.amazonClient.uploadImages(file);
//        System.out.print("url"+str);
//        return  str;
//    }
//
//
//    @RequestMapping(value = "/uploadServiceImage",method = RequestMethod.POST)
//    public String uploadServiceImagesCtrl(@RequestParam(value = "uploadServiceImage") MultipartFile file) {
//        String str= this.amazonClient.uploadServiceImages(file);
//        System.out.print("url"+str);
//        return  str;
//    }
//
//
//
//}
//

package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.req.DraftDeleteImageReq;
import com.zplus.ArtnStockMongoDB.dto.req.DraftUpdateImageReq;
import com.zplus.ArtnStockMongoDB.dto.req.UpdateImageReq;
import com.zplus.ArtnStockMongoDB.dto.res.MainResDto;
import com.zplus.ArtnStockMongoDB.model.ImageMaster;
import com.zplus.ArtnStockMongoDB.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/images")
@CrossOrigin(origins = "*")
public class ImageController {

    @Autowired
    private ImageService imageService;

    /// This is for update image status in draftMaster
    @PutMapping("/updateImageMaster")
    private ResponseEntity updateImageMaster(@RequestBody DraftUpdateImageReq draftUpdateImageReq)
    {
        MainResDto mainResDto=new MainResDto();
        Boolean flag=imageService.updateImageMaster(draftUpdateImageReq);
        if(flag)
        {
            mainResDto.setFlag(flag);
            mainResDto.setResponseCode(HttpStatus.OK.value());
            mainResDto.setMessage(" Update Image Succesfully ");
            return new ResponseEntity( mainResDto, HttpStatus.OK);
        } else {
            mainResDto.setFlag(flag);
            mainResDto.setResponseCode(HttpStatus.BAD_REQUEST.value());
            mainResDto.setMessage(" Does Not Update Image ");
            return new ResponseEntity( mainResDto, HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping(value = "/deleteImages")
    private ResponseEntity deleteImages(@RequestBody DraftDeleteImageReq draftDeleteImageReq)
    {
        System.out.println(" draftDeleteImageReq= "+draftDeleteImageReq.toString());

        MainResDto mainResDto=new MainResDto();
        Boolean flag=imageService.deleteImages(draftDeleteImageReq);
        if(flag)
        {
            mainResDto.setFlag(flag);
            mainResDto.setResponseCode(HttpStatus.OK.value());
            mainResDto.setMessage(" delete Image Succesfully ");
            return new ResponseEntity( mainResDto, HttpStatus.OK);
        } else {
            mainResDto.setFlag(flag);
            mainResDto.setResponseCode(HttpStatus.BAD_REQUEST.value());
            mainResDto.setMessage(" Does Not Deleted Image ");
            return new ResponseEntity( mainResDto, HttpStatus.BAD_REQUEST);
        }

    }


    @PutMapping(value = "/updateImg")
    private ResponseEntity updateImg(@RequestBody ImageMaster imageMaster)
    {
        Boolean flag=imageService.updateImg(imageMaster);
        if(flag)
            return new ResponseEntity(flag,HttpStatus.OK);
        else
            return new ResponseEntity(flag,HttpStatus.INTERNAL_SERVER_ERROR);
    }


//
//    private final CloudinaryService cloudinaryService;
//
//    @Autowired
//    public ImageController(CloudinaryService cloudinaryService) {
//        this.cloudinaryService = cloudinaryService;
//    }
//
//    @PostMapping("/upload")
//    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
//        try {
//            byte[] imageBytes = file.getBytes();
//            Map<String, Object> uploadResult = cloudinaryService.uploadImage(file);
//            String publicId = (String) uploadResult.get("public_id");
//            Map<String, Object> similarityResult = cloudinaryService.findSimilarImages(publicId);
//
//            // Process the similarity result and extract the relevant information
//            List<Map<String, Object>> resources = (List<Map<String, Object>>) similarityResult.get("resources");
//            List<String> similarImageUrls = new ArrayList<>();
//            for (Map<String, Object> resource : resources) {
//                // Extract the secure URL of each similar image
//                String url = (String) resource.get("secure_url");
//                similarImageUrls.add(url);
//            }
//
//            // Example: Print the similar image URLs
//            for (String url : similarImageUrls) {
//                System.out.println("Similar image URL: " + url);
//            }
//
//            // Return the similar image URLs in the response
//            return ResponseEntity.ok(similarImageUrls);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred during image upload or similarity search.");
//        }
//    }
}



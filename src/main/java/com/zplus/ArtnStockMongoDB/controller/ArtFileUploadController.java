package com.zplus.ArtnStockMongoDB.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.zplus.ArtnStockMongoDB.dao.ArtDimensionDao;
import com.zplus.ArtnStockMongoDB.dao.ImageMasterDao;
import com.zplus.ArtnStockMongoDB.dao.UserDao;
import com.zplus.ArtnStockMongoDB.dto.res.ColorInfo;
import com.zplus.ArtnStockMongoDB.dto.res.ColorResponse;
import com.zplus.ArtnStockMongoDB.model.ArtDimensionMaster;
import com.zplus.ArtnStockMongoDB.model.ImageMaster;
import com.zplus.ArtnStockMongoDB.model.ImageOrientation;
import com.zplus.ArtnStockMongoDB.model.UserMaster;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@CrossOrigin("*")
@RequestMapping("/api")
@RestController
public class ArtFileUploadController {

    private Cloudinary cloudinary;
    @Autowired
    private ImageMasterDao imageMasterDao;
    @Autowired
    private ArtDimensionDao artDimensionDao;
    @Autowired
    private UserDao userDao;

    @Autowired
    public ArtFileUploadController(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @PostMapping("/CloudinaryImageUpload")
    public ResponseEntity uploadCloudinaryImage(@RequestParam MultipartFile file, @RequestParam String userId) {
        try {
            String folderName = "artnstock";
            Map<String, Object> params = ObjectUtils.asMap(
                    "overwrite", true,
                    "folder", folderName,
                    "phash", true
            );


            System.out.println("params: " + params.toString());
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), params);
            String publicId = (String) uploadResult.get("public_id");
            Integer width = (Integer) uploadResult.get("width");
            Integer height = (Integer) uploadResult.get("height");
            String format = (String) uploadResult.get("format");
            String resourceType = (String) uploadResult.get("resource_type");
            String createdAt = (String) uploadResult.get("created_at");
            Integer bytes = (Integer) uploadResult.get("bytes");
            String url = (String) uploadResult.get("url");
            String secureUrl = (String) uploadResult.get("secure_url");
            Integer version = (Integer) uploadResult.get("version");
            String phashId = (String) uploadResult.get("phash");


//           Keyword suggested .................
//           Map<String, Object> result = cloudinary.api().update(publicId, ObjectUtils.asMap(
//                    "categorization", "aws_rek_tagging",
//                    "auto_tagging", 0.9));
//            List<String> tags = (List<String>) result.get("tags");
//            System.out.println("tags: " + tags.toString());
            ImageMaster imageMaster = new ImageMaster();
            ImageMaster savedImageMaster = new ImageMaster();
            ImageOrientation orientation = new ImageOrientation();

            try {
                String squareImage;
                String horizontalImage;
                String verticalImage;
                if (height > width) {
                    squareImage = cloudinary.url()
                            .transformation(new Transformation().aspectRatio("1:1").crop("crop")).generate(publicId);

                    horizontalImage = cloudinary.url()
                            .transformation(new Transformation().aspectRatio("16:9").crop("crop")).generate(publicId);

                    verticalImage = cloudinary.url()
                            .transformation(new Transformation().aspectRatio("9:16").crop("crop")).generate(publicId);

                    imageMaster.setType("Vertical");
                    orientation.setSquareUrl(squareImage);
                    orientation.setVerticalUrl(verticalImage);
                    orientation.setHorizontalUrl(horizontalImage);
                } else if (height < width) {
                    squareImage = cloudinary.url()
                            .transformation(new Transformation().aspectRatio("1:1").crop("crop")).generate(publicId);

                    horizontalImage = cloudinary.url()
                            .transformation(new Transformation().aspectRatio("16:9").crop("crop")).generate(publicId);

                    verticalImage = cloudinary.url()
                            .transformation(new Transformation().aspectRatio("9:16").crop("crop")).generate(publicId);

                    imageMaster.setType("Horizontal");
                    orientation.setSquareUrl(squareImage);
                    orientation.setVerticalUrl(verticalImage);
                    orientation.setHorizontalUrl(horizontalImage);
                } else {
                    squareImage = cloudinary.url()
                            .transformation(new Transformation().aspectRatio("1:1").crop("crop")).generate(publicId);

                    horizontalImage = cloudinary.url()
                            .transformation(new Transformation().aspectRatio("16:9").crop("crop")).generate(publicId);

                    verticalImage = cloudinary.url()
                            .transformation(new Transformation().aspectRatio("9:16").crop("crop")).generate(publicId);

                    imageMaster.setType("Square");
                    orientation.setSquareUrl(squareImage);
                    orientation.setVerticalUrl(verticalImage);
                    orientation.setHorizontalUrl(horizontalImage);
                }
                System.out.println("imageType" + imageMaster.getType());
                List<ArtDimensionMaster> list = artDimensionDao.findAll();
                boolean isWithinArtDimensions = false;
                for (ArtDimensionMaster artDimensionMaster : list) {
                    System.out.println("img" + artDimensionMaster.getDimensionType());
                    if (imageMaster.getType().equals(artDimensionMaster.getDimensionType())) {
                        isWithinArtDimensions = true;
                        double artDimensionHeight = artDimensionMaster.getHeightInPixel();
                        double artDimensionwidth = artDimensionMaster.getWidthInPixel();
                        if (height > artDimensionHeight && width > artDimensionwidth) {
                            // Delete the image from Cloudinary
                            try {
                                cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
                            } catch (IOException e) {
                                e.printStackTrace();
                                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting image: " + e.getMessage());
                            }
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Image height is greater than Art Dimension height");
                        }
                        break;
                    }
                }

                if (!isWithinArtDimensions) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Image type is not within Art Dimension");
                }

                try {
                    Map params2 = ObjectUtils.asMap(
                            "quality_analysis", true,
                            "colors", true
                    );
                    Map<String, Object> temp = cloudinary.api().resource(publicId, params2);
                    List<List<Object>> predominantColorList = (List<List<Object>>) temp.get("colors");
                    System.out.println("predominantColorList: " + predominantColorList.toString());
                    List<Object> formattedColorList = new ArrayList<>();
                    List<ColorInfo> colorInfos = new ArrayList<>();
                    for (int i = 0; i < 1 && i < predominantColorList.size(); i++) {
//                        for (int jj = 0; jj < 10; jj++) {
//                            colorInfos.add(new ColorInfo(String.valueOf(predominantColorList.get(jj).get(0)), Double.valueOf(predominantColorList.get(jj).get(1).toString())));
//                        }
                        for (int jj = 0; jj < predominantColorList.size() && jj < 10; jj++) {
                            colorInfos.add(new ColorInfo(
                                    String.valueOf(predominantColorList.get(jj).get(0)),
                                    Double.valueOf(predominantColorList.get(jj).get(1).toString())
                            ));
                        }
                        Map<String, Object> colorObject = new HashMap<>();

                        formattedColorList.add(colorObject);
                    }
                    try {
                        UserMaster userMaster = userDao.getUserMaster(userId);
                        userMaster.setUserId(userMaster.getUserId());
                        imageMaster.setUserMaster(userMaster);
                        imageMaster.setPublicId(publicId);
                        imageMaster.setWidth(width);
                        imageMaster.setHeight(height);
                        imageMaster.setBytes(bytes);
                        imageMaster.setSecureUrl(secureUrl);
                        imageMaster.setPHashId(phashId);
                        imageMaster.setColorInfos(colorInfos);
                        System.out.println("colorInfos: " + colorInfos.toString());
                        imageMaster.setImageOrientation(orientation);
                        //imageMaster.setSuggestedKeywordList(tags);

                        savedImageMaster = imageMasterDao.save(imageMaster);
                        System.out.println("savedImageMaster: " + savedImageMaster.getUserMaster().getUserId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ColorResponse colorResponse = new ColorResponse();
                    colorResponse.setWidth(width + "px");
                    colorResponse.setHeight(height + "px");
                    colorResponse.setFormat(format);
                    colorResponse.setResourceType(resourceType);
                    colorResponse.setCreatedAt(createdAt);
                    colorResponse.setBytes(bytes * 0.001);
                    colorResponse.setSecureUrl(secureUrl);
                    colorResponse.setOriginalFilename(file.getOriginalFilename());
                    colorResponse.setColorlist(colorInfos);
                    colorResponse.setPublicId(publicId);
                    colorResponse.setPhashId(phashId);
                    colorResponse.setOrientation(orientation);
                    colorResponse.setVersion(version);
                    colorResponse.setImageId(savedImageMaster.getImageId());
                    colorResponse.setType(savedImageMaster.getType());
                    colorResponse.setSuggestedKeywordList(savedImageMaster.getSuggestedKeywordList());

                    return ResponseEntity.ok(colorResponse);
                } catch (IOException e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image: " + e.getMessage());
                }
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image: " + e.getMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image: " + e.getMessage());
        }
    }
}

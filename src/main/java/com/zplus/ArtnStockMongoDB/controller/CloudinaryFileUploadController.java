package com.zplus.ArtnStockMongoDB.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.zplus.ArtnStockMongoDB.dao.ImageMasterDao;
import com.zplus.ArtnStockMongoDB.dto.res.ColorInfo;
import com.zplus.ArtnStockMongoDB.dto.res.ColorResponse;
import com.zplus.ArtnStockMongoDB.dto.res.MainResDto;
import com.zplus.ArtnStockMongoDB.dto.res.PreviewRes;
import com.zplus.ArtnStockMongoDB.model.ImageMaster;
import com.zplus.ArtnStockMongoDB.model.ImageOrientation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;


@RequestMapping
@CrossOrigin("*")
@RestController
public class CloudinaryFileUploadController {

    private final Cloudinary cloudinary;
    @Autowired
    private ImageMasterDao imageMasterDao;

    @Autowired
    public CloudinaryFileUploadController(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @PostMapping("/CloudinaryImageUpload")
    public ResponseEntity uploadCloudinaryImage(@RequestParam ("file")MultipartFile file, @RequestParam Boolean parameter) throws IOException {
      // param is true - load all data
        // param is false - load only image url

        System.out.println(" parameter ="+parameter);

        if(parameter){
        try {
            // Set the folder name for storing the image
            String folderName = "artnstock";

            Map<String, Object> params = ObjectUtils.asMap(
                    "overwrite", true,
                    "folder", folderName,
                    "phash", true
            );

//            Map<String, Object> params = ObjectUtils.asMap("quality", 70);

            System.out.println("params: " + params.toString());

            Double startTime= Double.valueOf(System.currentTimeMillis());
            System.out.println("  Start Time ="+startTime);
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), params);
            System.out.println("  end  Time ="+Double.valueOf(System.currentTimeMillis()));

            Double dtime= Double.valueOf(System.currentTimeMillis())-startTime;
            System.out.println("  dtime ="+dtime);

            String publicId = (String) uploadResult.get("public_id");
            Integer version = (Integer) uploadResult.get("version");
            String signature = (String) uploadResult.get("signature");
            Integer width = (Integer) uploadResult.get("width");
            Integer height = (Integer) uploadResult.get("height");
            String format = (String) uploadResult.get("format");
            String resourceType = (String) uploadResult.get("resource_type");
            String createdAt = (String) uploadResult.get("created_at");
            Integer bytes = (Integer) uploadResult.get("bytes");
            String url = (String) uploadResult.get("url");
            String secureUrl = (String) uploadResult.get("secure_url");
            String phashId = (String) uploadResult.get("phash");

            ImageMaster imageMaster = new ImageMaster();
            ImageMaster savedImageMaster = new ImageMaster();
            ImageOrientation orientation = new ImageOrientation();
            try {
                String squareImage;
                String horizontalImage;
                String verticalImage;
                String squareBCUrl = "";
                String verticalBCUrl;
                String horizontalBCUrl;

                String extension = "." + StringUtils.getFilenameExtension(file.getOriginalFilename());

                String thumbnailurl= cloudinary.url().transformation(new Transformation().height(800).quality("auto:low").density(72)).generate(publicId)+extension;
                imageMaster.setThumbnailUrl(thumbnailurl);

                if(height>width)
                {
                    imageMaster.setType("Vertical");
                }else if(width>height){
                    imageMaster.setType("Horizontal");
                }else {
                    imageMaster.setType("Square");
                }


//                if (extension.equalsIgnoreCase(".jpeg") || extension.equalsIgnoreCase(".eps") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jfif")||extension.equalsIgnoreCase(".png"))
//                {
//                    if (height > width) {
//                        squareImage = cloudinary.url()
//                                .transformation(new Transformation().aspectRatio("1:1").crop("crop")).generate(publicId) + extension;
//                        squareBCUrl = cloudinary.url().
//                                transformation(new Transformation().aspectRatio("1:1").crop("crop").effect("grayscale")).generate(publicId) + extension;
//

//                        horizontalImage = cloudinary.url()
//                                .transformation(new Transformation().aspectRatio("16:9").crop("crop")).generate(publicId) + extension;
//                        horizontalBCUrl = cloudinary.url().transformation(new Transformation().aspectRatio("16:9").crop("crop").effect("grayscale")).generate(publicId) + extension;
//
//                        verticalImage = cloudinary.url()
//                                .transformation(new Transformation().aspectRatio("9:16").crop("crop")).generate(publicId) + extension;
//                        verticalBCUrl = cloudinary.url().transformation(new Transformation().aspectRatio("9:16").crop("crop").effect("grayscale")).generate(publicId) + extension;
//
//                        imageMaster.setType("Vertical");
//                        orientation.setSquareUrl(squareImage);
//                        orientation.setVerticalUrl(verticalImage);
//                        orientation.setHorizontalUrl(horizontalImage);
//                    } else if (height < width) {
//                        squareImage = cloudinary.url()
//                                .transformation(new Transformation().aspectRatio("1:1").crop("crop")).generate(publicId) + extension;
//                        squareBCUrl = cloudinary.url().
//                                transformation(new Transformation().aspectRatio("1:1").crop("crop").effect("grayscale")).generate(publicId) + extension;
//
//
//                        horizontalImage = cloudinary.url()
//                                .transformation(new Transformation().aspectRatio("16:9").crop("crop")).generate(publicId) + extension;
//                        horizontalBCUrl = cloudinary.url().transformation(new Transformation().aspectRatio("16:9").crop("crop").effect("grayscale")).generate(publicId) + extension;
//
//                        verticalImage = cloudinary.url()
//                                .transformation(new Transformation().aspectRatio("9:16").crop("crop")).generate(publicId) + extension;
//                        verticalBCUrl = cloudinary.url().transformation(new Transformation().aspectRatio("9:16").crop("crop").effect("grayscale")).generate(publicId) + extension;
//
//
////                        imageMaster.setThumbnailUrl();
//                        imageMaster.setType("Horizontal");
//                        orientation.setSquareUrl(squareImage);
//                        orientation.setVerticalUrl(verticalImage);
//                        orientation.setHorizontalUrl(horizontalImage);
//                        orientation.setSquareBCUrl(squareBCUrl);
//                        orientation.setHorizontalBCUrl(horizontalBCUrl);
//                        orientation.setVerticalBCUrl(verticalBCUrl);
//                    } else {
//                        squareImage = cloudinary.url()
//                                .transformation(new Transformation().aspectRatio("1:1").crop("crop")).generate(publicId) + extension;
//                        squareBCUrl = cloudinary.url().
//                                transformation(new Transformation().aspectRatio("1:1").crop("crop").effect("grayscale")).generate(publicId) + extension;
//
//                        horizontalImage = cloudinary.url()
//                                .transformation(new Transformation().aspectRatio("16:9").crop("crop")).generate(publicId) + extension;
//                        horizontalBCUrl = cloudinary.url().transformation(new Transformation().aspectRatio("16:9").crop("crop").effect("grayscale")).generate(publicId) + extension;
//
//                        verticalImage = cloudinary.url()
//                                .transformation(new Transformation().aspectRatio("9:16").crop("crop")).generate(publicId) + extension;
//                        verticalBCUrl = cloudinary.url().transformation(new Transformation().aspectRatio("9:16").crop("crop").effect("grayscale")).generate(publicId) + extension;
//
////                        String url = cloudinary.url()
////                                .transformation(new Transformation()
////                                        .aspectRatio("9:16")
////                                        .crop("crop")
////                                        .effect("grayscale")
////                                        .quality("auto:low"))
////                                .generate(publicId) + extension;
//
//
//                        imageMaster.setType("Square");
//                        orientation.setSquareUrl(squareImage);
//                        orientation.setVerticalUrl(verticalImage);
//                        orientation.setHorizontalUrl(horizontalImage);
//                        orientation.setSquareBCUrl(squareBCUrl);
//                        orientation.setHorizontalBCUrl(horizontalBCUrl);
//                        orientation.setVerticalBCUrl(verticalBCUrl);
//                    }
//            }
//                else {
//                     return new ResponseEntity(new MainResDto(false," Please che the format of Files ",HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
//                }

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
//                    for (int i = 0; i < 1 && i < predominantColorList.size(); i++) {
//                        for (int jj = 0; jj < 10; jj++) {
//                            colorInfos.add(new ColorInfo(String.valueOf(predominantColorList.get(jj).get(0)), Double.valueOf(predominantColorList.get(jj).get(1).toString())));
//                        }

//                    Thread thread=new Thread(() ->{
                        for (int jj = 0; jj < predominantColorList.size() && jj < 10; jj++) {
                            colorInfos.add(new ColorInfo(
                                    String.valueOf(predominantColorList.get(jj).get(0)),
                                    Double.valueOf(predominantColorList.get(jj).get(1).toString())
                            ));
                        }
//                    });



                        Map<String, Object> colorObject = new HashMap<>();
                        formattedColorList.add(colorObject);
//                    }
                    try {
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

                        Map<String,Object> preview=new HashMap<>();

                        preview.put("horizontalPreview",new PreviewRes(1.0,0.0,0.0));
                        preview.put("verticalPreview",new PreviewRes(1.0,0.0,0.0));
                        preview.put("squarePreview",new PreviewRes(1.0,0.0,0.0));

                        imageMaster.setPreviews(preview);

                        savedImageMaster = imageMasterDao.save(imageMaster);
                        System.out.println("savedImageMaster: " + savedImageMaster.toString());
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
                    colorResponse.setVersion(version);
                    colorResponse.setOrientation(orientation);
                    colorResponse.setPhashId(phashId);
                    colorResponse.setImageId(savedImageMaster.getImageId());
                    colorResponse.setType(savedImageMaster.getType());
                    colorResponse.setSuggestedKeywordList(savedImageMaster.getSuggestedKeywordList());
                    colorResponse.setThumbnailUrl(thumbnailurl);

                    System.out.println("  orientation s==  "+  orientation.toString());

                    return ResponseEntity.ok(colorResponse);
                } catch (IOException e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image: " + e.getMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image: " + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image: " + e.getMessage());
        }
    }
        else{
            try {
                // Set the folder name for storing the image
                String folderName = "artnstock";

                Map<String, Object> params = ObjectUtils.asMap(
                        "overwrite", true,
                        "folder", folderName,
                        "phash", true
                );
                System.out.println("params: " + params.toString());
                Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), params);

                String url = (String) uploadResult.get("url");
                return ResponseEntity.ok(url);
            }catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image: " + e.getMessage());
            }
        }
    }


    public Map transformImage(String publicId) throws IOException {
        return cloudinary.uploader().explicit(publicId, ObjectUtils.asMap(
                "eager", new Object[] {
                        ObjectUtils.asMap(
                                "width", 800, // Resize the image to 800px width
                                "quality", "auto:low", // Compress the image with low quality
                                "format", "jpg" // Convert to jpg format
                        )
                }
        ));
    }


    public ResponseEntity uploadReferalCloudinaryImage(@RequestParam ("file")MultipartFile file, @RequestParam Boolean parameter) throws IOException {
      // param is true - load all data
        // param is false - load only image url

        System.out.println(" parameter ="+parameter);

        String extensiond= StringUtils.getFilenameExtension(file.getOriginalFilename());
        System.out.println("  extensiond ="+extensiond);
        if(parameter){
        try {
            // Set the folder name for storing the image
            String folderName = "artnstock";

            Map<String, Object> params = ObjectUtils.asMap(
                    "overwrite", true,
                    "folder", folderName,
                    "phash", true
            );

//            Map<String, Object> params = ObjectUtils.asMap("quality", 70);

//            if (file.getSize() > 27) {
//                //do something
//            }

            System.out.println("params: " + params.toString());

            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), params);

            String publicId = (String) uploadResult.get("public_id");
            Integer version = (Integer) uploadResult.get("version");
            String signature = (String) uploadResult.get("signature");
            Integer width = (Integer) uploadResult.get("width");
            Integer height = (Integer) uploadResult.get("height");
            String format = (String) uploadResult.get("format");
            String resourceType = (String) uploadResult.get("resource_type");
            String createdAt = (String) uploadResult.get("created_at");
            Integer bytes = (Integer) uploadResult.get("bytes");
            String url = (String) uploadResult.get("url");
            String secureUrl = (String) uploadResult.get("secure_url");
            String phashId = (String) uploadResult.get("phash");

            ImageMaster imageMaster = new ImageMaster();
            ImageMaster savedImageMaster = new ImageMaster();
            ImageOrientation orientation = new ImageOrientation();
            try {
                String squareImage;
                String horizontalImage;
                String verticalImage;
                String squareBCUrl = "";
                String verticalBCUrl;
                String horizontalBCUrl;

                String extension = "." + StringUtils.getFilenameExtension(file.getOriginalFilename());

                String thumbnailurl= cloudinary.url().transformation(new Transformation().quality("auto:low").density(72)).generate(publicId)+extension;
                imageMaster.setThumbnailUrl(thumbnailurl);

                if (extension.equalsIgnoreCase(".jpeg") || extension.equalsIgnoreCase(".eps") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jfif")||extension.equalsIgnoreCase(".png"))
                {
                    if (height > width) {
                        squareImage = cloudinary.url()
                                .transformation(new Transformation().aspectRatio("1:1").crop("crop")).generate(publicId) + extension;
                        squareBCUrl = cloudinary.url().
                                transformation(new Transformation().aspectRatio("1:1").crop("crop").effect("grayscale")).generate(publicId) + extension;


                        horizontalImage = cloudinary.url()
                                .transformation(new Transformation().aspectRatio("16:9").crop("crop")).generate(publicId) + extension;
                        horizontalBCUrl = cloudinary.url().transformation(new Transformation().aspectRatio("16:9").crop("crop").effect("grayscale")).generate(publicId) + extension;

                        verticalImage = cloudinary.url()
                                .transformation(new Transformation().aspectRatio("9:16").crop("crop")).generate(publicId) + extension;
                        verticalBCUrl = cloudinary.url().transformation(new Transformation().aspectRatio("9:16").crop("crop").effect("grayscale")).generate(publicId) + extension;

                        imageMaster.setType("Vertical");
                        orientation.setSquareUrl(squareImage);
                        orientation.setVerticalUrl(verticalImage);
                        orientation.setHorizontalUrl(horizontalImage);
                    } else if (height < width) {
                        squareImage = cloudinary.url()
                                .transformation(new Transformation().aspectRatio("1:1").crop("crop")).generate(publicId) + extension;
                        squareBCUrl = cloudinary.url().
                                transformation(new Transformation().aspectRatio("1:1").crop("crop").effect("grayscale")).generate(publicId) + extension;


                        horizontalImage = cloudinary.url()
                                .transformation(new Transformation().aspectRatio("16:9").crop("crop")).generate(publicId) + extension;
                        horizontalBCUrl = cloudinary.url().transformation(new Transformation().aspectRatio("16:9").crop("crop").effect("grayscale")).generate(publicId) + extension;

                        verticalImage = cloudinary.url()
                                .transformation(new Transformation().aspectRatio("9:16").crop("crop")).generate(publicId) + extension;
                        verticalBCUrl = cloudinary.url().transformation(new Transformation().aspectRatio("9:16").crop("crop").effect("grayscale")).generate(publicId) + extension;


//                        imageMaster.setThumbnailUrl();
                        imageMaster.setType("Horizontal");
                        orientation.setSquareUrl(squareImage);
                        orientation.setVerticalUrl(verticalImage);
                        orientation.setHorizontalUrl(horizontalImage);
                        orientation.setSquareBCUrl(squareBCUrl);
                        orientation.setHorizontalBCUrl(horizontalBCUrl);
                        orientation.setVerticalBCUrl(verticalBCUrl);
                    } else {
                        squareImage = cloudinary.url()
                                .transformation(new Transformation().aspectRatio("1:1").crop("crop")).generate(publicId) + extension;
                        squareBCUrl = cloudinary.url().
                                transformation(new Transformation().aspectRatio("1:1").crop("crop").effect("grayscale")).generate(publicId) + extension;

                        horizontalImage = cloudinary.url()
                                .transformation(new Transformation().aspectRatio("16:9").crop("crop")).generate(publicId) + extension;
                        horizontalBCUrl = cloudinary.url().transformation(new Transformation().aspectRatio("16:9").crop("crop").effect("grayscale")).generate(publicId) + extension;

                        verticalImage = cloudinary.url()
                                .transformation(new Transformation().aspectRatio("9:16").crop("crop")).generate(publicId) + extension;
                        verticalBCUrl = cloudinary.url().transformation(new Transformation().aspectRatio("9:16").crop("crop").effect("grayscale")).generate(publicId) + extension;

//                        String url = cloudinary.url()
//                                .transformation(new Transformation()
//                                        .aspectRatio("9:16")
//                                        .crop("crop")
//                                        .effect("grayscale")
//                                        .quality("auto:low"))
//                                .generate(publicId) + extension;


                        imageMaster.setType("Square");
                        orientation.setSquareUrl(squareImage);
                        orientation.setVerticalUrl(verticalImage);
                        orientation.setHorizontalUrl(horizontalImage);
                        orientation.setSquareBCUrl(squareBCUrl);
                        orientation.setHorizontalBCUrl(horizontalBCUrl);
                        orientation.setVerticalBCUrl(verticalBCUrl);
                    }
            }
                else {
                     return new ResponseEntity(new MainResDto(false," Please che the format of Files ",HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
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
//                    for (int i = 0; i < 1 && i < predominantColorList.size(); i++) {
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
//                    }
                    try {
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
                        System.out.println("savedImageMaster: " + savedImageMaster.toString());
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
                    colorResponse.setVersion(version);
                    colorResponse.setOrientation(orientation);
                    colorResponse.setPhashId(phashId);
                    colorResponse.setImageId(savedImageMaster.getImageId());
                    colorResponse.setType(savedImageMaster.getType());
                    colorResponse.setSuggestedKeywordList(savedImageMaster.getSuggestedKeywordList());
                    colorResponse.setThumbnailUrl(thumbnailurl);

                    System.out.println("  orientation s==  "+  orientation.toString());

                    return ResponseEntity.ok(colorResponse);
                } catch (IOException e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image: " + e.getMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image: " + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image: " + e.getMessage());
        }
    }
        else{
            try {
                // Set the folder name for storing the image
                String folderName = "artnstock";

                Map<String, Object> params = ObjectUtils.asMap(
                        "overwrite", true,
                        "folder", folderName,
                        "phash", true
                );
                System.out.println("params: " + params.toString());
                Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), params);

                String url = (String) uploadResult.get("url");
                return ResponseEntity.ok(url);
            }catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image: " + e.getMessage());
            }
        }
    }


}



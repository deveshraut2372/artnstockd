package com.zplus.ArtnStockMongoDB.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.zplus.ArtnStockMongoDB.dao.ImageMasterDao;
import com.zplus.ArtnStockMongoDB.dto.res.ColorInfo;
import com.zplus.ArtnStockMongoDB.dto.res.ColorResponse;
import com.zplus.ArtnStockMongoDB.model.ImageMaster;
import com.zplus.ArtnStockMongoDB.model.ImageOrientation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

import org.springframework.data.mongodb.core.MongoTemplate;

@RequestMapping(value = "similar_image")
@CrossOrigin("*")
@RestController
public class SimilarImageController {

    private final Cloudinary cloudinary;
    @Autowired
    private ImageMasterDao imageMasterDao;
    private final MongoTemplate mongoTemplate;


    public SimilarImageController(Cloudinary cloudinary, ImageMasterDao imageMasterDao, MongoTemplate mongoTemplate) {
        this.cloudinary = cloudinary;
        this.imageMasterDao = imageMasterDao;
        this.mongoTemplate = mongoTemplate;
    }


    @PostMapping("/CloudinaryImage")
    public ResponseEntity uploadCloudinaryImage(@RequestParam MultipartFile file, @RequestParam Boolean parameter) throws IOException {

        if (parameter) {
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
        } else {
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
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image: " + e.getMessage());
            }
        }
    }
@GetMapping(value = "/findSimilarImages/{pHashId}")
public ResponseEntity<List<ImageMaster>> findSimilarImages(@PathVariable String pHashId) {
    try {
        BigInteger uploadedHash = new BigInteger(pHashId, 16);

        List<ImageMaster> similarImages = imageMasterDao.findAll();
        System.out.println("similarImages .??????????????" + similarImages.size());

        List<ImageMaster> responseList = new ArrayList<>();

        for (ImageMaster similarImage : similarImages) {
           String storedImageHash = similarImage.getPHashId();
            BigInteger storedHash = new BigInteger(storedImageHash, 16);
            BigInteger xorResult = uploadedHash.xor(storedHash);

            int hammingDistance = xorResult.bitCount();

            double similarityScore = 1.0 - ((double) hammingDistance / 64.0);
            System.out.println("similarityScore.." + similarityScore);
            if (similarityScore > 0.5) {
                responseList.add(similarImage);
            }
        }
        System.out.println("responseList...." + responseList.size());

        return ResponseEntity.ok(responseList);
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>()); // Return an empty list in case of an error
    }
}

    // Helper method to convert ColorInfo to a JSON string
    private String convertColorInfoToString(List<ColorInfo> colorInfos) {
        if (colorInfos != null && !colorInfos.isEmpty()) {
            // ColorInfos is not empty, so convert it to a JSON string
            StringBuilder jsonBuilder = new StringBuilder();
            jsonBuilder.append("[");

            for (ColorInfo colorInfo : colorInfos) {
                jsonBuilder.append("{\"color\": \"").append(colorInfo.getColor()).append("\", \"percentage\": ").append(colorInfo.getPercentage()).append("},");
            }
            // Remove the trailing comma and close the JSON array
            jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
            jsonBuilder.append("]");

            return jsonBuilder.toString();
        } else {
            // ColorInfos is empty, return an empty JSON array
            return "[]";
        }
    }

}


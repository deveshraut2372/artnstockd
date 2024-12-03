//package com.zplus.ArtnStockMongoDB.service.impl;
//import com.cloudinary.Cloudinary;
//import com.cloudinary.utils.ObjectUtils;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class CloudinaryService {
//
//    private final Cloudinary cloudinary;
//
//    public CloudinaryService() {
//        cloudinary = new Cloudinary(ObjectUtils.asMap(
//                "cloud_name", "YOUR_CLOUD_NAME",
//                "api_key", "YOUR_API_KEY",
//                "api_secret", "YOUR_API_SECRET"
//        ));
//    }
//
//
//    public Map<String, Object> findSimilarImages(String publicId) throws IOException {
//        Map<String, Object> options = ObjectUtils.asMap("public_id", publicId, "similarity_search", "true");
//        Map<String, Object> result = cloudinary.api().similarByImage(options);
//
//        List<String> similarImageUrls = new ArrayList<>();
//        if (result.containsKey("similar_images")) {
//            List<Map<String, Object>> similarImages = (List<Map<String, Object>>) result.get("similar_images");
//            for (Map<String, Object> similarImage : similarImages) {
//                String similarImageUrl = (String) similarImage.get("url");
//                similarImageUrls.add(similarImageUrl);
//            }
//        }
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("similarImageUrls", similarImageUrls);
//        return response;
//    }
//
//    // Other methods for image uploading and processing in Cloudinary
//}
//

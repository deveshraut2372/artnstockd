//package com.zplus.ArtnStockMongoDB.configuration;
//
//import com.amazonaws.auth.AWSCredentials;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.model.CannedAccessControlList;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.annotation.PostConstruct;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.Date;
//
///**
// * Created by pramod on 4/11/2018.
// */
//@Service
//class AmazonClient {
//    private AmazonS3 s3client;
//
//    @Value("${cloud.aws.credentials.endpointurl}")
//    private String endpointUrl;
//
//    @Value("${cloud.aws.credentials.bucketName}")
//    private String bucketName;
//
//    @Value("${cloud.aws.credentials.accessKey}")
//    private String accessKey;
//
//    public AmazonS3 getS3client() {
//        return s3client;
//    }
//
//    public void setS3client(AmazonS3 s3client) {
//        this.s3client = s3client;
//    }
//
//    public String getEndpointUrl() {
//        return endpointUrl;
//    }
//
//    public void setEndpointUrl(String endpointUrl) {
//        this.endpointUrl = endpointUrl;
//    }
//
//    public String getBucketName() {
//        return bucketName;
//    }
//
//    public void setBucketName(String bucketName) {
//        this.bucketName = bucketName;
//    }
//
//    public String getAccessKey() {
//        return accessKey;
//    }
//
//    public void setAccessKey(String accessKey) {
//        this.accessKey = accessKey;
//    }
//
//    public String getSecretKey() {
//        return secretKey;
//    }
//
//    public void setSecretKey(String secretKey) {
//        this.secretKey = secretKey;
//    }
//
//    @Value("${cloud.aws.credentials.secretKey}")
//    private String secretKey;
//
//
//
//    @PostConstruct
//    private void initializeAmazon() {
//        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
//        this.s3client = new AmazonS3Client(credentials);
//    }
//
//    private File convertMultiPartToFile(MultipartFile file) throws IOException {
//        File convFile = new File(file.getOriginalFilename());
//        FileOutputStream fos = new FileOutputStream(convFile);
//        fos.write(file.getBytes());
//        fos.close();
//        return convFile;
//    }
//    private String generateFileName(MultipartFile multiPart) {
//        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
//    }
//
//
//    public String uploadImages(MultipartFile multipartFile) {
//        String fileUrl = "";
//        try {
//            File file = convertMultiPartToFile(multipartFile);
//            String fileName = generateFileName(multipartFile);
//            fileUrl = getEndpointUrl() + "/" + bucketName + "/detective/img/" + fileName;
//            uploadImagesTos3bucket(fileName, file);
//            file.delete();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return fileUrl;
//    }
//
//    private void uploadImagesTos3bucket(String fileName, File file) {
//        s3client.putObject(new PutObjectRequest(bucketName+"/detective/img", fileName, file)
//                .withCannedAcl(CannedAccessControlList.PublicRead));
//    }
//
//    public String uploadServiceImages(MultipartFile multipartFile) {
//        String fileUrl = "";
//        try {
//            File file = convertMultiPartToFile(multipartFile);
//            String fileName = generateFileName(multipartFile);
//            fileUrl = getEndpointUrl() + "/" + bucketName + "/detective/ServiceImages/" + fileName;
//            uploadUserProfileImagesTos3bucket(fileName, file);
//            file.delete();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return fileUrl;
//    }
//
//    private void uploadUserProfileImagesTos3bucket(String fileName, File file) {
//        s3client.putObject(new PutObjectRequest(bucketName+"/detective/ServiceImages", fileName, file)
//                .withCannedAcl(CannedAccessControlList.PublicRead));
//    }
//
//
//
//}
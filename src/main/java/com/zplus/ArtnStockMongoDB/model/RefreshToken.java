//package com.zplus.ArtnStockMongoDB.model;
//
//import lombok.Data;
//import org.checkerframework.checker.signedness.qual.Unsigned;
//import org.checkerframework.common.aliasing.qual.Unique;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.DBRef;
//import org.springframework.data.mongodb.core.mapping.Document;
//import org.springframework.data.mongodb.core.mapping.Field;
//
////import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//import java.time.Instant;
//
//@Document(value= "refreshtoken")
//@Data
//public class RefreshToken {
//    @Id
//    private String id;
//
//   @DBRef
//    private UserMaster user;
//
//    @Unique
//    @NotNull(message = "token is compulsory")
//    private String token;
//
//    @NotNull(message = "expiryDate is compulsory")
//    private Instant expiryDate;
//
//    public RefreshToken() {
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public UserMaster getUser() {
//        return user;
//    }
//
//    public void setUser(UserMaster user) {
//        this.user = user;
//    }
//
//    public String getToken() {
//        return token;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }
//
//    public Instant getExpiryDate() {
//        return expiryDate;
//    }
//
//    public void setExpiryDate(Instant expiryDate) {
//        this.expiryDate = expiryDate;
//    }
//
//}

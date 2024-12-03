package com.zplus.ArtnStockMongoDB.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "User_Serve_Master")
public class UserServeMaster {

    @Id
    private String userServeTimeId;
    @DBRef
    private UserMaster userMaster;
    private LocalDateTime loginDate;
    private LocalDateTime logoutDate;


}

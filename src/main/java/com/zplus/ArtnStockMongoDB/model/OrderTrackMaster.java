package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Document(collection = "order_track_master")
@ToString
public class OrderTrackMaster {

    @Id
    private String orderTrackId;
    private String orderTrackUniqueNo;
    private String orderTrackStatus;
    private String orderTrackDescription;
    private LocalDate orderTrackDate;
    private LocalTime orderTrackTime;
    private String orderTrackAddress;

    @DBRef
    private OrderMaster orderMaster;
    @DBRef
    private CartArtFrameMaster cartArtFrameMaster;
}

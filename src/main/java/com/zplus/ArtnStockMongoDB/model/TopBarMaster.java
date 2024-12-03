package com.zplus.ArtnStockMongoDB.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "topbar_master")
public class TopBarMaster {

    @Id
    private String topBarId;

    private String link1;

    private String link2;

    private String link3;

    private String status;

    private Date date;


}

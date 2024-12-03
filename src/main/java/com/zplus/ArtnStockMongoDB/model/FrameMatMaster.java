package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@NoArgsConstructor
@Getter
@Setter
@ToString
@Document(collection = "frame_mat_master")
public class FrameMatMaster {

    @Id
    private String frameMatId;
    private String status;
    private String frameId;
    private Boolean frameFlag;
    private String frameColor;
    private String size;
    private String paper;
    private Orientation orientation;
    private MatWiseMaster matWiseMaster;
    @DBRef
    private UserMaster userMaster;
    @DBRef
    private  ArtMaster artMaster;
}

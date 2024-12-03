package com.zplus.ArtnStockMongoDB.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@Data
@Document(collection = "contributor_art_markup_master")
public class ContributorArtMarkupMaster {

    @Id
    private String contributorArtMarkupId;
    private LocalDate date;
    private List<OrientationMaster> orientationMasters;

    @DBRef
    private ArtMaster artMaster;

}

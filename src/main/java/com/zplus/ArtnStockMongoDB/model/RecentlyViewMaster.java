package com.zplus.ArtnStockMongoDB.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter@Data@ToString
@Document(collection = "recently_view_master")
public class RecentlyViewMaster {
  @Id
  private String recentlyViewId;

  private Date date;

  @DBRef
  private ArtMaster artMaster;

  @DBRef
  private UserMaster userMaster;
}

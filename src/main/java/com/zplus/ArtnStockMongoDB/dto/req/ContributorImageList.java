package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter@Setter@NoArgsConstructor
public class ContributorImageList {
   private List<ContributorImageUploadReq> list;
}

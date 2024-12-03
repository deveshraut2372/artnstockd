package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class UpdateAadharAndSignatureReqdto {
    private String userId;
    private String aadharUpload;
    private String signatureUpload;}

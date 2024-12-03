package com.zplus.ArtnStockMongoDB.dto.req;

import com.zplus.ArtnStockMongoDB.model.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter@Setter
public class UserMasterReq {
    private String userId;
    private String userFirstName;
    private String userLastName;
    private String displayName;
    private String password;
    private String status;
    private Integer otp;
    private List<String> userRole;
    private Integer count;
    private String aadharUpload;
    private String signatureUpload;

    private ShippingAddress shippingAddress;
    private ResidentialAddress residentialAddress;
    private SocialMedia socialMedia;
    private PaymentDetails paymentDetails;
    private Preferences preferences;
    private LanguagePreference languagePreference;
    private DeleteAccount deleteAccount;

    private String profileProgress;
    private Double approvalPercentage;
    private  List<ArtUpload> artUpload;
    private Double markupPer;
    private List<UserWalletPoint> walletPoints;
    private String referenceCode;
    private String profileImage;
    private String coverImage;
    private String userDesignation;
    private String useInfo;
    private List<String> userRecognition;
    private String userDescription;
    private String styles;
    private String subjects;
    private String profession;
    private String equipments;
    private String portfolioUrl;
//    private String contributorTypeId;
    private Map<String,Object> recognizations=new HashMap<>();
    private ContributorTypeMaster contributorTypeMaster;

    private String mobileNo;
    private String buisnessName;
    private String profileUrlName;
    private String userSpecialization;


}

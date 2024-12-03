package com.zplus.ArtnStockMongoDB.model;

import com.zplus.ArtnStockMongoDB.dto.res.Recognization;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Getter
@Setter
@Document(collection = "user_master")
@ToString
@Data
public class UserMaster {

    @Id
    private String userId;
    private String userFirstName;
    private String userLastName;
    private String displayName;
    @Indexed(unique = true)
    private String emailAddress;
    private String userUniqueNo;
//    @Size(max = 8)
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

    private Integer buyCount=0;
    private Boolean termsAndConditions;

    private String customToken;

    private String level;

    private Boolean login=false;

//    @DBRef
//    private Set<Role> roles = new HashSet<>();

    private String jwtToken;

    private LocalDate localDate=LocalDate.now();

    private String styles;
    private String subjects;
    private String profession;
    private  String equipments;

    private Map<String,Object> recognizations=new HashMap<>();

//    @DBRef
    private ContributorTypeMaster contributorTypeMaster=new ContributorTypeMaster();

    private String portfolioUrl;

    private Integer nameCount;

    private String message;

    private String emailChangeStatus="InActive";

    private String mobileNo;

    private String buisnessName;

    private String profileUrlName;

    private String userSpecialization;

    public List<ShippingAddress> shippingAddressList;


}

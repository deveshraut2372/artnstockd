package com.zplus.ArtnStockMongoDB.dto.res;

import com.zplus.ArtnStockMongoDB.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMasterRes {

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

    public UserMasterRes(String userId, String userFirstName, String userLastName, String displayName, String emailAddress, String userUniqueNo, String password, String status, Integer otp, List<String> userRole, Integer count, String aadharUpload, String signatureUpload, ShippingAddress shippingAddress, ResidentialAddress residentialAddress, SocialMedia socialMedia, PaymentDetails paymentDetails, Preferences preferences, LanguagePreference languagePreference, DeleteAccount deleteAccount, String profileProgress, Double approvalPercentage, List<ArtUpload> artUpload, Double markupPer, List<UserWalletPoint> walletPoints, String referenceCode, String profileImage, String coverImage, String userDesignation, String useInfo, List<String> userRecognition, String userDescription, Integer buyCount, Boolean termsAndConditions, String customToken, String level) {
        this.userId = userId;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.displayName = displayName;
        this.emailAddress = emailAddress;
        this.userUniqueNo = userUniqueNo;
        this.password = password;
        this.status = status;
        this.otp = otp;
        this.userRole = userRole;
        this.count = count;
        this.aadharUpload = aadharUpload;
        this.signatureUpload = signatureUpload;
        this.shippingAddress = shippingAddress;
        this.residentialAddress = residentialAddress;
        this.socialMedia = socialMedia;
        this.paymentDetails = paymentDetails;
        this.preferences = preferences;
        this.languagePreference = languagePreference;
        this.deleteAccount = deleteAccount;
        this.profileProgress = profileProgress;
        this.approvalPercentage = approvalPercentage;
        this.artUpload = artUpload;
        this.markupPer = markupPer;
        this.walletPoints = walletPoints;
        this.referenceCode = referenceCode;
        this.profileImage = profileImage;
        this.coverImage = coverImage;
        this.userDesignation = userDesignation;
        this.useInfo = useInfo;
        this.userRecognition = userRecognition;
        this.userDescription = userDescription;
        this.buyCount = buyCount;
        this.termsAndConditions = termsAndConditions;
        this.customToken = customToken;
        this.level = level;
    }
    private Integer profileStrength;

    private String jwtToken;

    private Map<String,Object> recognizations=new HashMap<>();

    private String styles;

    private String subjects;

    private String profession;

    private String portfolioUrl;

    private  String equipments;

    private ContributorTypeMaster contributorTypeMaster;

    private String joinDate;

    private String emailChangeStatus;

    private String mobileNo;

    private String buisnessName;

    private String profileUrlName;

    private String userSpecialization;

    public List<ShippingAddress> shippingAddressList;


}

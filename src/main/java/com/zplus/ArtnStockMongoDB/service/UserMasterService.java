package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.*;
import com.zplus.ArtnStockMongoDB.dto.res.*;
import com.zplus.ArtnStockMongoDB.model.ShippingAddress;
import com.zplus.ArtnStockMongoDB.model.UserMaster;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface UserMasterService {
    CustomerRes saveData(UserMaster userMaster);

    UserMaster verifyUser(String userId, Integer otp);

    UserLoginResDto userLogin(UserLoginReqDto userLoginReqDto);

    UserForgotPassResDto forgotPassword(UserForgotPasswordReqDto userForgotPasswordReqDto) ;

    List getAllUserMasterList();

    Boolean sendMailUser(String userId);

    Boolean updateEmailAddress(String userId, String emailAddress1) throws MessagingException, IOException;

    Boolean sendMailUserMaster(String userId);

    Boolean updateShippingAddressAndResidentialAddress(UpdateShippingReqDto updateShippingAndResidentialAddress);

    Boolean updateResidentialAddress(UpdateResidentialReqdto updateResidentialReqdto);

    Boolean updatePassword(String userId, String password);

    CheckUserStatusResponse getStatusByUserId(String userId);

    UserMasterRes getUserRecord(String userId);

    Boolean updateUserMaster(UserMasterReq userMasterReq);

    DisplayNameResponse getDisplayNameWiseUser(String displayName);

    Boolean getUserIdWiseChangeStatus(String userId);

    Boolean getUserIdWiseMailIdChange(String userId, String emailAddress);

    Boolean UpdateMarkupContributer(UpdateMarkupContributerReqDto updateMarkupContributerReqDto);

    Boolean createDateMasterMaster(DateMasterReqDto dateMasterReqDto);

    Boolean updateAdharAndSignature(UpdateAadharAndSignatureReqdto updateAadharAndSignatureReqdto);

    UserUpdatePasswordResDto updatePasswordUser(UserUpdatePasswordReqDto userUpdatePasswordReqDto);

    Boolean checkTermsAndCondition(String userId);

    Boolean logout(String userId);

    Boolean checkUserLoginStatus(String username);

    Integer getLoginUsersCount();

    Boolean checkJwtExpiredOrNot(JwtReq jwtReq);

    Boolean changePassword(ChangePasswordReq changePasswordReq);

    UserForgotPassResDto forgotPasswordforgotPasswordToken(UserForgotPasswordReqDto userForgotPasswordReqDto);

    Boolean checkTokenValidOrNot(String token);

    Integer CalculateProfileProgress(String userId);

    Boolean changeMailUser(UserChangeEmailReq userChangeEmailReq);

    MainResDto deleteUserByUserId(String userId);

    MainResDto changeUserEmailStatus(String userId);

    UserMaster verifyChangeEmailUser(String id, Integer otp,String newEmailAddress);

    List<ShippingAddress> getShippingAddressByUserId(String userId);

    Boolean createShippingAddress(CreateShippingReqDto createShippingReqDto);

    Boolean UpdateShippingAddress(CreateShippingReqDto createShippingReqDto);

    Boolean deleteShippingAddress(DeleteShippingReqDto deleteShippingReqDto);

    Boolean convertDefaultAddress(DeleteShippingReqDto convertDefaultAddress);

    Boolean updateProfileDetails(PaymentDetailsReq paymentDetailsReq);


    Map<String, String> changeUserMail(String email, String id);

    Map<String, String> verificationOtp(String email, String id, Integer otp);
}

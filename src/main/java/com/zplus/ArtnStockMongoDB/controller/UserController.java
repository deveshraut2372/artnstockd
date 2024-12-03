package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.ExceptionHandling.NoValueFoundException;
import com.zplus.ArtnStockMongoDB.config.security.jwt.JwtUtils;
import com.zplus.ArtnStockMongoDB.dao.*;
import com.zplus.ArtnStockMongoDB.dto.req.*;
import com.zplus.ArtnStockMongoDB.dto.res.*;
import com.zplus.ArtnStockMongoDB.model.PaymentDetails;
import com.zplus.ArtnStockMongoDB.model.ShippingAddress;
import com.zplus.ArtnStockMongoDB.model.UserMaster;
import com.zplus.ArtnStockMongoDB.service.UserMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/user_master")
public class UserController {

    @Autowired
    private UserMasterService userMasterService;
    @Autowired
    private ShippingAddressDao shippingAddressDao;
    @Autowired
    private ResidentialAddressDao residentialAddressDao;
    @Autowired
    private SocialMediaDao socialMediaDao;
    @Autowired
    private PreferencesDao preferencesDao;
    @Autowired
    private PaymentDetailsDao paymentDetailsDao;
    @Autowired
    private LanguagePreferenceDao languagePreferenceDao;
    @Autowired
    private DeleteAccountDao deleteAccountDao;


//    ShippingAddress setType(ShippingAddress shippingAddress)
//    {
//        shippingAddress.setType("Default");
//        return  shippingAddress;
//    }

    @PostMapping("/save")
    public ResponseEntity saveData(@RequestBody UserMaster userMaster) {

//        if (userMaster.getShippingAddress() != null) {
//            ShippingAddress shippingAddress=new ShippingAddress();
//            shippingAddress=shippingAddressDao.save(userMaster.getShippingAddress());
////            userMaster.setShippingAddress(shippingAddress);
//        }

        if (!userMaster.getShippingAddressList().isEmpty()) {
           List<ShippingAddress> shippingAddressList1=new ArrayList<>();
            userMaster.getShippingAddressList().stream().forEach(shippingAddress -> shippingAddress.setType("Default"));
            shippingAddressList1=shippingAddressDao.saveAll(userMaster.getShippingAddressList());
            userMaster.setShippingAddressList(shippingAddressList1);
        }

        if (userMaster.getResidentialAddress() != null) {
            residentialAddressDao.save(userMaster.getResidentialAddress());
        }

        if (userMaster.getPaymentDetails() != null) {
            PaymentDetails paymentDetails=paymentDetailsDao.save(userMaster.getPaymentDetails());
            userMaster.setPaymentDetails(paymentDetails);
        }

        if (userMaster.getDeleteAccount() != null) {
            deleteAccountDao.save(userMaster.getDeleteAccount());
        }

        if (userMaster.getLanguagePreference() != null) {
            languagePreferenceDao.save(userMaster.getLanguagePreference());
        }

        if (userMaster.getPreferences() != null) {
            preferencesDao.save(userMaster.getPreferences());
        }

        if (userMaster.getSocialMedia() != null) {
            socialMediaDao.save(userMaster.getSocialMedia());
        }

        userMaster.setTermsAndConditions(false);

        CustomerRes customerRes = userMasterService.saveData(userMaster);
        return new ResponseEntity(customerRes, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity updateUserMaster(@RequestBody UserMasterReq userMasterReq) {
        Boolean flag = userMasterService.updateUserMaster(userMasterReq);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("verifyUser/{id}/{otp}")
    public ResponseEntity verifyUser(@PathVariable String id, @PathVariable Integer otp) {
        UserMaster userMaster = userMasterService.verifyUser(id, otp);
        return new ResponseEntity(userMaster, HttpStatus.CREATED);
    }

    @GetMapping("/verifyChangeEmailUser/{id}/{otp}/{newEmailAddress}")
    public ResponseEntity verifyChangeEmailUser(@PathVariable String id, @PathVariable Integer otp,@PathVariable String newEmailAddress) {
        UserMaster userMaster = userMasterService.verifyChangeEmailUser(id, otp,newEmailAddress);
        return new ResponseEntity(userMaster, HttpStatus.CREATED);
    }

    @PostMapping(value = "/userLogin")
    public ResponseEntity userLogin(@RequestBody UserLoginReqDto userLoginReqDto) {
        UserLoginResDto customerResDto = userMasterService.userLogin(userLoginReqDto);
        return new ResponseEntity(customerResDto, HttpStatus.OK);
    }

    @PostMapping(value = "/forgotPassword")
    public ResponseEntity forgotPassword(@RequestBody UserForgotPasswordReqDto userForgotPasswordReqDto) {
        UserForgotPassResDto userForgotPassResDto = userMasterService.forgotPassword(userForgotPasswordReqDto);
        return new ResponseEntity(userForgotPassResDto, HttpStatus.OK);
    }

    @PostMapping(value = "/forgotPasswordToken")
    public ResponseEntity forgotPasswordToken(@RequestBody UserForgotPasswordReqDto userForgotPasswordReqDto) throws NoSuchAlgorithmException, IOException, KeyManagementException {
        UserForgotPassResDto userForgotPassResDto = new UserForgotPassResDto();
        userMasterService.forgotPasswordforgotPasswordToken(userForgotPasswordReqDto);
        return new ResponseEntity(userForgotPassResDto, HttpStatus.OK);
    }

    @GetMapping(value = "checkTokenValidOrNot/{token}")
    public ResponseEntity checkTokenValidOrNot(@PathVariable("token") String token)
    {
        Boolean flag=false;
        flag=userMasterService.checkTokenValidOrNot(token);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.OK);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getAllUserMasterList")
    public ResponseEntity getAllUserMasterList() {
        List list = userMasterService.getAllUserMasterList();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("sendVerifyMailUser/{userId}")
    public ResponseEntity sendVerifyMailUser(@PathVariable String userId) throws MessagingException, IOException
    {
        Boolean flag = userMasterService.sendMailUser(userId);
        return new ResponseEntity(flag, HttpStatus.CREATED);
    }

    @PostMapping("/changeMailUser")
    public ResponseEntity changeMailUser(
            @RequestBody UserChangeEmailReq userChangeEmailReq)
//            @PathVariable String userId,@PathVariable String emailAddress)
    {
        Boolean flag = userMasterService.changeMailUser(userChangeEmailReq);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.OK);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/updateEmailAddress/{id}/{emailAddress1}")
    public ResponseEntity updateEmailAddress(@PathVariable String id, @PathVariable String emailAddress1) throws MessagingException, IOException {
        Boolean flag = userMasterService.updateEmailAddress(id, emailAddress1);
        return new ResponseEntity(flag, HttpStatus.OK);
    }

    @GetMapping("sendWelcomeMail/{userId}")
    public ResponseEntity sendWelcomeMail(@PathVariable String userId) throws MessagingException, IOException {
        Boolean flag = userMasterService.sendMailUserMaster(userId);
        return new ResponseEntity(flag, HttpStatus.CREATED);
    }

    @PutMapping(value = "/updateShippingAddress")
    public ResponseEntity updateShippingAddress(@RequestBody UpdateShippingReqDto updateShippingAndResidentialAddress) throws MessagingException, IOException {
        Boolean flag = userMasterService.updateShippingAddressAndResidentialAddress(updateShippingAndResidentialAddress);
        return new ResponseEntity(flag, HttpStatus.OK);
    }

    @PutMapping(value = "/updateResidentialAddress")
    public ResponseEntity updateResidentialAddress(@RequestBody UpdateResidentialReqdto updateResidentialReqdto) throws MessagingException, IOException {
        Boolean flag = userMasterService.updateResidentialAddress(updateResidentialReqdto);
        return new ResponseEntity(flag, HttpStatus.OK);
    }

    @PutMapping(value = "/updatePassword/{userId}/{password}")
    public ResponseEntity updatePassword(@PathVariable String userId, @PathVariable String password) throws MessagingException, IOException {
        Boolean flag = userMasterService.updatePassword(userId, password);
        return new ResponseEntity(flag, HttpStatus.OK);
    }

    @GetMapping(value = "/checkUserStatus/{userId}")
    public ResponseEntity checkUserStatus(@PathVariable String userId) throws MessagingException, IOException {
        CheckUserStatusResponse checkUserStatusResponse = userMasterService.getStatusByUserId(userId);
        return new ResponseEntity(checkUserStatusResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/getUserRecord/{userId}")
    public ResponseEntity getUserRecord(@PathVariable String userId) throws MessagingException, IOException {
        UserMasterRes userMasterRes = new UserMasterRes();
        userMasterRes = userMasterService.getUserRecord(userId);
        return new ResponseEntity(userMasterRes, HttpStatus.OK);
    }

    @GetMapping(value = "/getDisplayNameWiseUser/{displayName}")
    public ResponseEntity getDisplayNameWiseUser(@PathVariable String displayName) throws MessagingException, IOException {
        DisplayNameResponse displayNameResponse = userMasterService.getDisplayNameWiseUser(displayName);
        return new ResponseEntity(displayNameResponse, HttpStatus.OK);
    }

    @PutMapping(value = "/getUserIdWiseChangeStatus/{userId}")
    public ResponseEntity getUserIdWiseChangeStatus(@PathVariable String userId) throws MessagingException, IOException {
        Boolean flag = userMasterService.getUserIdWiseChangeStatus(userId);
        return new ResponseEntity(flag, HttpStatus.OK);
    }

    @GetMapping(value = "/getUserIdWiseMailIdChange/{userId}/{emailAddress}")
    public ResponseEntity getUserIdWiseMailIdChange(@PathVariable String userId, @PathVariable String emailAddress) {
        Boolean flag = userMasterService.getUserIdWiseMailIdChange(userId, emailAddress);
        return new ResponseEntity(flag, HttpStatus.OK);
    }

    @PutMapping(value = "UpdateMarkupContributer")
    public ResponseEntity UpdateMarkupContributer(@RequestBody UpdateMarkupContributerReqDto updateMarkupContributerReqDto) {
        Boolean flag = userMasterService.UpdateMarkupContributer(updateMarkupContributerReqDto);
        return new ResponseEntity(flag, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createDateMasterMaster(@RequestBody DateMasterReqDto dateMasterReqDto) {
        Boolean flag = userMasterService.createDateMasterMaster(dateMasterReqDto);
        if (flag) {
            return new ResponseEntity(flag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(flag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/updateAadharAndSignature")
    public ResponseEntity updateAdharAndSignature(@RequestBody UpdateAadharAndSignatureReqdto updateAadharAndSignatureReqdto) throws MessagingException, IOException {
        Boolean flag = userMasterService.updateAdharAndSignature(updateAadharAndSignatureReqdto);
        return new ResponseEntity(flag, HttpStatus.OK);
    }

    @PutMapping(value = "/updatePasswordUser")
    public ResponseEntity updatePasswordUser(@RequestBody UserUpdatePasswordReqDto userUpdatePasswordReqDto) {
        UserUpdatePasswordResDto userUpdatePasswordResDto = userMasterService.updatePasswordUser(userUpdatePasswordReqDto);
        return new ResponseEntity(userUpdatePasswordResDto, HttpStatus.OK);
    }

    @GetMapping(value = "/checkTermsAndCondition/{userId}")
    public ResponseEntity checkTermsAndCondition(@Param("userId") String userId) {
        Boolean flag;
        flag = userMasterService.checkTermsAndCondition(userId);
        if (flag) {
            return new ResponseEntity("Sucess", HttpStatus.OK);
        } else {
            return new ResponseEntity("Unsucess", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/logout/{userId}")
    public ResponseEntity logout(@PathVariable String userId) {
        Boolean flag;
        System.out.println("  user id ="+userId);
        flag = userMasterService.logout(userId);
        System.out.println("  flag ="+flag);

        if(flag==null)
        {
            return new ResponseEntity("logout", HttpStatus.OK);
        }

        if (flag) {
            return new ResponseEntity("logout", HttpStatus.OK);
        } else {
            return new ResponseEntity("Unsucess", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/checkUserLoginStatus/{username}")
    public ResponseEntity checkUserLoginStatus(@PathVariable String username) {
        Boolean flag;
        flag = userMasterService.checkUserLoginStatus(username);
        return new ResponseEntity(flag, HttpStatus.OK);
    }

    @GetMapping("/getLoginUsersCount")
    public ResponseEntity getLoginUsersCount() {
        Integer cnt = userMasterService.getLoginUsersCount();
        return new ResponseEntity(cnt, HttpStatus.OK);
    }

    @PostMapping("/checkJwtExpiredOrNot")
    public ResponseEntity checkJwtExpiredOrNot(@RequestBody JwtReq jwtReq)
    {
        System.out.println(" JwtReq == "+jwtReq.toString());
    Boolean flag;
    flag=userMasterService.checkJwtExpiredOrNot(jwtReq);

    if(flag)
    {
        return new ResponseEntity(flag, HttpStatus.OK);
    } else {
        return new ResponseEntity(flag, HttpStatus.OK);
    }
    }

    @PostMapping("/changePassword")
    public ResponseEntity changePassword(@RequestBody ChangePasswordReq changePasswordReq)
    {
        Boolean flag=false;
        flag=userMasterService.changePassword(changePasswordReq );
        if(flag)
        {
            return new ResponseEntity(flag, HttpStatus.OK);
        }else{
            return new ResponseEntity(flag, HttpStatus.OK);
        }
    }

    @DeleteMapping("/deleteUserByUserId/{userId}")
    public ResponseEntity deleteUserByUserId(@PathVariable String userId)
    {
        System.out.println("  user id ="+userId);
        MainResDto mainResDto=new MainResDto();
        Boolean flag=false;
        mainResDto=userMasterService.deleteUserByUserId(userId );
        if(mainResDto.getFlag())
        {
            return new ResponseEntity(mainResDto, HttpStatus.OK);
        } else {
            return new ResponseEntity(mainResDto, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/changeUserEmailStatus/{userId}")
    public ResponseEntity changeUserEmailStatus(@PathVariable("userId") String userId)
    {
        System.out.println("  user id ="+userId);
        MainResDto mainResDto=new MainResDto();
        Boolean flag=false;
        mainResDto=userMasterService.changeUserEmailStatus(userId );
        if(mainResDto.getFlag())
        {
            return new ResponseEntity(mainResDto, HttpStatus.OK);
        } else {
            return new ResponseEntity(mainResDto, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getShippingAddressByUserId/{userId}")
    public ResponseEntity getShippingAddressByUserId(@PathVariable String userId)
    {
        List<ShippingAddress> shippingAddressList=new ArrayList<>();
        shippingAddressList=userMasterService.getShippingAddressByUserId(userId);
        return new ResponseEntity(shippingAddressList, HttpStatus.OK);
    }

//    @PostMapping(value = "/ChangeUserEmail")
//    public ResponseEntity changeUserEmail(@RequestBody UserChangeEmailReq userChangeEmailReq)
//    {
//        System.out.println("  user id ="+userId);
//        MainResDto mainResDto=new MainResDto();
//        Boolean flag=false;
//        mainResDto=userMasterService.changeUserEmailStatus(userId );
//        if(mainResDto.getFlag())
//        {
//            return new ResponseEntity(mainResDto, HttpStatus.OK);
//        } else {
//            return new ResponseEntity(mainResDto, HttpStatus.BAD_REQUEST);
//        }
//    }



//    @GetMapping("/CalculateProfileProgress/{userId}")
//    public ResponseEntity<Map> CalculateProfileProgress(@PathVariable("userId") String userId )
//    {
//        Integer profileProgress=0;
//        profileProgress=userMasterService.CalculateProfileProgress(userId );
//        Map<String,String> profileMap=new HashMap<>();
//        profileMap.put("profileProgress",profileProgress.toString());
//            return new ResponseEntity(profileMap, HttpStatus.OK);
//    }



    @PostMapping(value = "/createShippingAddress")
    public ResponseEntity createShippingAddress(@RequestBody CreateShippingReqDto createShippingReqDto) throws MessagingException, IOException {
        Boolean flag = userMasterService.createShippingAddress(createShippingReqDto);
        return new ResponseEntity(flag, HttpStatus.OK);
    }

    @PutMapping(value = "/UpdateShippingAddress1")
    public ResponseEntity UpdateShippingAddress(@RequestBody CreateShippingReqDto createShippingReqDto) throws MessagingException, IOException {
        Boolean flag = userMasterService.UpdateShippingAddress(createShippingReqDto);
        return new ResponseEntity(flag, HttpStatus.OK);
    }

    @PostMapping(value = "/deleteShippingAddress")
    public ResponseEntity deleteShippingAddress(@RequestBody DeleteShippingReqDto deleteShippingReqDto) throws MessagingException, IOException {
        Boolean flag = userMasterService.deleteShippingAddress(deleteShippingReqDto);
        return new ResponseEntity(flag, HttpStatus.OK);
    }

    @PostMapping(value = "/convertDefaultAddress")
    public ResponseEntity convertDefaultAddress(@RequestBody DeleteShippingReqDto convertDefaultAddress) throws MessagingException, IOException {
        Boolean flag = userMasterService.convertDefaultAddress(convertDefaultAddress);
        return new ResponseEntity(flag, HttpStatus.OK);
    }

    @PutMapping(value = "/updatePaymentDetails")
    public ResponseEntity updatePaymentDetails(@RequestBody PaymentDetailsReq paymentDetailsReq) throws MessagingException, IOException {
        Boolean flag = userMasterService.updateProfileDetails(paymentDetailsReq);
        return new ResponseEntity(flag, HttpStatus.OK);
    }


    @GetMapping("/changeUserMail/{email}/{id}")
    public ResponseEntity changeUserMail(@PathVariable("email") String email,@PathVariable("id") String id)
    {
        Map<String,String> message=new HashMap<>();
        message=userMasterService.changeUserMail(email,id);

        if(message.get("flag").equalsIgnoreCase("true"))
        {
            return new ResponseEntity(message,HttpStatus.OK);
        }else {
            return new ResponseEntity(message,HttpStatus.BAD_REQUEST);
        }

    }



    @GetMapping("/verificationOtp/{email}/{id}/{otp}")
    public ResponseEntity verificationOtp(@PathVariable("email") String email,@PathVariable("id") String id,@PathVariable("otp") Integer otp)
    {
        Map<String,String> message=new HashMap<>();
        message=userMasterService.verificationOtp(email,id,otp);
        if(message.get("flag").equalsIgnoreCase("true"))
        {
            return new ResponseEntity(message,HttpStatus.OK);
        }else{
            return new ResponseEntity(message,HttpStatus.BAD_REQUEST);
        }
    }
}








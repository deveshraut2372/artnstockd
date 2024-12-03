package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.config.security.jwt.JwtUtils;
import com.zplus.ArtnStockMongoDB.configuration.RandomNumberGenerator;
import com.zplus.ArtnStockMongoDB.configuration.ReferralCodeGenerator;
import com.zplus.ArtnStockMongoDB.configuration.SendMailComponent;
import com.zplus.ArtnStockMongoDB.configuration.UniqueNumber;
import com.zplus.ArtnStockMongoDB.dao.*;
import com.zplus.ArtnStockMongoDB.dto.req.*;
import com.zplus.ArtnStockMongoDB.dto.res.*;
import com.zplus.ArtnStockMongoDB.model.*;
import com.zplus.ArtnStockMongoDB.service.FileUploadLimitService;
import com.zplus.ArtnStockMongoDB.service.UserMasterService;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

@Service
public class UserMasterServiceImpl implements UserMasterService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ContributorTypeDao contributorTypeDao;

    @Autowired
    private UserMessageDao userMessageDao;


    @Autowired
    private CartDao cartDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private SendMailComponent sendMailComponent;

    @Autowired
    private DateMasterDao dateMasterDao;

    @Autowired
    private UserServeMasterDao userServeMasterDao;

    @Autowired
    private UserServeTimeMasterDao userServeTimeMasterDao;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private FileUploadLimitRepository fileUploadLimitRepository;

    @Autowired
    private FileUploadLimitService fileUploadLimitService;

    @Autowired
    private ArtMasterDao artMasterDao;
    @Autowired
    private ShippingAddressDao shippingAddressDao;

    @Autowired
     private PaymentDetailsDao paymentDetailsDao;



Logger logger= LoggerFactory.getLogger(UserMasterServiceImpl.class);


    @Override
    public CustomerRes saveData(UserMaster userMaster) {

        userMaster.setMarkupPer(5.0);
        userMaster.setLevel("1");
        userMaster.setTermsAndConditions(false);
        UserMaster userMaster1 = userDao.findByEmailAddress(userMaster.getEmailAddress());
        CustomerRes customerRes = new CustomerRes();
        CartMaster cartMaster = new CartMaster();

        if(userDao.existsByDisplayName(userMaster.getDisplayName()))
        {
            customerRes.setMessage("Display Name is already exists");
            customerRes.setFlag(false);
        }else if (userMaster1 == null) {
            customerRes.setMessage("E-Mail is not exists");
            customerRes.setFlag(true);
            customerRes = saveUser(userMaster);
        } else {
            customerRes.setMessage("E-Mail is already exists");
            customerRes.setFlag(false);
        }
        return customerRes;
    }

    public CustomerRes saveUser(UserMaster userMaster) {
        Boolean flag = false;
        CustomerRes customerRes = new CustomerRes();
        UserMaster userMaster1 = new UserMaster();
        userMaster1.setBuyCount(0);
        BeanUtils.copyProperties(userMaster, userMaster1);
        userMaster.setStatus("InProcess");
        userMaster.setTermsAndConditions(false);
        userMaster.setNameCount(0);
        try {
            Integer otp = RandomNumberGenerator.getNumber();
            System.out.println("otp" + otp);
            userMaster.setOtp(otp);
            userMaster.setBuyCount(0);
//           String jwt =jwtUtils.generateJwtToken(userMaster.getEmailAddress());

            String jwt= jwtUtils.generateToken(userMaster.getEmailAddress());
           userMaster.setJwtToken(jwt);
           userMaster.setLocalDate(LocalDate.now());
            userMaster1 = userDao.save(userMaster);

            //
            UserMessageMaster userMessageMaster=new UserMessageMaster();
            userMessageMaster.setUserMaster(userMaster);
            userMessageMaster.setStatus("Active");
            userMessageMaster.setMessage("Increase your discoverability ");
            userMessageDao.save(userMessageMaster);
            //

            Integer profileProgress=0;
            profileProgress=calculateProfileProgress(userMaster);
            userMaster1.setProfileProgress(profileProgress.toString());

            String uid = userMaster1.getUserId();
            Map<String, Object> additionalClaims = new HashMap<String, Object>();
            additionalClaims.put("premiumAccount", true);

            Map<String,Object> recognisation=new HashMap<>();
            recognisation.put("Recognization1",new Recognization("","","De-Activate Recognition"));
            recognisation.put("Recognization2",new Recognization("","","De-Activate Recognition"));
            recognisation.put("Recognization3",new Recognization("","","De-Activate Recognition"));
            userMaster1.setRecognizations(recognisation);

            String customToken=null;

//            String customToken = FirebaseAuth.getInstance()
//                    .createCustomToken(uid, additionalClaims);

            System.out.println("  customToken =="+customToken);
            userMaster1.setCustomToken(customToken);
           UserMaster userMasterd= userDao.save(userMaster1);

            String userRole= userMaster.getUserRole().get(0);

            System.out.println("  User role ="+userRole);
           if(userRole.equalsIgnoreCase("contributor"))
           {
               fileUploadLimitService.createFileUploadLimit(userMaster1.getUserId());

               FileUploadLimitMaster fileUploadLimitMaster=new FileUploadLimitMaster();
               fileUploadLimitMaster.setApprovedPercentage(0.0);
               fileUploadLimitMaster.setUserMaster(userMasterd);
               fileUploadLimitMaster.setLevel(1);

               System.out.println("  contributor ");
                FileUploadLimitMaster fileUploadLimitMaster1= fileUploadLimitRepository.save(fileUploadLimitMaster);
               System.out.println( fileUploadLimitMaster1.toString());
               System.out.println(" FileUpload Limit is Create ");
           }


            System.out.println("userId" + userMaster1.getReferenceCode());
            try {

                CartMaster cartMaster = new CartMaster();

                cartMaster.setCartDate(new Date());
                cartMaster.setTotalQty(0);
                cartMaster.setTotalAmount(0.0);
                cartMaster.setStatus("InActive");
                cartMaster.setDescription("Cart is empty");
                userMaster.setUserId(userMaster1.getUserId());
                System.out.println("userMaster" + userMaster.toString());
                cartMaster.setUserMaster(userMaster);
                CartMaster cartMaster1=cartDao.save(cartMaster);

                System.out.println(" cartMaster1 == is a save  "+cartMaster1.toString());
                flag = true;
            } catch (Exception e) {
                e.printStackTrace();
                flag = false;
            }
            customerRes.setFlag(true);
            customerRes.setMessage("record save  successfully");
            customerRes.setId(userMaster.getUserId());
//
        } catch (Exception e) {
            e.printStackTrace();
            customerRes.setFlag(false);
            customerRes.setMessage("record save not successfully");
            customerRes.setId(userMaster.getUserId());
        }
        String userUniqueNo = UniqueNumber.generateUniqueNumber();
        System.out.println("userUniqueNo: " + userUniqueNo);
        try {
            Optional<UserMaster> optionalUserMaster = userDao.findByUserId(userMaster1.getUserId());
            if (optionalUserMaster.isPresent()) {
                UserMaster master = optionalUserMaster.get();
                master.setUserUniqueNo(userUniqueNo);
                master.setReferenceCode(ReferralCodeGenerator.generateReferralCode());
                UserMaster userMaster2 = userDao.save(master);
                System.out.println("userUniqueNo111: " + userMaster2.getReferenceCode());
                flag = true;
            } else {
                flag = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerRes;
    }

    private Integer calculateProfileProgress(UserMaster userMaster) {

        double cnt=0.0;

        if(userMaster.getUserFirstName()!=null)
        {
            cnt=cnt+5.55;
        }
        if(userMaster.getUserLastName()!=null)
        {
            cnt=cnt+5.55;
        } if(userMaster.getDisplayName()!=null)
        {
            cnt=cnt+5.55;
        }
        if(userMaster.getEmailAddress()!=null)
        {
            cnt=cnt+5.55;
        }
        if(userMaster.getPassword()!=null)
        {
            cnt=cnt+5.55;
        }
        if(userMaster.getAadharUpload()!=null)
        {
            cnt=cnt+5.55;
        }
        if(userMaster.getSignatureUpload()!=null)
        {
            cnt=cnt+5.55;
        }
        if(userMaster.getShippingAddress()!=null)
        {
            cnt=cnt+5.55;
        }
        if(userMaster.getResidentialAddress()!=null)
        {
            cnt=cnt+5.55;
        }
        if(userMaster.getSocialMedia()!=null)
        {
            cnt=cnt+5.55;
        }
        if(userMaster.getPaymentDetails()!=null)
        {
            cnt=cnt+5.55;
        }
        if(userMaster.getProfileImage()!=null)
        {
            cnt=cnt+5.55;
        }
        if(userMaster.getCoverImage()!=null)
        {
            cnt=cnt+5.55;
        }
        if(userMaster.getUserDescription()!=null)
        {
            cnt=cnt+5.55;
        }
        if(userMaster.getUseInfo()!=null)
        {
            cnt=cnt+5.55;
        }
        if(userMaster.getUserRecognition()!=null)
        {
            cnt=cnt+5.55;
        }
        if(userMaster.getUserDesignation()!=null)
        {
            cnt=cnt+5.55;
        }

        Integer profilePercentage=0;
        if(cnt==99)
        {
            profilePercentage=  100;
        }else {
            profilePercentage = (int) cnt;
        }
        userMaster.setProfileProgress(profilePercentage.toString());
        userDao.save(userMaster);

        return profilePercentage;
    }

    @Override
    public UserMaster verifyUser(String id, Integer otp) {
        Boolean flag = false;
        UserMaster userMaster = userDao.getId(id);
        System.out.println("otp........!" + userMaster.getOtp());
        if (userMaster.getOtp().equals(otp)) {

            userMaster.setStatus("Active");
            userDao.save(userMaster);
            flag = true;
        } else {
            flag = false;
        }
        return userMaster;
    }

    @Override
    public UserLoginResDto userLogin(UserLoginReqDto userLoginReqDto) {
        UserLoginResDto userLoginResDto = new UserLoginResDto();

        UserMaster userMaster = userDao.findByEmailAddress(userLoginReqDto.getUserName());
        if (userMaster == null) {
            userLoginResDto.setMessage("Mobile Number or emailId not exists");
            userLoginResDto.setResponseCode(HttpStatus.BAD_REQUEST.value());
            return userLoginResDto;
        }
        if (userMaster.getEmailAddress().equalsIgnoreCase(userLoginReqDto.getUserName())) {
            if (userMaster.getPassword().equals(userLoginReqDto.getPassword())) {
                if (userMaster.getStatus().equals("Active")) {
                    String jwttoken= jwtUtils.generateToken(userLoginReqDto.getUserName());
                    userMaster.setJwtToken(jwttoken);
                    System.out.println(" user s="+userMaster.toString());
                    userDao.save(userMaster);
                    userLoginResDto.setResponseCode(HttpStatus.OK.value());
                    userLoginResDto.setFlag(true);
                    userLoginResDto.setMessage("Login Successfully");
                    userLoginResDto.setUserId(userMaster.getUserId());
                    userLoginResDto.setEmailAddress(userMaster.getEmailAddress());
                    userLoginResDto.setPassword(userMaster.getPassword());
                    userLoginResDto.setStatus(userMaster.getStatus());
                    userLoginResDto.setUserRole(userMaster.getUserRole());
                    userLoginResDto.setShippingAddress(userMaster.getShippingAddress());

                    userLoginResDto.setDisplayName(userMaster.getDisplayName());
                    userLoginResDto.setUserFirstName(userMaster.getUserFirstName());
                    userLoginResDto.setUserLastName(userMaster.getUserLastName());
                    userLoginResDto.setUserUniqueNo(userMaster.getUserUniqueNo());
                    userLoginResDto.setProfileImage(userMaster.getProfileImage());
                    userLoginResDto.setJwtToken(jwttoken);
                    BeanUtils.copyProperties(userMaster, userLoginResDto);

                    // new change
                    if(userMaster.getLogin()) {
                        System.out.println(" user is already login  ");
                    }else
                    {
                        userMaster.setLogin(true);
                        userDao.save(userMaster);
                        UserServeMaster userServeMaster = new UserServeMaster();
//                        List<UserServeMaster> userServeMasterList=userServeMasterList=userServeTimeMasterDao.findAllByUserMaster_UserIdAndLogin(userMaster.getUserId(),true);
//                        userServeMaster=userServeMasterList.get(0);
                        userServeMaster.setLoginDate(LocalDateTime.now());
                        userServeMaster.setUserMaster(userMaster);
//                        userServeMaster.setStatus("Active");
                        System.out.println(" userServeMasterDao is on  ");
                        UserServeMaster userServeMaster1=userServeMasterDao.save(userServeMaster);
                    }
                    return userLoginResDto;
                } else {
                    userLoginResDto.setMessage("Inactive User");
                    userLoginResDto.setResponseCode(HttpStatus.BAD_REQUEST.value());
                    return userLoginResDto;
                }
            } else {
                userLoginResDto.setMessage("Wrong Password");
                userLoginResDto.setResponseCode(HttpStatus.BAD_REQUEST.value());
                return userLoginResDto;
            }
        } else {
            userLoginResDto.setMessage(" EmailId Not Found");
            userLoginResDto.setResponseCode(HttpStatus.BAD_REQUEST.value());
            return userLoginResDto;
        }
    }

    @Override
    public UserForgotPassResDto forgotPassword(UserForgotPasswordReqDto userForgotPasswordReqDto) {
        Boolean flag = false;
        UserForgotPassResDto userForgotPassResDto = new UserForgotPassResDto();



        try {
            UserMaster userMaster = userDao.findAllByEmailAddress(userForgotPasswordReqDto.getEmailAddress());
            Integer customerOTP = RandomNumberGenerator.getNumber();

            Optional<UserMaster> optionalCustomerMaster = userDao.findById(userMaster.getUserId());
            if (optionalCustomerMaster.isPresent()) {
                UserMaster customerMaster1 = optionalCustomerMaster.get();
                customerMaster1.setOtp(customerOTP);
                customerMaster1.setLogin(false);
                userDao.save(customerMaster1);
                flag = true;
            } else {
                flag = false;
            }
//            Integer flag = customerDao.updateOtp(customerMaster.getCustomerId(), customerOTP);

            String content = "Customer \n Your ArtnStock verification OTP code is  " + customerOTP + "   Please DO NOT share this OTP with anyone." +
                    " Forgot password  Link =   http://localhost:3000/forgot-password_change-password/"+userMaster.getUserId()+"/"+customerOTP;
            String senderId = userMaster.getEmailAddress();
            String subject = "ArtnStock : Email Verification OTP";

            System.out.println("http://localhost:3000/forgot-password_change-password/"+userMaster.getUserId()+"/"+customerOTP);

            System.out.println("localhost:3000/forgot-password-change-password/"+userMaster.getUserId()+"/"+customerOTP);
            System.out.println("http://localhost:7171/customer_master/forgotPassword/" + userMaster.getUserId() + "/" + customerOTP);

//            sendMailComponent.sendMail(senderId, content, subject);
            userForgotPassResDto.setStatus(true);
            userForgotPassResDto.setCustomerId(userMaster.getUserId());


            return userForgotPassResDto;
        } catch (Exception e) {
            e.printStackTrace();
            userForgotPassResDto.setStatus(false);
            return userForgotPassResDto;
        }
    }

    @Override
    public List getAllUserMasterList() {
        return userDao.findAll();

    }

    @Override
    public Boolean sendMailUser(String userId) {
        System.out.println("ooooooooooooo" + userId);
        Boolean flag = false;
        try {
            UserMaster userMaster = userDao.getId(userId);
            System.out.println("userMaster...." + userMaster.getEmailAddress());
//                Integer customerOTP = RandomNumberGenerator.getNumber();


            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(userMaster.getEmailAddress());
            helper.setFrom("zpluscybertest@gmail.com");
            helper.setSubject(userMaster.getUserFirstName() + " new contact ");

            URL url = new URL("https://imgartnstock.zplusglobalmarketinsights.com/pdf/index.html");

            //            System.out.println("resource.........." + url);
            InputStream inputStream = url.openStream();
            String htmlMsg;
            htmlMsg = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("htmlMsg" + htmlMsg);

            String nameTag = "<p class='hi'>Hi Azra! Thank you for signing up on Artnstock.</p>";
            String changeNameTag = "<p class='hi'>Hi " + userMaster.getUserFirstName() + "! Thank you for signing up on Artnstock.</p>";
            String tempName = htmlMsg.replace(nameTag, changeNameTag);

            String emailTag = "<span class='orange bot'>ksgrafiks2012@gmail.com</span>";
            String changeEmailTag = "<span class='orange bot'>" + userMaster.getEmailAddress() + "</span>";
            String tempEmail = tempName.replace(emailTag, changeEmailTag);

            String verifyTag = "<a href='' class='btn ve'>Verify Email Address</a>";
            String changeVerifyTag = "<a href='" + "http://artnstock.zplusglobalmarketinsights.com/verify-user/" + userId + "/" + userMaster.getOtp() + "' class='btn ve'>Verify Email Address</a>";
//                String changeVerifyTag="<a class='verify' href='"+"http://localhost:3000/customer-success/"+ userId + "/" + userMaster.getOtp() +"'>Verify Email Address</a>";
            String tempVerify = tempEmail.replace(verifyTag, changeVerifyTag);

            String linkTag = "<a class='orange link'>temp</a>";

            String changeLinkTag = "<a class='orange link'>" + "http://artnstock.zplusglobalmarketinsights.com/verify-user/" + userId + "/" + userMaster.getOtp() + "</a>";
            String tempLink = tempVerify.replace(linkTag, changeLinkTag);

//            System.out.println(temp);
            helper.setText(tempLink, true);


            mailSender.send(mimeMessage);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }


    @Override
    public Boolean changeMailUser(UserChangeEmailReq userChangeEmailReq) {
        System.out.println("ooooooooooooo" + userChangeEmailReq.getUserId());
        Boolean flag = false;
        try {
            UserMaster userMaster = userDao.getId(userChangeEmailReq.getUserId());
            System.out.println("userMaster...." + userMaster.getEmailAddress());
                Integer customerOTP = RandomNumberGenerator.getNumber();
                userMaster.setOtp(customerOTP);
                userDao.save(userMaster);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(userMaster.getEmailAddress());
            helper.setFrom("zpluscybertest@gmail.com");
            helper.setSubject(userMaster.getUserFirstName() + " Change Mail Form Confirmation ");


            URL url = new URL("https://imgartnstock.zplusglobalmarketinsights.com/pdf/mailSend.html");
//            URL url = new URL("https://imgartnstock.zplusglobalmarketinsights.com/pdf/index.html");

            //            System.out.println("resource.........." + url);
            InputStream inputStream = url.openStream();
            String htmlMsg;
            htmlMsg = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("htmlMsg" + htmlMsg);

            String nameTag = "<p class='hi'>Hi Azra! Thank you for signing up on Artnstock.</p>";
            String changeNameTag = "<p class='hi'>Hi " + userMaster.getUserFirstName() + "! Thank you for signing up on Artnstock.</p>";
            String tempName = htmlMsg.replace(nameTag, changeNameTag);

            String emailTag = "<span class='orange bot'>ksgrafiks2012@gmail.com</span>";
            String changeEmailTag = "<span class='orange bot'>" + userMaster.getEmailAddress() + "</span>";
            String tempEmail = tempName.replace(emailTag, changeEmailTag);

            String verifyTag = "<a href='' class='btn ve'>Confirmation Mail</a>";
//            String changeVerify="  <a href="+"http://artnstock.com/contributor"+" class='btn ve'> Confirmation For Mail Address Change </a> ";
            String changeVerifyTag = "<a   style='color:white'  href=' "+"http://artnstock.com/change-email-confirm/"+userMaster.getUserId()+"/"+userMaster.getOtp()+"/"+userChangeEmailReq.getNewEmailAddress()+"' class='btn ve  verify' >Confirmation For Email Address Change</a>";
//                String changeVerifyTag="<a class='verify' href='"+"http://localhost:3000/customer-success/"+ userId + "/" + userMaster.getOtp() +"'>Verify Email Address</a>";
            String tempVerify = tempEmail.replace(verifyTag, changeVerifyTag);

            String linkTag = "<a class='orange link'>temp</a>";

            String changeLinkTag = "<a class='orange link'  >" + "http://artnstock.zplusglobalmarketinsights.com/verify-user/" + userChangeEmailReq.getUserId() + "/" + userMaster.getOtp() + "</a>";
            String tempLink = tempVerify.replace(linkTag, changeLinkTag);

//            System.out.println(temp);
            helper.setText(tempLink, true);

//            helper.setText("  Change Email Address Of "+userMaster.getDisplayName()+"\n " +
//                    " Your Previous Mail Id Is "+userMaster.getEmailAddress()+" \n" +
//                    " This Email Address is Change At "+LocalDate.now()+"\n And New Email Address is "+emailAddress);

            mailSender.send(mimeMessage);
            helper.setTo(userChangeEmailReq.getNewEmailAddress());
            mailSender.send(mimeMessage);
            userMaster.setEmailAddress(userChangeEmailReq.getNewEmailAddress());
            userDao.save(userMaster);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @Override
    public MainResDto deleteUserByUserId(String userId){
        MainResDto mainResDto=new MainResDto();
        try{
            UserMaster userMaster = new UserMaster();
            userMaster = userDao.getId(userId);
            userDao.save(userMaster);

            Query query = new Query();
            query.addCriteria(Criteria.where("userMaster").is(userMaster));

            List<ArtMaster> artMasterList=new ArrayList<>();
            artMasterList=artMasterDao.findAllByUserMaster_UserIdAndStatus(userId,"Approved");
            System.out.println("  artMasterList  1="+artMasterList.size());

            Update update=new Update();
            update.set("status","InActive");
            mongoTemplate.updateMulti(query,update,ArtMaster.class);

            artMasterList=artMasterDao.findAllByUserMaster_UserIdAndStatus(userId,"InActive");
            System.out.println("  artMasterList 2 ="+artMasterList.size());


            mainResDto.setFlag(true);
            mainResDto.setMessage(" SuccesFully Deleted ");
            mainResDto.setResponseCode(HttpStatus.OK.value());
            return mainResDto;
        }catch (Exception e){
            e.printStackTrace();
            mainResDto.setFlag(false);
            mainResDto.setMessage(e.getMessage());
            mainResDto.setResponseCode(HttpStatus.BAD_REQUEST.value());
            return mainResDto;
        }
}

    @Override
    public MainResDto changeUserEmailStatus(String userId) {
        MainResDto mainResDto=new MainResDto();
        try{
            UserMaster userMaster = new UserMaster();
            userMaster = userDao.getId(userId);
            userMaster.setEmailChangeStatus("Active");
            userDao.save(userMaster);
            mainResDto.setFlag(true);
            mainResDto.setMessage(" SuccesFully Change Email Status ");
            mainResDto.setResponseCode(HttpStatus.OK.value());
            return mainResDto;
        }catch (Exception e){
            e.printStackTrace();
            mainResDto.setFlag(false);
            mainResDto.setMessage(e.getMessage());
            mainResDto.setResponseCode(HttpStatus.BAD_REQUEST.value());
            return mainResDto;
        }
    }


    @Override
    public Boolean updateEmailAddress(String userId, String emailAddress1) throws MessagingException, IOException {
        Boolean flag = false;
        Optional<UserMaster> optionalCustomerMaster = userDao.findById(userId);
        if (optionalCustomerMaster.isPresent()) {
            UserMaster userMaster = optionalCustomerMaster.get();
            userMaster.setEmailAddress(emailAddress1);
            userDao.save(userMaster);
            flag = true;

//        Integer emailAddress = customerDao.customerUpdatePassword(customerId ,emailAddress1);
            System.out.println("emailaddress........" + userMaster.getEmailAddress());
            if (userMaster.getEmailAddress() != null) {
                UserMaster userMaster1 = userDao.getUserMaster(userId);
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
                helper.setTo(userMaster1.getEmailAddress());
                System.out.println("email..............." + userMaster1.getEmailAddress());

                helper.setFrom("zpluscybertest@gmail.com");
                helper.setSubject(userMaster1.getUserFirstName() + " new contact ");

//                URL url = new URL("https://zplusglobalmarketinsights.com/artNStockImages/pdf/mailSend.html");
                     URL url = new URL("https://imgartnstock.zplusglobalmarketinsights.com/pdf/index.html");

                InputStream inputStream = url.openStream();
                String htmlMsg = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

//                File resource = new ClassPathResource("https://zplusglobalmarketinsights.com/artNStockImages/pdf/mailSend.html").getFile();
//                System.out.println("resource.........." + resource);
//                String htmlMsg = new String(Files.readAllBytes(resource.toPath()));

                String nameTag = "<p class='hi'>Hi Azra! Thank you for signing up on Artnstock.</p>";
                String changeNameTag = "<p class='hi'>Hi " + userMaster.getUserFirstName() + "! Thank you for signing up on Artnstock.</p>";
                String tempName = htmlMsg.replace(nameTag, changeNameTag);

                String emailTag = "<span class='orange bot'>ksgrafiks2012@gmail.com</span>";
                String changeEmailTag = "<span class='orange bot'>" + userMaster.getEmailAddress() + "</span>";
                String tempEmail = tempName.replace(emailTag, changeEmailTag);

                String verifyTag = "<a href='' class='btn ve'>Verify Email Address</a>";
                String changeVerifyTag = "<a href='" + "http://artnstock.zplusglobalmarketinsights.com/#/user-welcomeuser/" + userId + "/" + userMaster.getOtp() + "' class='btn ve'>Verify Email Address</a>";
                String tempVerify = tempEmail.replace(verifyTag, changeVerifyTag);

                String linkTag = "<a class='orange link'>temp</a>";
                String changeLinkTag = "<a class='orange link'>" + "http://artnstock.zplusglobalmarketinsights.com/#/user-welcomeuser/" + userId + "/" + userMaster.getOtp() + "</a>";
                String tempLink = tempVerify.replace(linkTag, changeLinkTag);

                helper.setText(tempLink, true);

                mailSender.send(mimeMessage);
                flag = true;
            }

        } else {
            flag = false;
        }
        return flag;
    }


//
//    public  String convertInputStreamToString(InputStream inputStream) throws IOException {
//        byte[] bytes = new byte[1024]; // You can adjust the buffer size as needed
//        StringBuilder stringBuilder = new StringBuilder();
//        int bytesRead;
//        while ((bytesRead = inputStream.read(bytes)) != -1) {
//            stringBuilder.append(new String(bytes, 0, bytesRead, StandardCharsets.UTF_8));
//        }
//        return stringBuilder.toString();
//    }




    @Override
    public Boolean sendMailUserMaster(String userId) {
        Boolean flag = false;
        try {
            UserMaster userMaster = userDao.getId(userId);
            System.out.println("usermaster" + userMaster.toString());

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(userMaster.getEmailAddress());
            helper.setFrom("zpluscybertest@gmail.com");
            helper.setSubject(userMaster.getUserFirstName() + " new contact ");

//            URL url = new URL("https://zplusglobalmarketinsights.com/artNStockImages/pdf/successMail.html");
            URL url = new URL("https://imgartnstock.zplusglobalmarketinsights.com/pdf/index.html");
            InputStream inputStream = url.openStream();
            String htmlMsg = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            String aTag = "<p class='name'>Dear, Khalid</p>";
            String changeTag = "<p class='name'>Dear, " + userMaster.getUserFirstName() + "</p>";

            String temp = htmlMsg.replace(aTag, changeTag);
            String spanTag = "<span class='orange'>ksgrafiks2012@gmail.com</span>";
            String changeSpanTag = "<span class='orange'>" + userMaster.getEmailAddress() + "</span>";
            String newTemp = temp.replace(spanTag, changeSpanTag);
            helper.setText(newTemp, true);


            mailSender.send(mimeMessage);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;


    }

    @Override
    public Boolean updateShippingAddressAndResidentialAddress(UpdateShippingReqDto updateShippingAndResidentialAddress) {
        Boolean flag = false;
        Optional<UserMaster> optionalUserMaster = userDao.findByUserId(updateShippingAndResidentialAddress.getUserId());
        if (optionalUserMaster.isPresent()) {
            UserMaster userMaster = optionalUserMaster.get();
            if(!updateShippingAndResidentialAddress.getShippingAddressList().isEmpty())
            {
                userMaster.setShippingAddressList(updateShippingAndResidentialAddress.getShippingAddressList());
            }
            userMaster.setShippingAddress(updateShippingAndResidentialAddress.getShippingAddress());

            userDao.save(userMaster);
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }


    @Override
    public Boolean updateResidentialAddress(UpdateResidentialReqdto updateResidentialReqdto) {
        Boolean flag = false;
        Optional<UserMaster> optionalUserMaster = userDao.findByUserId((updateResidentialReqdto.getUserId()));
        if (optionalUserMaster.isPresent()) {
            UserMaster userMaster = optionalUserMaster.get();
            userMaster.setResidentialAddress(updateResidentialReqdto.getResidentialAddress());
            userDao.save(userMaster);
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    @Override
    public Boolean updatePassword(String userId, String password) {
        Boolean flag = false;
        Optional<UserMaster> optionalUserMaster = userDao.findByUserId(userId);
        if (optionalUserMaster.isPresent()) {
            UserMaster userMaster = optionalUserMaster.get();
            userMaster.setPassword(password);
            userDao.save(userMaster);
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    @Override
    public CheckUserStatusResponse getStatusByUserId(String userId) {
//        UserMaster userMaster = new UserMaster();
        CheckUserStatusResponse checkUserStatusResponse = new CheckUserStatusResponse();
        Optional<UserMaster> userMaster = userDao.findByUserId(userId);
        checkUserStatusResponse.setStatus(userMaster.get().getStatus());
        checkUserStatusResponse.setUserRole(userMaster.get().getUserRole());

        checkUserStatusResponse.setResponseCode(HttpStatus.OK.value());
        checkUserStatusResponse.setFlag(true);
        checkUserStatusResponse.setMessage("Record getSuccessfully");
        checkUserStatusResponse.setUserId(userMaster.get().getUserId());
        checkUserStatusResponse.setEmailAddress(userMaster.get().getEmailAddress());
        checkUserStatusResponse.setPassword(userMaster.get().getPassword());
        checkUserStatusResponse.setStatus(userMaster.get().getStatus());
        checkUserStatusResponse.setUserRole(userMaster.get().getUserRole());
        checkUserStatusResponse.setShippingAddress(userMaster.get().getShippingAddress());
        checkUserStatusResponse.setDisplayName(userMaster.get().getDisplayName());
        checkUserStatusResponse.setUserFirstName(userMaster.get().getUserFirstName());
        checkUserStatusResponse.setUserLastName(userMaster.get().getUserLastName());
        checkUserStatusResponse.setUserUniqueNo(userMaster.get().getUserUniqueNo());
        return checkUserStatusResponse;
    }

    @Override
    public UserMasterRes getUserRecord(String userId) {
        UserMasterRes userMasterRes=new UserMasterRes();

        UserMaster userMaster = userDao.getUserMaster(userId);
        BeanUtils.copyProperties(userMaster,userMasterRes);

//        Map<String ,Object> user= (Map<String, Object>) userMaster;
//        List<UserMaster> collect = user.values()
//                .stream()
//                .filter(e -> e == null)
//                .collect(Collectors.toList());
//        System.out.println(collect);

//        int totalkeys=user.get()

        LocalDate localDate=userMaster.getLocalDate();

        String month =localDate.getMonth().toString().toLowerCase();
        month=month.substring(0,3).toUpperCase();
//        month=month.substring(0,1).toUpperCase()+month.substring(1);

        String joinDate=localDate.getDayOfMonth()+" "+month+" "+localDate.getYear();
        System.out.println("  join Date ="+joinDate);
        userMasterRes.setJoinDate(joinDate);
        userMasterRes.setProfileStrength(70);
//        userMasterRes.setShippingAddressList();
        return userMasterRes;
    }

    public Boolean updateUserMaster(UserMasterReq userMasterReq) {
        try {
            UserMaster userMaster = userDao.getUserMaster(userMasterReq.getUserId());

            if(userMasterReq.getContributorTypeMaster()!=null)
            {
                ContributorTypeMaster contributorTypeMaster=new ContributorTypeMaster();
                contributorTypeMaster=contributorTypeDao.findById(userMasterReq.getContributorTypeMaster().getContributorTypeId()).get();
                userMaster.setContributorTypeMaster(contributorTypeMaster);
            }


            if (userMaster == null) {
                return false;
            }

            String displayName=userMaster.getDisplayName();


            // Get the fields and their values from the UserMasterReq object
            Field[] fields = UserMasterReq.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(userMasterReq);
                if (value != null) {
                    Field userField = UserMaster.class.getDeclaredField(field.getName());
                    userField.setAccessible(true);
                    userField.set(userMaster, value);
                }
            }


            System.out.println(" nname count ="+userMaster.getNameCount());
         if(userMaster.getNameCount()==0 || userMaster.getNameCount().equals(null))
         {
             userMaster.setDisplayName(userMasterReq.getDisplayName());
             userMaster.setNameCount(1);
         }else {
             userMaster.setDisplayName(displayName);
         }

            userMaster.setRecognizations(userMasterReq.getRecognizations());
            userMaster.setProfileProgress(userMasterReq.getProfileProgress());
            userMaster.setPortfolioUrl(userMasterReq.getPortfolioUrl());
            userMaster.setStyles(userMasterReq.getStyles());
            userMaster.setSubjects(userMasterReq.getSubjects());
            userMaster.setProfession(userMasterReq.getProfession());
            userMaster.setEquipments(userMasterReq.getEquipments());
            userMaster.setMobileNo(userMasterReq.getMobileNo());
            userMaster.setBuisnessName(userMasterReq.getBuisnessName());

            UserMaster um=userDao.save(userMaster);
            System.out.println("um"+um.toString());
            return true;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public DisplayNameResponse getDisplayNameWiseUser(String displayName) {
        DisplayNameResponse displayNameResponse = new DisplayNameResponse();
        UserMaster userMaster = userDao.getDisplayNameWiseUser(displayName);
        System.out.println("displayname" + userMaster);
        if (userMaster == null) {
            displayNameResponse.setMsg("display name does not get");
            displayNameResponse.setFlag(false);
        } else {
            displayNameResponse.setMsg("display name find Successully");
            displayNameResponse.setFlag(true);
        }
        return displayNameResponse;
    }

    @Override
    public Boolean getUserIdWiseChangeStatus(String userId) {
        Boolean flag = false;
        Optional<UserMaster> optionalUserMaster = userDao.findByUserId(userId);
        if (optionalUserMaster.isPresent()) {
            UserMaster userMaster = optionalUserMaster.get();
            userMaster.setStatus("DeActivated");
            userDao.save(userMaster);
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    @Override
    public Boolean getUserIdWiseMailIdChange(String userId, String emailAddress) {
        Boolean flag = false;
        Optional<UserMaster> optionalUserMaster = userDao.findByUserId(userId);
        if (optionalUserMaster.isPresent()) {
            UserMaster userMaster = optionalUserMaster.get();
            userMaster.setEmailAddress(emailAddress);
            userDao.save(userMaster);
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    @Override
    public Boolean UpdateMarkupContributer(UpdateMarkupContributerReqDto updateMarkupContributerReqDto) {
        Boolean flag = false;
        UserMaster userMaster = userDao.getId(updateMarkupContributerReqDto.getUserId());
        if (userMaster != null) {
            if (userMaster.getUserRole().contains("Contributor")) {

                Optional<UserMaster> optionalUserMaster = userDao.findByUserId(updateMarkupContributerReqDto.getUserId());
                if (optionalUserMaster.isPresent()) {
                    userMaster = optionalUserMaster.get();
                    userMaster.setMarkupPer(updateMarkupContributerReqDto.getMarkupPer());
                    UserMaster userMaster1 = userDao.save(userMaster);
                    flag = true;
                } else {
                    flag = false;
                }

            }
        }

//            if (userMaster.getUserRole().equals("Contributor")) {
//                try {
//                    Optional<UserMaster> optionalUserMaster = userDao.findByUserId(updateMarkupContributerReqDto.getUserId());
//                    if (optionalUserMaster.isPresent()) {
//                        UserMaster userMaster1 = optionalUserMaster.get();
//                        userMaster1.setMarkupPer(updateMarkupContributerReqDto.getMarkupPer());
//                        UserMaster userMaster2 = userDao.save(userMaster1);
//                        System.out.println("userMaster..." + userMaster2.toString());
//
//                        flag = true;
//                    } else {
//                        flag = false;
//                    }
//                }catch (Exception e)
//                {
//                    e.printStackTrace();
//                    flag=false;
//                }
//            }

        return flag;


    }

    @Override
    public Boolean createDateMasterMaster(DateMasterReqDto dateMasterReqDto) {
        DateMaster dateMaster = new DateMaster();

        UserMaster userMaster = new UserMaster();
        userMaster.setUserId(dateMasterReqDto.getUserId());
//        dateMaster.setDate(new Date());
        dateMaster.setUserMaster(userMaster);
        BeanUtils.copyProperties(dateMasterReqDto, dateMaster);
        try {
            dateMasterDao.save(dateMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateAdharAndSignature(UpdateAadharAndSignatureReqdto updateAadharAndSignatureReqdto) {
        Boolean flag = false;
        Optional<UserMaster> optionalUserMaster = userDao.findByUserId(updateAadharAndSignatureReqdto.getUserId());
        if (optionalUserMaster.isPresent()) {
            UserMaster userMaster = optionalUserMaster.get();
            userMaster.setAadharUpload(updateAadharAndSignatureReqdto.getAadharUpload());
            userMaster.setSignatureUpload(updateAadharAndSignatureReqdto.getSignatureUpload());
            userDao.save(userMaster);
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    @Override
    public UserUpdatePasswordResDto updatePasswordUser(UserUpdatePasswordReqDto userUpdatePasswordReqDto) {
        UserMaster userMaster = userDao.getUserMaster((userUpdatePasswordReqDto.getUserId()));
        UserUpdatePasswordResDto customerUpdatePasswordResDto = new UserUpdatePasswordResDto();
        if (userMaster.getPassword().equals(userUpdatePasswordReqDto.getPassword())) {
            if (userMaster.getPassword().equals(userUpdatePasswordReqDto.getNewPassword())) {
                customerUpdatePasswordResDto.setMsg("password update successfully");
                customerUpdatePasswordResDto.setResponseCode(HttpStatus.OK.value());
                return customerUpdatePasswordResDto;
            } else if (userUpdatePasswordReqDto.getNewPassword().equals(userUpdatePasswordReqDto.getConfirmPassword())) {
                userMaster.setPassword(userUpdatePasswordReqDto.getNewPassword());
                userDao.save(userMaster);
                System.out.println(userMaster);
                customerUpdatePasswordResDto.setMsg("Password updated Successfully..!");
                customerUpdatePasswordResDto.setResponseCode(HttpStatus.OK.value());
                customerUpdatePasswordResDto.setFlag(true);
                customerUpdatePasswordResDto.setUserId(userMaster.getUserId());
                customerUpdatePasswordResDto.setEmailAddress(userMaster.getEmailAddress());
                return customerUpdatePasswordResDto;
            } else {
                customerUpdatePasswordResDto.setMsg("password not mach..!");
                customerUpdatePasswordResDto.setFlag(false);
                customerUpdatePasswordResDto.setUserId(userMaster.getUserId());
                customerUpdatePasswordResDto.setEmailAddress(userMaster.getEmailAddress());
                customerUpdatePasswordResDto.setResponseCode(HttpStatus.BAD_REQUEST.value());

                return customerUpdatePasswordResDto;
            }
        } else {
            customerUpdatePasswordResDto.setMsg("password not mach..!");
            customerUpdatePasswordResDto.setFlag(false);
            customerUpdatePasswordResDto.setUserId(userMaster.getUserId());
            customerUpdatePasswordResDto.setEmailAddress(userMaster.getEmailAddress());
            customerUpdatePasswordResDto.setResponseCode(HttpStatus.BAD_REQUEST.value());

            return customerUpdatePasswordResDto;
        }
    }

    @Override
    public Boolean checkTermsAndCondition(String userId) {
        System.out.println(  "id ="+ userId);
        try {
            UserMaster userMaster = new UserMaster();
            userMaster = userDao.findById(userId).get();
            userMaster.setTermsAndConditions(false);
            userDao.save(userMaster);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Async
    public Boolean logout(String userId) {

        try {
            UserMaster userMaster=userDao.findByUserId(userId).get();
            if(!userMaster.getLogin())
            {
                return true;
            }
            userMaster.setLogin(false);
            userDao.save(userMaster);
            UserServeMaster userServeMaster = new UserServeMaster();
//        userServeTimeMaster=userServeTimeMasterDao.findByUserMaster_userId(userId);
            List<UserServeMaster> userServeMasterList = userServeMasterDao.findAllByUserMaster_UserId(userId);
            System.out.println("Size ="+userServeMasterList.size());
            //           userServeMaster=userServeMasterList.stream().findFirst().get();

            if(userServeMasterList.isEmpty()) {
               return true;
            }
                userServeMaster = userServeMasterList.get(0);
                LocalDateTime logoutDate = LocalDateTime.now();
            Duration  duration = Duration.between(userServeMaster.getLoginDate(), logoutDate);

            UserServeTimeMaster userServeTimeMaster = new UserServeTimeMaster();
            userServeTimeMaster.setLoginDate(userServeMaster.getLoginDate());
            userServeTimeMaster.setLogoutDate(LocalDateTime.now());
            userServeTimeMaster.setHours(duration.toHours() + "");
            userServeTimeMaster.setMinutes(duration.toMinutes() + "");
            userServeTimeMaster.setSeconds(duration.getSeconds() + "");
            userServeTimeMaster.setUserMaster(userServeMaster.getUserMaster());

            userServeTimeMasterDao.save(userServeTimeMaster);
            userServeMasterDao.delete(userServeMaster);

            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean checkUserLoginStatus(String username) {
        UserMaster userMaster=new UserMaster();
        userMaster=userDao.findByEmailAddress(username);
        if(userMaster==null)
        {
            return false;
        }else {
            return userMaster.getLogin();
        }
    }

    @Override
    public Integer getLoginUsersCount() {
        List list=userDao.getcountsByLogin(true);

        return list.size();
    }

    @Override
    public Boolean checkJwtExpiredOrNot(JwtReq jwtReq) {
        UserMaster userMaster=userDao.findByEmailAddress(jwtReq.getEmailAddress());

        if(jwtUtils.validateToken(jwtReq.getJwtToken()))
        {
            String name =jwtUtils.getUserNameFromJwtToken1(jwtReq.getJwtToken());
            System.out.println("  name ="+name);
            System.out.println("  request name ="+jwtReq.getEmailAddress());
        if(name.equalsIgnoreCase(jwtReq.getEmailAddress())){
            return true;
        }else
            return false;
        }else{
            userMaster.setLogin(false);
            userDao.save(userMaster);
            return false;
//            throw new RuntimeException(" Jwt Token is Expired Please Login again");
        }
    }

    @Override
    public Boolean changePassword(ChangePasswordReq changePasswordReq) {
        try
        {
            boolean flag=false;

            System.out.println(" changePasswordReq ==  "+changePasswordReq.toString());
            UserMaster userMaster=new UserMaster();
            userMaster=userDao.getUserMaster(changePasswordReq.getUserId());

            System.out.println("  User Master "+userMaster.getUserId());
            System.out.println("  User Master "+userMaster.getPassword());
            System.out.println("  User Master "+userMaster.getEmailAddress());
            flag=logout(changePasswordReq.getUserId());

            userMaster.setPassword(changePasswordReq.getPassword());
            userMaster.setLogin(false);
            userDao.save(userMaster);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public UserForgotPassResDto forgotPasswordforgotPasswordToken(UserForgotPasswordReqDto userForgotPasswordReqDto) {
        Boolean flag = false;
        UserForgotPassResDto userForgotPassResDto = new UserForgotPassResDto();
        try {
            UserMaster userMaster=new UserMaster();
            if(userDao.existsByEmailAddress(userForgotPasswordReqDto.getEmailAddress())) {
                 userMaster = userDao.findAllByEmailAddress(userForgotPasswordReqDto.getEmailAddress());
            }

            Integer customerOTP = RandomNumberGenerator.getNumber();

            Optional<UserMaster> optionalCustomerMaster = userDao.findById(userMaster.getUserId());
            if (optionalCustomerMaster.isPresent()) {
                UserMaster customerMaster1 = optionalCustomerMaster.get();
                customerMaster1.setOtp(customerOTP);
                customerMaster1.setLogin(false);
                userDao.save(customerMaster1);
                flag = true;
            } else {
                flag = false;
            }
//            Integer flag = customerDao.updateOtp(customerMaster.getCustomerId(), customerOTP);

            String token =jwtUtils.generateToken(userForgotPasswordReqDto.getEmailAddress());

            String content = "Customer \n Your ArtnStock verification OTP code is  " + customerOTP + "   Please DO NOT share this OTP with anyone." +
                    " Forgot password  Link =   http://localhost:3000/forgot-password_change-password/"+token;
            String senderId = userMaster.getEmailAddress();
            String subject = "ArtnStock : Email Verification OTP";

            System.out.println("http://localhost:3000/forgot-password_change-password/"+userMaster.getUserId()+"/"+token);

            System.out.println("http://localhost:3000/forgot-password_change-password/"+userMaster.getUserId()+"/"+token);

//            sendMailComponent.sendMail(senderId, content, subject);
            userForgotPassResDto.setStatus(true);
            userForgotPassResDto.setCustomerId(userMaster.getUserId());
            return userForgotPassResDto;
        } catch (Exception e) {
            e.printStackTrace();
            userForgotPassResDto.setStatus(false);
            return userForgotPassResDto;
        }
    }

    @Override
    public Boolean checkTokenValidOrNot(String token) {
        return jwtUtils.validateToken(token);
    }

    @Override
    public Integer CalculateProfileProgress(String userId) {
        UserMaster userMaster=new UserMaster();
        userMaster=userDao.getUserMaster(userId);
       return calculateProfileProgress(userMaster);
    }

    public Boolean getData(String userId) throws IOException, ParseException {
        Boolean flag = false;
        Date date = new Date();
        SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd");
        String stringDate1 = sd1.format(date);
        List<DateMaster> dateMasterList = dateMasterDao.findByUserMasterUserId(userId);

        for (DateMaster dateMaster : dateMasterList) {
            if (dateMaster.getDate().equals(sd1.parse(stringDate1))) {
                Integer cnt = dateMaster.getCount() + 1;
                dateMaster.setCount(cnt);
                dateMasterDao.save(dateMaster);
                System.out.println("dateMaster1" + dateMaster.toString());
                flag = true;
            }
        }
        DateMaster dateMaster2 = dateMasterDao.findByDateAndUserMasterUserId(stringDate1, userId);
        if (dateMaster2 == null) {
            DateMaster dateMaster3 = new DateMaster();
            dateMaster3.setCount(1);
            dateMaster3.setDate(sd1.parse(stringDate1));
            UserMaster userMaster1 = new UserMaster();
            userMaster1.setUserId(userId);
            dateMaster3.setUserMaster(userMaster1);
            dateMasterDao.save(dateMaster3);
            flag = true;
        }
        return flag;
    }


    @Override
    public UserMaster verifyChangeEmailUser(String id, Integer otp,String newEmailAddress) {
        UserMaster userMaster=new UserMaster();
        userMaster=userDao.getUserMaster(id);
            if (userMaster.getOtp().equals(otp)) {
                userMaster.setEmailAddress(newEmailAddress);
                userMaster.setEmailChangeStatus("InActive");
                userMaster.setOtp(null);
                userDao.save(userMaster);
            }
            return userMaster;
    }

    @Override
    public List<ShippingAddress> getShippingAddressByUserId(String userId) {
        UserMaster userMaster=new UserMaster();
        userMaster=userDao.getUserMaster(userId);
        List<ShippingAddress> shippingAddressList=new ArrayList<>();
        System.out.println("  shipping Address ="+shippingAddressList.size());
        if(!userMaster.getShippingAddressList().isEmpty() || userMaster.getShippingAddressList()!=null)
        {
            shippingAddressList=userMaster.getShippingAddressList();
        }
        return shippingAddressList;
    }

    @Override
    public Boolean createShippingAddress(CreateShippingReqDto createShippingReqDto) {

        try
        {
        UserMaster userMaster=new UserMaster();
        userMaster= userDao.findByUserId(createShippingReqDto.getUserId()).get();

        if(userMaster.getShippingAddressList().isEmpty())
        {
            List<ShippingAddress> shippingAddressList=new ArrayList<>();
           ShippingAddress shippingAddress=new ShippingAddress();
           shippingAddress=shippingAddressDao.save(createShippingReqDto.getShippingAddress());
           shippingAddressList.add(shippingAddress) ;
           userMaster.setShippingAddressList(shippingAddressList);
           userDao.save(userMaster);
           return true;
        }else if(!userMaster.getShippingAddressList().isEmpty()){
            List<ShippingAddress> shippingAddressList1=new ArrayList<>();
            shippingAddressList1=userMaster.getShippingAddressList();
            ShippingAddress shippingAddress=new ShippingAddress();
            shippingAddress=shippingAddressDao.save(createShippingReqDto.getShippingAddress());
            shippingAddressList1.add(shippingAddress);
            userMaster.setShippingAddressList(shippingAddressList1);
            userDao.save(userMaster);

            if(shippingAddress.getDefaultType())
            {
                Boolean f;
             DeleteShippingReqDto deleteShippingReqDto=new DeleteShippingReqDto();
             deleteShippingReqDto.setUserId(userMaster.getUserId());
             deleteShippingReqDto.setShippingAddressId(shippingAddress.getShippingAddressId());
               f=convertDefaultAddress(deleteShippingReqDto);
               return  f;
            }

            return true;
        }else {
            return  false;
        }
        }catch ( Exception e)
        {
            logger.info(" Error ="+e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean UpdateShippingAddress(CreateShippingReqDto createShippingReqDto) {

        try
        {
            UserMaster userMaster=new UserMaster();
            userMaster= userDao.findByUserId(createShippingReqDto.getUserId()).get();

            List<ShippingAddress> shippingAddressList=userMaster.getShippingAddressList();

                ShippingAddress shippingAddress=new ShippingAddress();
                BeanUtils.copyProperties(createShippingReqDto.getShippingAddress(),shippingAddress);
                ShippingAddress shippingAddress1=shippingAddressDao.save(shippingAddress);

            shippingAddressList.removeIf(shippingAddress2 -> shippingAddress.getShippingAddressId().equalsIgnoreCase(shippingAddress2.getShippingAddressId()));
            shippingAddressList.add(shippingAddress1);

                userMaster.setShippingAddressList(shippingAddressList);
                userDao.save(userMaster);

            if(shippingAddress1.getDefaultType())
            {
                Boolean f;
                DeleteShippingReqDto deleteShippingReqDto=new DeleteShippingReqDto();
                deleteShippingReqDto.setUserId(userMaster.getUserId());
                deleteShippingReqDto.setShippingAddressId(shippingAddress1.getShippingAddressId());
                f=convertDefaultAddress(deleteShippingReqDto);
                return  f;
            }

                return true;

        }catch ( Exception e)
        {
            logger.info(" Error ="+e.getMessage());
            return false;
        }

    }

    @Override
    public Boolean deleteShippingAddress(DeleteShippingReqDto deleteShippingReqDto) {
        try
        {
            UserMaster userMaster=new UserMaster();
            userMaster= userDao.findByUserId(deleteShippingReqDto.getUserId()).get();

            List<ShippingAddress> shippingAddressList=userMaster.getShippingAddressList();

            shippingAddressList.removeIf(shippingAddress2 -> deleteShippingReqDto.getShippingAddressId().equalsIgnoreCase(shippingAddress2.getShippingAddressId()));
            userMaster.setShippingAddressList(shippingAddressList);
            userDao.save(userMaster);
            return true;

        }catch ( Exception e)
        {
            logger.info(" Error ="+e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean convertDefaultAddress(DeleteShippingReqDto convertDefaultAddress) {


        try {
            UserMaster userMaster = new UserMaster();
            Optional<UserMaster> userMasterOption = userDao.findByUserId(convertDefaultAddress.getUserId());
            userMasterOption.ifPresent(setting -> BeanUtils.copyProperties(setting, userMaster));

            ShippingAddress shippingAddress = shippingAddressDao.findById(convertDefaultAddress.getShippingAddressId()).orElse(null);
            shippingAddress.setDefaultType(true);

            List<ShippingAddress> shippingAddressList = new ArrayList<>();
            shippingAddressList = userMaster.getShippingAddressList();
            shippingAddressList.stream().parallel().forEach(shippingAddress1 -> {
                if (shippingAddress1.getShippingAddressId().equalsIgnoreCase(convertDefaultAddress.getShippingAddressId())) {
                    shippingAddress1.setDefaultType(true);
                    shippingAddressDao.save(shippingAddress1);
                } else {
                    shippingAddress1.setDefaultType(false);
                    shippingAddressDao.save(shippingAddress1);
                }
            });

            Collections.sort(shippingAddressList,Comparator.comparing(ShippingAddress::getDefaultType).reversed());

            userMaster.setShippingAddressList(shippingAddressList);
            userDao.save(userMaster);

            return true;
        }catch (Exception e)
        {
         e.printStackTrace();
         return false;
        }
    }

//    @Override
//    public Boolean updateProfileDetails(PaymentDetailsReq paymentDetailsReq) {
//        return null;
//    }


    @Override
    public Boolean updateProfileDetails(PaymentDetailsReq paymentDetailsReq){
        try
        {
            UserMaster userMaster=new UserMaster();
            userMaster=userDao.getUserMaster(paymentDetailsReq.getUserId());
            PaymentDetails paymentDetails=new PaymentDetails();
            BeanUtils.copyProperties(paymentDetailsReq,paymentDetails);
            PaymentDetails paymentDetails1=paymentDetailsDao.save(paymentDetails);
            userMaster.setPaymentDetails(paymentDetails1);
            userDao.save(userMaster);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Map<String, String> changeUserMail(String email, String id) {
        Map<String,String> message=new HashMap<>();
        UserMaster userMaster=userDao.findByUserId(id).orElse(null);
        if(userMaster!=null) {
            try {
                Random random=new Random();
                Integer otp=RandomNumberGenerator.getNumber();
                userMaster.setOtp(otp);
                userDao.save(userMaster);

                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                simpleMailMessage.setSubject(" Verification Opt For Change Email ");
                simpleMailMessage.setTo(email);
                simpleMailMessage.setFrom("apurv.patole@zpluscybertech.com");
                simpleMailMessage.setText("" +
                        "We received a request to change the email address associated with your account. To confirm this request, please use the following One-Time Password (OTP):\n" +
                        " "+otp+" \n" +
                        "This OTP is valid for the next 10 minutes. If you did not request this change, please ignore this email. Your email address will remain unchanged.\n" +
                        "If you need any assistance or did not make this request, please contact our support team immediately.\n" +
                        "Thank you, ");

                mailSender.send(simpleMailMessage);
                message.put("flag", "false");
                message.put("responseCode", "200");
                message.put("message", "Generated Opt ");
                return message;
            } catch (Exception e) {
                e.printStackTrace();
                message.put("flag", "false");
                message.put("responseCode", "400");
                message.put("message", e.getMessage());
                return message;
            }
        } else
        {
            message.put("flag", "false");
            message.put("responseCode", "400");
            message.put("message","  User Not Found ");
            return message;
        }
    }

    @Override
    public Map<String, String> verificationOtp(String email, String id, Integer otp) {
        Map<String,String> message=new HashMap<>();
        UserMaster userMaster=userDao.findByUserId(id).orElse(null);

        System.out.println(" otp ="+userMaster.getOtp());
        System.out.println(" otp v ="+otp);
        if(userMaster!=null) {
            try {
                if(userMaster.getOtp().equals(otp))
                {
                    userMaster.setEmailAddress(email);
                    userDao.save(userMaster);
                    message.put("flag", "true");
                    message.put("responseCode", "200");
                    message.put("message", "OTP Verifired ");
                    return message;
                }else{
                    message.put("flag", "false");
                    message.put("responseCode", "400");
                    message.put("message", " OTP is incorrect try again ");
                    return message;
                }
            }catch (Exception e) {
                e.printStackTrace();
                message.put("flag", "false");
                message.put("responseCode", "400");
                message.put("message", e.getMessage());
                return message;
            }
        }else{
            message.put("flag", "false");
            message.put("responseCode", "400");
            message.put("message","  User Not Found ");
            return message;
        }
    }


//    @Override
//    public UserMaster getByUserId(String userId) {
//        return userDao.findByUserId(userId).get();
//    }

}



package com.zplus.ArtnStockMongoDB.configuration.FileHandling;

import com.zplus.ArtnStockMongoDB.dao.UserDao;
import com.zplus.ArtnStockMongoDB.model.UserMaster;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@CrossOrigin(origins = "*")
public class FileHandlingController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping(value = "/uploadfiles",method = RequestMethod.POST)
    public ResponseEntity fileHandelDemo(@RequestParam(value = "uploadfile") MultipartFile file) throws Exception
    {

        System.out.println("  size of file ="+file.getSize()/1024/1024);
        long filesize=file.getSize()/1024/1024;
        if(filesize>2)
        {
            return new ResponseEntity(" File Size Should be a 2 MB Or Less !", HttpStatus.INTERNAL_SERVER_ERROR);
//          throw new RuntimeException(" File Size Should be a 2 MB Or Less !");
        }

        String s = file.getOriginalFilename();
        String extension = s.substring(s.lastIndexOf(".") + 1);

        if (extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("gif") || extension.equalsIgnoreCase("jpeg")||extension.equalsIgnoreCase("pdf")) {
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            FileUploadResDto fileUploadResDto = new FileUploadResDto();
            File convFile = new File(file.getOriginalFilename());
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();

            String host = "artnstock.com";
            int port = 21;
            String user = "nx7h06oc0r17";
            String pwd="Bmjg!CfO6n8S";
//            String pwd = "Techno@zplus11";

            FTPClient ftpClient = new FTPClient();
            try {
                ftpClient.connect(host, port);
                ftpClient.login(user, pwd);
                ftpClient.enterLocalPassiveMode();

                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                File sourceFile = new File(filename);
                InputStream inputStream = new FileInputStream(convFile);

                boolean done = ftpClient.storeFile("public_html/files.artnstock.com/artnstockReleses/" + filename, inputStream);
                inputStream.close();
                if (done) {
                    System.out.println("file is uploaded successfully..............");
                }
            } catch (IOException e) {
                System.out.println("Exception occured while ftp : " + e);
            } finally {
                try {
                    if (ftpClient.isConnected()) {
                        ftpClient.logout();
                        ftpClient.disconnect();
                    }
                } catch (IOException e) {
                    System.out.println("Exception occured while ftp logout/disconnect : " + e);
                }
            }

            File deleteFile = new File(filename);

            if (deleteFile.delete()) {
                System.out.println("File deleted successfully");
            } else {
                System.out.println("Failed to delete the file");
            }
            fileUploadResDto.setPath("http://files.artnstock.com/artnstockReleses/"+filename);
            fileUploadResDto.setStatus(true);
            return new ResponseEntity(fileUploadResDto, HttpStatus.OK);
        }
        else
        {
            FileUploadResDto fileUploadResDto = new FileUploadResDto();
            fileUploadResDto.setStatus(false);
            fileUploadResDto.setPath("Upload Image Only");
            return new ResponseEntity(fileUploadResDto, HttpStatus.OK);
        }
    }


    @RequestMapping(value = "/uploadProfile",method = RequestMethod.POST)
    public ResponseEntity fileHandelProfileDemo(@RequestParam(value = "uploadfile") MultipartFile file) throws Exception {

        System.out.println("  size of file ="+file.getSize()/1024/1024);
        long filesize=file.getSize()/1024/1024;
        if(filesize>5)
        {
            return new ResponseEntity(" File Size Should be a 5 MB Or Less !", HttpStatus.INTERNAL_SERVER_ERROR);
//          throw new RuntimeException(" File Size Should be a 2 MB Or Less !");
        }

        String s = file.getOriginalFilename();
        String extension = s.substring(s.lastIndexOf(".") + 1);

        if (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg")) {
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            FileUploadResDto fileUploadResDto = new FileUploadResDto();
            File convFile = new File(file.getOriginalFilename());
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();

            String host = "artnstock.com";
            int port = 21;
            String user = "nx7h06oc0r17";
            String pwd="Bmjg!CfO6n8S";
//            String pwd = "Techno@zplus11";

            FTPClient ftpClient = new FTPClient();
            try {
                ftpClient.connect(host, port);
                ftpClient.login(user, pwd);
                ftpClient.enterLocalPassiveMode();

                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                File sourceFile = new File(filename);
                InputStream inputStream = new FileInputStream(convFile);

                boolean done = ftpClient.storeFile("public_html/files.artnstock.com/artnstockReleses/" + filename, inputStream);
                inputStream.close();
                if (done) {
                    System.out.println("file is uploaded successfully..............");
                }
            } catch (IOException e) {
                System.out.println("Exception occured while ftp : " + e);
            } finally {
                try {
                    if (ftpClient.isConnected()) {
                        ftpClient.logout();
                        ftpClient.disconnect();
                    }
                } catch (IOException e) {
                    System.out.println("Exception occured while ftp logout/disconnect : " + e);
                }
            }

            File deleteFile = new File(filename);

            if (deleteFile.delete()) {
                System.out.println("File deleted successfully");
            } else {
                System.out.println("Failed to delete the file");
            }
            fileUploadResDto.setPath("http://files.artnstock.com/artnstockReleses/"+filename);
            fileUploadResDto.setStatus(true);
            return new ResponseEntity(fileUploadResDto, HttpStatus.OK);
        }
        else
        {
            FileUploadResDto fileUploadResDto = new FileUploadResDto();
            fileUploadResDto.setStatus(false);
            fileUploadResDto.setPath("Upload Image Only  in format jpg and jpeg ");
            return new ResponseEntity(fileUploadResDto, HttpStatus.OK);
        }
    }


    @RequestMapping(value = "/uploadSignatureUpload",method = RequestMethod.POST)
    public ResponseEntity uploadSignatureUpload(@RequestParam(value = "uploadfile") MultipartFile file,@RequestParam(value = "userId") String userId) throws Exception {

        UserMaster userMaster=new UserMaster();
        userMaster=userDao.findById(userId).get();

        System.out.println("  size of file ="+file.getSize()/1024/1024);
        long filesize=file.getSize()/1024/1024;
        if(filesize>2)
        {
            return new ResponseEntity(" File Size Should be a 2 MB Or Less !", HttpStatus.INTERNAL_SERVER_ERROR);
//          throw new RuntimeException(" File Size Should be a 2 MB Or Less !");
        }

        String s = file.getOriginalFilename();
        String extension = s.substring(s.lastIndexOf(".") + 1);

        if (extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("pdf")) {
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            FileUploadResDto fileUploadResDto = new FileUploadResDto();
            File convFile = new File(file.getOriginalFilename());
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();

            String host = "artnstock.com";
            int port = 21;
            String user = "nx7h06oc0r17";
            String pwd="Bmjg!CfO6n8S";
//            String pwd = "Techno@zplus11";

            FTPClient ftpClient = new FTPClient();

            try {
                ftpClient.connect(host, port);
                ftpClient.login(user, pwd);
                ftpClient.enterLocalPassiveMode();

                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                File sourceFile = new File(filename);
                InputStream inputStream = new FileInputStream(convFile);

                boolean done = ftpClient.storeFile("public_html/files.artnstock.com/artnstockReleses/" + filename, inputStream);
                inputStream.close();
                if (done) {
                    System.out.println("file is uploaded successfully..............");
                }
            } catch (IOException e) {
                System.out.println("Exception occured while ftp : " + e);
            } finally {
                try {
                    if (ftpClient.isConnected()) {
                        ftpClient.logout();
                        ftpClient.disconnect();
                    }
                } catch (IOException e) {
                    System.out.println("Exception occured while ftp logout/disconnect : " + e);
                }
            }

            File deleteFile = new File(filename);

            if (deleteFile.delete()) {
                System.out.println("File deleted successfully");
            } else {
                System.out.println("Failed to delete the file");
            }
            fileUploadResDto.setPath("http://files.artnstock.com/artnstockReleses/"+filename);
            fileUploadResDto.setStatus(true);
            return new ResponseEntity(fileUploadResDto, HttpStatus.OK);
        }
        else
        {
            FileUploadResDto fileUploadResDto = new FileUploadResDto();
            fileUploadResDto.setStatus(false);
            fileUploadResDto.setPath("Upload Image Only  PNG or PDF file ");
            return new ResponseEntity(fileUploadResDto, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/uploadAadharUpload",method = RequestMethod.POST)
    public ResponseEntity uploadAadharUpload(@RequestParam(value = "uploadfile") MultipartFile file,@RequestParam(value = "userId") String userId) throws Exception {

        UserMaster userMaster=new UserMaster();
        userMaster=userDao.findById(userId).get();

        System.out.println("  size of file ="+file.getSize()/1024/1024);
        long filesize=file.getSize()/1024/1024;
        if(filesize>1)
        {
            return new ResponseEntity(" File Size Should be a 1 MB Or Less !", HttpStatus.INTERNAL_SERVER_ERROR);
//          throw new RuntimeException(" File Size Should be a 2 MB Or Less !");
        }

        String s = file.getOriginalFilename();
        String extension = s.substring(s.lastIndexOf(".") + 1);

        if (extension.equalsIgnoreCase("jpeg") || extension.equalsIgnoreCase("tiff")|| extension.equalsIgnoreCase("pdf")) {
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            FileUploadResDto fileUploadResDto = new FileUploadResDto();
            File convFile = new File(file.getOriginalFilename());
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();

            String host = "artnstock.com";
            int port = 21;
            String user = "nx7h06oc0r17";
            String pwd="Bmjg!CfO6n8S";
//            String pwd = "Techno@zplus11";

            FTPClient ftpClient = new FTPClient();
            try {
                ftpClient.connect(host, port);
                ftpClient.login(user, pwd);
                ftpClient.enterLocalPassiveMode();

                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                File sourceFile = new File(filename);
                InputStream inputStream = new FileInputStream(convFile);

                boolean done = ftpClient.storeFile("public_html/files.artnstock.com/artnstockReleses/" + filename, inputStream);
                inputStream.close();
                if (done) {
                    System.out.println("file is uploaded successfully..............");
                }
            } catch (IOException e) {
                System.out.println("Exception occured while ftp : " + e);
            } finally {
                try {
                    if (ftpClient.isConnected()) {
                        ftpClient.logout();
                        ftpClient.disconnect();
                    }
                } catch (IOException e) {
                    System.out.println("Exception occured while ftp logout/disconnect : " + e);
                }
            }

            File deleteFile = new File(filename);

            if (deleteFile.delete()) {
                System.out.println("File deleted successfully");
            } else {
                System.out.println("Failed to delete the file");
            }
            fileUploadResDto.setPath("http://files.artnstock.com/artnstockReleses/"+filename);
            fileUploadResDto.setStatus(true);
            return new ResponseEntity(fileUploadResDto, HttpStatus.OK);
        }
        else
        {
            FileUploadResDto fileUploadResDto = new FileUploadResDto();
            fileUploadResDto.setStatus(false);
            fileUploadResDto.setPath("Upload Image Only  JPEG, TIFF or PDF file ");
            return new ResponseEntity(fileUploadResDto, HttpStatus.OK);
        }
    }
}

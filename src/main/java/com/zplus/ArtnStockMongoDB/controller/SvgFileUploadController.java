package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dto.res.FileUploadResDto;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@CrossOrigin(origins = "*")
public class SvgFileUploadController {

    @RequestMapping(value = "/uploadfile",method = RequestMethod.POST)

    public ResponseEntity fileHandelDemo(@RequestParam(value = "uploadfiledata") MultipartFile file) throws Exception {

        String s = file.getOriginalFilename();
        String extension = s.substring(s.lastIndexOf(".") + 1);

        if (extension.equalsIgnoreCase("svg")) {
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            FileUploadResDto fileUploadResDto = new FileUploadResDto();
            File convFile = new File(file.getOriginalFilename());
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();

            String host = "zplusglobalmarketinsights.com";
            int port = 21;
            String user = "kab8f0gfd7hd";
            String pwd = "Zplus@123";

            FTPClient ftpClient = new FTPClient();
            try {
                ftpClient.connect(host, port);
                ftpClient.login(user, pwd);
                ftpClient.enterLocalPassiveMode();

                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                File sourceFile = new File(filename);
                InputStream inputStream = new FileInputStream(convFile);

                boolean done = ftpClient.storeFile("public_html/artnstocksvgimg/" + filename, inputStream);
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
            fileUploadResDto.setPath("https://zplusglobalmarketinsights.com/artnstocksvgimg/"+filename);
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
}

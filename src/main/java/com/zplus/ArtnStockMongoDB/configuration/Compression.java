package com.zplus.ArtnStockMongoDB.configuration;

import org.springframework.beans.factory.annotation.Value;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class Compression {
    @Value("${endpointUrl}")
    private String endpointUrl;
    @Value("${bucketName}")
    private String bucketName;
    @Value("${accessKey}")
    private String accessKey;
    @Value("${secretKey}")
    private String secretKey;
    @Value("${storedUrl}")
    private String storedUrl;
    @Value("${https}")
    private String https;

    public void usingMath() throws IOException {
        File originalImage = new File("C:\\Users\\Admin\\Desktop\\download (1).jpg");

        File compressImage = new File(https + bucketName + storedUrl + "/");

        try {
            compressJPAImage(originalImage,compressImage,0.20f);
            System.out.println("done..............");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void compressJPAImage(File originalImage, File compressImage, Float compressQuality) throws IOException {
        RenderedImage image = ImageIO.read(originalImage);
        ImageWriter jpgWriter=ImageIO.getImageWritersByFormatName("jpg").next();

        ImageWriteParam jpaParam = jpgWriter.getDefaultWriteParam();
        jpaParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        jpaParam.setCompressionQuality(compressQuality);

        try (ImageOutputStream outputStream = ImageIO.createImageOutputStream(compressImage)) {
            jpgWriter.setOutput(outputStream);
            IIOImage outputImage = new IIOImage(image, null, null);
            jpgWriter.write(null, outputImage, jpaParam);

        }
        jpgWriter.dispose();

    }

    public static void main(String[] args) {

    }

}
package com.sparta.netflixclone.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteBucketRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.sparta.netflixclone.entity.Member;
import com.sparta.netflixclone.entity.enumclass.ExceptionEnum;
import com.sparta.netflixclone.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marvin.image.MarvinImage;
import org.marvinproject.image.transform.scale.Scale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3Upload {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.s3.url}")
    private String defaultUrl;
    private final AmazonS3 amazonS3;
    private final AmazonS3Client amazonS3Client;


    // s3에 올라갈 파일 설정 후 업로드
    public String upload(MultipartFile uploadFile) throws IOException {
        String origName = uploadFile.getOriginalFilename();
        String url;
        try {
            final String ext = origName.substring(origName.lastIndexOf('.')+1);

            if(!(ext.equals("png")||ext.equals("jpg")||ext.equals("jpeg"))){
                throw new CustomException(ExceptionEnum.WRONG_IMAGE_VALUE);
            }

            final String saveFileName = getUuid() + "." + ext;

            File file = new File(System.getProperty("user.dir") + saveFileName);

            // 이미지 리사이징
            MultipartFile resizedFile = resizeImage(saveFileName,ext,uploadFile,300);

            resizedFile.transferTo(file);
            uploadOnS3(saveFileName, file);

            url = defaultUrl + saveFileName;
            file.delete();

        } catch (StringIndexOutOfBoundsException e) {
            url = null;
        }
        return url;
    }

    private static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    // 파일 s3에 업로드
    private void uploadOnS3(final String findName, final File file) {
        final TransferManager transferManager = new TransferManager(this.amazonS3Client);
        final PutObjectRequest request = new PutObjectRequest(bucket, findName, file);
        final Upload upload =  transferManager.upload(request);

        // 업로드 중 발생할 수 있는 에러
        try {
            upload.waitForCompletion();
        } catch (AmazonClientException amazonClientException) {
            log.error(amazonClientException.getMessage());
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    // 기존 이미지 S3에서 삭제
    public void deleteS3File(String fileName){
        String delFileName = fileName.substring(fileName.lastIndexOf("com/")+4);
        amazonS3.deleteObject(new DeleteObjectRequest(bucket,delFileName));
    }

    // 이미지 리사이징
    MultipartFile resizeImage(String fileName, String fileFormatName, MultipartFile originalImage, int imageSize){
        try{
            BufferedImage image = ImageIO.read(originalImage.getInputStream());
            int originWidth = image.getWidth();
            int originHeight = image.getHeight();

            MarvinImage imageMarvin = new MarvinImage(image);

            Scale scale = new Scale();
            scale.load();
            scale.setAttribute("newWidth", imageSize);
            scale.setAttribute("newHeight",imageSize*originHeight/originWidth);
            scale.process(imageMarvin.clone(),imageMarvin,null,null,false);

            BufferedImage imageNoAlpha = imageMarvin.getBufferedImageNoAlpha();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(imageNoAlpha,fileFormatName,baos);
            baos.flush();

            return new MockMultipartFile(fileName,baos.toByteArray());

        } catch (IOException e) {
            throw new CustomException(ExceptionEnum.WRONG_VALUE);
        }
    }
}
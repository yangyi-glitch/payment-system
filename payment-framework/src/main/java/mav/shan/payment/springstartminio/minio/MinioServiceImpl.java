package mav.shan.payment.springstartminio.minio;

import cn.hutool.core.util.ObjectUtil;
import io.minio.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import mav.shan.payment.springstartminio.config.MinioConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;


@Slf4j
@Service
public class MinioServiceImpl implements MinioService {

    @Resource
    private MinioClient minioClient;
    @Resource
    private MinioConfig minioConfig;

    @Override
    public boolean bucketExists() {
        try {
            boolean b = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioConfig.getBucket()).build());
            if (!b) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioConfig.getBucket()).build());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean remove(String url) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(minioConfig.getBucket())
                    .object(url)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean upload(String url, MultipartFile file, String suffix) {
        try {
            InputStream inputStream = file.getInputStream();
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(minioConfig.getBucket())
                    .object(url)
                    .stream(inputStream, file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean upload(byte[] butes, String contentType, String url) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(butes);
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(minioConfig.getBucket())
                    .object(url)
                    .stream(inputStream, butes.length, -1)
                    .contentType(contentType)
                    .build());
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public String perviewUrl(String url) {
        String contentType = this.getContentType(url);
        String perviewUrl = "";
        try {
            perviewUrl = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .bucket(minioConfig.getBucket())
                            .object(url)
                            .method(Method.GET)
//                            .expiry(1, TimeUnit.DAYS) // 永久有效
                            .build());
        } catch (Exception e) {
            e.printStackTrace();
            return perviewUrl;
        }
        log.info("预览地址：" + perviewUrl);
        return perviewUrl;
    }

    private String getContentType(String url) {
        String contentType = "";
        try {
            StatObjectResponse statObjectResponse = minioClient.statObject(StatObjectArgs.builder()
                    .bucket(minioConfig.getBucket())
                    .object(url)
                    .build());
            if (ObjectUtil.isNotNull(statObjectResponse)) {
                contentType = statObjectResponse.contentType();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contentType;
    }
}

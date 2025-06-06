package mav.shan.payment.springstartminio.minio;

import org.springframework.web.multipart.MultipartFile;

public interface MinioService {

    /**
     * 检查桶存不存在
     */
    boolean bucketExists();

    /**
     * 删除文件
     */
    boolean remove(String url);

    /**
     * 上传文件
     *
     * @param url    路径
     * @param file   文件
     * @param suffix 后缀
     * @return
     */
    boolean upload(String url, MultipartFile file, String suffix);

    /**
     * 针对文件转换上传
     * @param butes
     * @return
     */
    boolean upload(byte[] butes, String contentType, String url);

    /**
     * 获取预览连接
     *
     * @param url
     * @return
     */
    String perviewUrl(String url);
}

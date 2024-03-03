package org.sosd.oss.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class AliyunOSSUtil {

    private static final String ENDPOINT = "oss-accelerate.aliyuncs.com";
    private static final String ACCESS_KEY_ID = "LTAI5tPJUvG9SibMtS2Tq1SN";
    private static final String ACCESS_KEY_SECRET = "OeM2AM9GAgIfv7mLyxvgYWjkkHUrA1";
    private static final String BUCKET_NAME = "web-tilas-y";

    private static final OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);

    // 上传文件
    public static String uploadFile(MultipartFile file) throws IOException {
        String fileKey = "avatar/" + file.getOriginalFilename();
        try {
            ossClient.putObject(BUCKET_NAME, fileKey, file.getInputStream());
            return fileKey; // 返回文件的key或URL
        } finally {
            // 不要在这里关闭ossClient，因为它是共享的
        }
    }

    // 更新文件（实际上是上传新文件覆盖旧文件）
    public static String updateFile(MultipartFile file, String oldFileKey) throws IOException {
        try {
            // 删除旧文件
            if (ossClient.doesObjectExist(BUCKET_NAME, oldFileKey)) {
                ossClient.deleteObject(BUCKET_NAME, oldFileKey);
            }
            // 上传新文件
            return uploadFile(file);
        } finally {
            // 不要在这里关闭ossClient
        }
    }

    // 需要一个关闭OSSClient的方法，在应用关闭时调用
    public static void shutdownOSSClient() {
        if (ossClient != null) {
            ossClient.shutdown();
        }
    }
}
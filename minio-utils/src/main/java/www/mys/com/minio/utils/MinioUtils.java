package www.mys.com.minio.utils;

import io.minio.MinioClient;
import io.minio.ObjectStat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import www.mys.com.utils.DownloadUtils;
import www.mys.com.file.base.MYSFileInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Date;
import java.util.List;


public class MinioUtils {

    private static final Logger log = LoggerFactory.getLogger(MinioUtils.class);

    private static long maxAge = -1;

    public static void setMaxAge(long maxAge) {
        MinioUtils.maxAge = maxAge;
    }

    public static boolean uploadFile(String host, String accessKey, String secretKey, String bucketName
            , MultipartFile file, String fileName) {
        log.info("uploadFile host=" + host + ";accessKey=" + accessKey + ";secretKey=" + secretKey
                + ";bucketName=" + bucketName + ";fileName=" + fileName);
        try {
            MinioClient minioClient = new MinioClient(host, accessKey, secretKey);
            boolean isExist = minioClient.bucketExists(bucketName);
            if (!isExist) {
                minioClient.makeBucket(bucketName);
            }
            minioClient.putObject(bucketName, fileName, file.getInputStream(), file.getContentType());
            return true;
        } catch (Exception e) {
            log.info("uploadFile error: " + e);
            return false;
        }
    }

    public static boolean uploadFile(String host, String accessKey, String secretKey, String bucketName
            , InputStream inputStream, String fileName, String contentType) {
        log.info("uploadFile host=" + host + ";accessKey=" + accessKey + ";secretKey=" + secretKey
                + ";bucketName=" + bucketName + ";fileName=" + fileName + ";contentType=" + contentType);
        try {
            MinioClient minioClient = new MinioClient(host, accessKey, secretKey);
            boolean isExist = minioClient.bucketExists(bucketName);
            if (!isExist) {
                minioClient.makeBucket(bucketName);
            }
            minioClient.putObject(bucketName, fileName, inputStream, contentType);
            return true;
        } catch (Exception e) {
            log.info("uploadFile error: " + e);
            return false;
        }
    }

    public static boolean downloadFile(HttpServletRequest request, HttpServletResponse response, String host
            , String accessKey, String secretKey, String bucketName, String objectName) {
        log.info("downloadFile host=" + host + ";accessKey=" + accessKey + ";secretKey=" + secretKey
                + ";bucketName=" + bucketName + ";objectName=" + objectName);
        try {
            MinioClient minioClient = new MinioClient(host, accessKey, secretKey);
            boolean isExist = minioClient.bucketExists(bucketName);
            if (!isExist) {
                log.info("bucketName not exist:bucketName=" + bucketName);
                return false;
            }
            ObjectStat objectStat = minioClient.statObject(bucketName, objectName);
            return DownloadUtils.downloadFile(request, response, minioClient.getObject(bucketName, objectName)
                    , objectStat.name(), objectStat.length(), objectStat.contentType(), true, maxAge);
        } catch (Exception e) {
            log.info("downloadFile error: " + e);
        }
        return false;
    }

    public static void deleteFile(String host, String accessKey, String secretKey, String bucketName, String objectName) {
        log.info("deleteFile host=" + host + ";accessKey=" + accessKey + ";secretKey=" + secretKey
                + ";bucketName=" + bucketName + ";objectName=" + objectName);
        try {
            MinioClient minioClient = new MinioClient(host, accessKey, secretKey);
            boolean isExist = minioClient.bucketExists(bucketName);
            if (!isExist) {
                log.info("bucketName not exist:bucketName=" + bucketName);
                return;
            }
            minioClient.removeObject(bucketName, objectName);
        } catch (Exception e) {
            log.info("downloadFile error: " + e);
        }
    }

    public static void deleteFiles(String host, String accessKey, String secretKey, String bucketName, List<String> objectNames) {
        log.info("deleteFiles host=" + host + ";accessKey=" + accessKey + ";secretKey=" + secretKey
                + ";bucketName=" + bucketName + ";objectNames=" + objectNames);
        try {
            MinioClient minioClient = new MinioClient(host, accessKey, secretKey);
            boolean isExist = minioClient.bucketExists(bucketName);
            if (!isExist) {
                log.info("bucketName not exist:bucketName=" + bucketName);
                return;
            }
            minioClient.removeObject(bucketName, objectNames);
        } catch (Exception e) {
            log.info("downloadFile error: " + e);
        }
    }

    public static MYSFileInfo getFileInfo(String host, String accessKey, String secretKey, String bucketName
            , String objectName, boolean needStream) {
        log.info("getFileInfo host=" + host + ";accessKey=" + accessKey + ";secretKey=" + secretKey
                + ";bucketName=" + bucketName + ";objectName=" + objectName);
        try {
            MinioClient minioClient = new MinioClient(host, accessKey, secretKey);
            boolean isExist = minioClient.bucketExists(bucketName);
            if (!isExist) {
                log.info("bucketName not exist:bucketName=" + bucketName);
                return null;
            }
            ObjectStat objectStat = minioClient.statObject(bucketName, objectName);
            InputStream inputStream;
            if (needStream) {
                inputStream = minioClient.getObject(bucketName, objectName);
            } else {
                inputStream = null;
            }
            return new MYSFileInfo() {
                @Override
                public InputStream getStream() {
                    return inputStream;
                }

                @Override
                public String getId() {
                    return objectStat.name();
                }

                @Override
                public String getFileName() {
                    return objectStat.name();
                }

                @Override
                public String getContentType() {
                    return objectStat.contentType();
                }

                @Override
                public long getLength() {
                    return objectStat.length();
                }

                @Override
                public Date getCreatedAt() {
                    return objectStat.createdTime();
                }

                @Override
                public Date getUpdatedAt() {
                    return objectStat.createdTime();
                }
            };
        } catch (Exception e) {
            log.info("getFileInfo error.e=" + e);
            return null;
        }
    }

}

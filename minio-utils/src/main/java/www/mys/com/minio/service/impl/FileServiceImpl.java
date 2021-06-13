package www.mys.com.minio.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import www.mys.com.minio.base.MYSFileInfo;
import www.mys.com.minio.service.FileService;
import www.mys.com.minio.utils.MinioUtils;
import www.mys.com.utils.BaseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

@Service(value = "fileService")
public class FileServiceImpl implements FileService {

    @Value(value = "${minio.host}")
    private String minioHost;
    @Value(value = "${minio.accessKey}")
    private String minioAccessKey;
    @Value(value = "${minio.secretKey}")
    private String minioSecretKey;
    @Value(value = "${minio.bucketName}")
    private String minioBucketName;

    @Override
    public MYSFileInfo uploadFile(InputStream inputStream, String fileName, String contentType) throws Exception {
        if (MinioUtils.uploadFile(minioHost, minioAccessKey, minioSecretKey, minioBucketName
                , inputStream, fileName, contentType)) {
            return MinioUtils.getFileInfo(minioHost, minioAccessKey, minioSecretKey, minioBucketName, fileName, false);
        }
        throw new BaseException("upload file error.", -1);
    }

    @Override
    public MYSFileInfo uploadFile(MultipartFile file, String fileName) throws Exception {
        if (MinioUtils.uploadFile(minioHost, minioAccessKey, minioSecretKey, minioBucketName, file, fileName)) {
            return MinioUtils.getFileInfo(minioHost, minioAccessKey, minioSecretKey, minioBucketName
                    , fileName, false);
        }
        throw new BaseException("upload file error.", -1);
    }

    @Override
    public MYSFileInfo getFile(String fileId, boolean backFile) {
        return MinioUtils.getFileInfo(minioHost, minioAccessKey, minioSecretKey, minioBucketName, fileId
                , backFile);
    }

    @Override
    public void deleteFile(String fileId) {
        MinioUtils.deleteFile(minioHost, minioAccessKey, minioSecretKey, minioBucketName, fileId);
    }

    @Override
    public void deleteFiles(List<String> fileIds) {
        MinioUtils.deleteFiles(minioHost, minioAccessKey, minioSecretKey, minioBucketName, fileIds);
    }

    @Override
    public void downloadFile(HttpServletRequest request, HttpServletResponse response, String fileId) throws Exception {
        MinioUtils.downloadFile(request, response, minioHost, minioAccessKey, minioSecretKey, minioBucketName, fileId);
    }
}

package www.mys.com.file.service;

import org.springframework.web.multipart.MultipartFile;
import www.mys.com.file.base.MYSFileInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

public interface FileService {

    public MYSFileInfo uploadFile(InputStream inputStream, String fileName, String contentType) throws Exception;

    public MYSFileInfo uploadFile(MultipartFile file, String fileName) throws Exception;

    public MYSFileInfo getFile(String fileId, boolean backFile);

    public void deleteFile(String fileId);

    public void deleteFiles(List<String> fileIds);

    public void downloadFile(HttpServletRequest request, HttpServletResponse response
            , String fileId) throws Exception;

}

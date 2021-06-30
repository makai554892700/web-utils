package www.mys.com.file.service.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import www.mys.com.file.base.MYSFileInfo;
import www.mys.com.file.service.FileService;
import www.mys.com.file.utils.PathUtils;
import www.mys.com.utils.DownloadUtils;
import www.mys.com.utils.FileUtils;
import www.mys.com.utils.MD5Utils;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service(value = "fileService")
public class FileServiceImpl implements FileService {

    private static final Logger log = Logger.getLogger(FileServiceImpl.class.getName());

    private static long maxAge = -1;
    private static String tempPath = PathUtils.TEMP_PATH;

    public static void setMaxAge(long maxAge) {
        FileServiceImpl.maxAge = maxAge;
    }

    public static void setTempPath(String tempPath) {
        FileServiceImpl.tempPath = tempPath;
        FileUtils.sureDir(tempPath);
    }

    @Override
    public MYSFileInfo uploadFile(InputStream inputStream, String fileName, String contentType) throws Exception {
        Name name = getNameByFileName(fileName);
        File realFile = FileUtils.sureFileIsNew(name.realPath);
        if (!FileUtils.inputStream2File(inputStream, realFile)) {
            throw new Exception("upload file error.");
        }
        return transName2MYSFileInfo(name);
    }

    @Override
    public MYSFileInfo uploadFile(MultipartFile file, String fileName) throws Exception {
        return uploadFile(file.getInputStream(), fileName, file.getContentType());
    }

    @Override
    public MYSFileInfo getFile(String fileId, boolean backFile) {
        Name name = getNameById(fileId);
        if (name == null) {
            return null;
        }
        return transName2MYSFileInfo(name);
    }

    @Override
    public void deleteFile(String fileId) {
        Name name = getNameById(fileId);
        if (name != null) {
            FileUtils.deleteFile(new File(name.realPath));
        }
    }

    @Override
    public void deleteFiles(List<String> fileIds) {
        for (String fileId : fileIds) {
            deleteFile(fileId);
        }
    }

    @Override
    public void downloadFile(HttpServletRequest request, HttpServletResponse response, String fileId) throws Exception {
        Name name = getNameById(fileId);
        if (name != null) {
            DownloadUtils.downloadFile(request, response, new FileInputStream(name.realPath), name.fileName
                    , new File(name.realPath).length(), new MimetypesFileTypeMap().getContentType(name.fileName)
                    , true, maxAge);
        } else {
            throw new Exception("file not exist.");
        }
    }

    public static Name getNameById(String id) {
        if (id == null || id.length() != 32) {
            return null;
        }
        Name result = new Name();
        result.longName = id;
        result.shortName = id.substring(0, 16);
        result.start = id.substring(0, 4);
        result.end = id.substring(28, 32);
        String startPath = tempPath + File.separatorChar + result.start;
        FileUtils.sureDir(startPath);
        String endPath = startPath + File.separatorChar + result.end;
        FileUtils.sureDir(endPath);
        StringBuilder resultFileName = new StringBuilder();
        FileUtils.listDir(endPath, new FileUtils.DirBack() {
            @Override
            public void onStart(String fileName) {
                if (resultFileName.length() == 0 && id.equals(MD5Utils.MD5(fileName, false))) {
                    resultFileName.append(fileName);
                }
            }

            @Override
            public void onDir(String line) {

            }

            @Override
            public void onEnd(String fileName) {

            }
        });
        if (resultFileName.length() == 0) {
            log.info("file not exist.id=" + id);
            return null;
        }
        result.fileName = resultFileName.toString();
        result.realPath = endPath + File.separatorChar + resultFileName;
        return result;
    }

    public static Name getNameByFileName(String fileName) {
        Name result = new Name();
        result.fileName = fileName;
        String data = MD5Utils.MD5(fileName, false);
        result.longName = data;
        result.shortName = data.substring(0, 16);
        result.start = data.substring(0, 2);
        result.end = data.substring(30, 32);
        String startPath = tempPath + File.separatorChar + result.start;
        FileUtils.sureDir(startPath);
        String endPath = startPath + File.separatorChar + result.end;
        FileUtils.sureDir(endPath);
        result.realPath = endPath + File.separatorChar + fileName;
        return result;
    }

    public static MYSFileInfo transName2MYSFileInfo(Name name) {
        if (name == null) {
            return null;
        }
        return new MYSFileInfo() {
            @Override
            public InputStream getStream() {
                try {
                    return new FileInputStream(name.realPath);
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            public String getId() {
                return name.longName;
            }

            @Override
            public String getFileName() {
                return name.fileName;
            }

            @Override
            public String getContentType() {
                return new MimetypesFileTypeMap().getContentType(name.fileName);
            }

            @Override
            public long getLength() {
                return new File(name.realPath).length();
            }

            @Override
            public Date getCreatedAt() {
                return null;
            }

            @Override
            public Date getUpdatedAt() {
                return null;
            }
        };
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Name {
        private String fileName;
        private String longName;
        private String shortName;
        private String start;
        private String end;
        private String realPath;
    }

}

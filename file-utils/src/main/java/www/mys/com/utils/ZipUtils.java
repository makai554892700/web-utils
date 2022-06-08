package www.mys.com.utils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipUtils {
    private static final Logger log = Logger.getLogger(ZipUtils.class.getName());

    public static void zip(File file, String zipFile) {
        zip(new ArrayList<File>() {{
            add(file);
        }}, zipFile);
    }

    public static void zip(List<File> files, String zipFile) {
        FileUtils.sureFileIsNew(zipFile);
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(zipFile);
        } catch (Exception e) {
            log.log(Level.WARNING, "getFileOutputStream error.e=" + e);
            return;
        }
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);
        for (File file : files) {
            try {
                zip(zipOutputStream, file, file.getName());
            } catch (Exception e) {
                log.log(Level.WARNING, "zip error.e=" + e);
            }
        }
        CloseUtils.closeSilently(zipOutputStream);
        CloseUtils.closeSilently(bufferedOutputStream);
        CloseUtils.closeSilently(fileOutputStream);
    }

    private static void zip(ZipOutputStream zipOutputStream, File file, String zipFilePath) throws Exception {
        log.log(Level.WARNING, "zipFilePath=" + zipFilePath);
        if (file.isDirectory()) {
            File[] fileList = file.listFiles();
            zipOutputStream.putNextEntry(new ZipEntry(zipFilePath + File.separator));
            zipFilePath = zipFilePath.length() == 0 ? "" : zipFilePath + File.separator;
            if (fileList != null) {
                for (File tempFile : fileList) {
                    zip(zipOutputStream, tempFile, zipFilePath + tempFile.getName());
                }
            }
        } else {
            zipOutputStream.putNextEntry(new ZipEntry(zipFilePath));
            FileInputStream inputStream = new FileInputStream(file);
            int tempLen = 102400;
            int len;
            byte[] data = new byte[tempLen];
            while ((len = inputStream.read(data)) != -1) {
                zipOutputStream.write(data, 0, len);
            }
            inputStream.close();
        }
    }

    public static File unzip(File zip, String rootPath) {
        return unzip(zip, rootPath, Charset.forName("GBK"));
    }

    public static File unzip(File zip, String rootPath, Charset charset) {
        boolean isInited = false;
        File rootFile = null;
        FileUtils.sureDir(rootPath);
        ZipFile zipFile = null;
        String path, tempParent, tempPath;
        Enumeration enumeration;
        ZipEntry entry;
        InputStream inputStream;
        int index;
        try {
            zipFile = new ZipFile(zip, charset);
            enumeration = zipFile.entries();
            while (enumeration.hasMoreElements()) {
                entry = (ZipEntry) enumeration.nextElement();
                path = rootPath + File.separator + entry.getName();
                log.log(Level.WARNING, "entry.getName()=" + entry.getName());
                if (!isInited) {
                    rootFile = new File(path);
                    isInited = true;
                }
                if (entry.isDirectory()) {
                    FileUtils.sureDir(path);
                } else {
                    tempParent = path.substring(0, path.lastIndexOf("/") + 1);
                    tempPath = tempParent.replace(rootPath, "");
                    index = 1;
                    while (index > 0) {
                        index = tempPath.indexOf("/", index + 1);
                        if (index > 1) {
                            FileUtils.sureDir(rootPath + tempPath.substring(0, index));
                        }
                    }
                    FileUtils.sureFileIsNew(path);
                    inputStream = zipFile.getInputStream(entry);
                    FileUtils.inputStream2File(inputStream, new File(path));
                    CloseUtils.closeSilently(inputStream);
                }
            }
        } catch (Exception e) {
            log.log(Level.WARNING, "unzip file error.e=" + e);
        } finally {
            CloseUtils.closeSilently(zipFile);
        }
        return rootFile;
    }

}

package www.mys.com.utils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileUtils {

    private static final Logger log = Logger.getLogger(FileUtils.class.getName());

    public static File sureDir(String dir) {
        log.log(Level.WARNING, "sure dir = " + dir);
        if (dir == null) {
            return null;
        }
        File tempFile = new File(dir);
        if (!tempFile.exists()) {
            if (!tempFile.mkdir()) {
                return null;
            }
        }
        return tempFile;
    }

    public static File sureFile(String filePath) {
        if (filePath == null) {
            return null;
        }
        File tempFile = new File(filePath);
        if (!tempFile.exists()) {
            try {
                if (!tempFile.createNewFile()) {
                    return null;
                }
            } catch (Exception e) {
                return null;
            }
        }
        return tempFile;
    }

    public static boolean deleteFile(File file) {
        if (file == null || !file.exists()) {
            return false;
        }
        return deleteDir(file);
    }

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null) {
                for (String child : children) {
                    boolean success = deleteDir(new File(dir, child));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        return dir.delete();
    }

    public static void listAllFile(String dirPath, AllBack all) {
        if (all == null) {
            return;
        }
        all.onStart(dirPath);
        LineBack lineBack = new LineBack() {
            private String fileName;

            @Override
            public void onStart(String fileName) {
                this.fileName = fileName;
                all.onFileStart(fileName);
            }

            @Override
            public void onLine(String line) {
                all.onLine(fileName, line);
            }

            @Override
            public void onEnd(String fileName) {
                all.onFileEnd(fileName);
            }
        };
        DirBack dirBack = new DirBack() {
            @Override
            public void onStart(String fileName) {
            }

            @Override
            public void onDir(String line) {
                File file = new File(line);
                if (file.isDirectory()) {
                    if (all.needReadLine(line)) {
                        listAllFile(line, all);
                    }
                } else {
                    if (all.needReadLine(line)) {
                        readLine(line, lineBack);
                    }
                }
            }

            @Override
            public void onEnd(String fileName) {
            }
        };
        listDir(dirPath, dirBack);
        all.onEnd(dirPath);
    }

    public static void listDir(String dirPath, DirBack dirBack) {
        if (dirBack == null) {
            return;
        }
        dirBack.onStart(dirPath);
        if (dirPath == null || dirPath.isEmpty()) {
            dirBack.onEnd(dirPath);
            return;
        }
        File file = new File(dirPath);
        if (!file.exists() || !file.isDirectory()) {
            log.log(Level.WARNING, "file is not exists or is not a directory");
        }
        File[] children = file.listFiles();
        if (children != null) {
            for (File child : children) {
                dirBack.onDir(child.getAbsolutePath());
            }
        }
        dirBack.onEnd(dirPath);
    }

    public static File sureFileIsNew(String filePath) {
        if (filePath != null && !filePath.isEmpty()) {
            File file = new File(filePath);
            if (file.exists()) {
                boolean isOk = file.delete();
            }
            try {
                boolean isOk = file.createNewFile();
            } catch (Exception e) {
                log.log(Level.WARNING, "e=" + e);
                return null;
            }
            return file;
        }
        return null;
    }

    public static boolean strToFile(File file, StringBuilder data) {
        if (data == null) {
            return false;
        }
        AppendFileUtils appendFileUtils = AppendFileUtils.getInstance(file);
        final int len = 102400;
        while (data.length() > 0) {
            int tempLen = Math.min(data.length(), len);
            appendFileUtils.appendString(data.substring(0, tempLen));
            data.delete(0, tempLen);
        }
        appendFileUtils.endAppendFile();
        return true;
    }

    public static void appendStr2File(File file, String data) {
        AppendFileUtils appendFileUtils = AppendFileUtils.getInstance(file);
        appendFileUtils.appendString(data);
        appendFileUtils.endAppendFile();
    }

    public static boolean byte2File(File file, byte[] data) {
        if (data == null || file == null) {
            return false;
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(data);
        } catch (Exception e) {
            return false;
        } finally {
            CloseUtils.closeSilently(fileOutputStream);
        }
        return true;
    }

    public static byte[] inputStream2Bytes(InputStream inputStream) {
        if (inputStream == null) {
            log.log(Level.WARNING, "message is error.");
            return null;
        } else {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            if (!StreamUtils.inputStream2OutputStream(inputStream, byteArrayOutputStream)) {
                CloseUtils.closeSilently(byteArrayOutputStream);
                return null;
            }
            CloseUtils.closeSilently(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
    }

    public static boolean inputStream2File(InputStream inputStream, File file) {
        boolean result = false;
        if (inputStream == null || file == null || !file.exists()) {
            log.log(Level.WARNING, "message is error.");
        } else {
            OutputStream outputStream;
            try {
                outputStream = new FileOutputStream(file);
            } catch (Exception e) {
                log.log(Level.WARNING, "e=" + e);
                return false;
            }
            result = StreamUtils.inputStream2OutputStream(inputStream, outputStream);
            CloseUtils.closeSilently(outputStream);
        }
        return result;
    }

    public static boolean readLine(InputStream is, LineBack lineBack) {
        boolean result = false;
        if (lineBack != null && is != null) {
            InputStreamReader inputStreamReader;
            TempStream tempStream;
            try {
                tempStream = codeString(is);
                is = tempStream.inputStream;
                inputStreamReader = new InputStreamReader(is, tempStream.charset);
            } catch (Exception e) {
                log.log(Level.WARNING, "e1=" + e);
                return false;
            }
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            result = onLine(lineBack, bufferedReader, tempStream.charset);
            CloseUtils.closeReader(bufferedReader);
            CloseUtils.closeReader(inputStreamReader);
        } else {
            log.log(Level.WARNING, "is is null or lineBack is null;is=" + is + ";lineBack=" + lineBack);
        }
        return result;
    }

    public static class TempStream {
        public InputStream inputStream;
        public Charset charset;
    }

    public static TempStream codeString(InputStream inputStream) throws Exception {
        TempStream result = new TempStream();
        byte[] data = inputStream2Bytes(inputStream);
        result.charset = charset(new ByteArrayInputStream(data));
        result.inputStream = new ByteArrayInputStream(data);
//        log.log(Level.WARNING,"charset=" + result.charset);
        return result;
    }

    public static Charset charset(InputStream inputStream) {
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];
        try {
            boolean checked = false;
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1) {
                bis.close();
                return Charset.forName(charset);
            } else if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                charset = "UTF-16LE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
                charset = "UTF-16BE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF-8";
                checked = true;
            }
            bis.reset();
            if (!checked) {
                while ((read = bis.read()) != -1) {
                    if (read >= 0xF0)
                        break;
                    if (0x80 <= read && read <= 0xBF)
                        break;
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF)
                            continue;
                        else
                            break;
                    } else if (0xE0 <= read && read <= 0xEF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else
                                break;
                        } else
                            break;
                    }
                }
            }
            bis.close();
        } catch (Exception e) {
            log.log(Level.WARNING, "e=" + e);
        }
        return Charset.forName(charset);
    }

    public static boolean readLine(String filePath, LineBack lineBack) {
        boolean result = false;
        if (lineBack != null && filePath != null && !filePath.isEmpty()) {
            lineBack.onStart(filePath);
            File file = new File(filePath);
            if (file.exists()) {
                FileReader fileReader = null;
                TempStream tempStream;
                try {
                    tempStream = codeString(new FileInputStream(filePath));
                    fileReader = new FileReader(file);
                } catch (Exception e) {
                    log.log(Level.WARNING, "e1=" + e);
                    return false;
                }
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                result = onLine(lineBack, bufferedReader, tempStream.charset);
                CloseUtils.closeReader(bufferedReader);
                CloseUtils.closeReader(fileReader);
            }
            lineBack.onEnd(filePath);
        } else {
            log.log(Level.WARNING, "filePath is null or lineBack is null;filePath=" + filePath + ";lineBack=" + lineBack);
        }
        return result;
    }

    private static boolean onLine(LineBack lineBack, BufferedReader bufferedReader, Charset charset) {
        StringBuilder tempString = new StringBuilder();
        String line, tempStr;
        while (true) {
            try {
                tempStr = bufferedReader.readLine();
                if (tempStr == null) {
                    return true;
                }
                tempString.append(tempStr);
            } catch (Exception e) {
                log.log(Level.WARNING, "e1=" + e);
                return false;
            }
//            log.log(Level.WARNING,"line1=" + tempString);
//            log.log(Level.WARNING,"line2=" + new String(tempString.toString().getBytes(), StandardCharsets.UTF_8));
//            log.log(Level.WARNING,"line3=" + new String(tempString.toString().getBytes(charset), StandardCharsets.UTF_8));
//            log.log(Level.WARNING,"line4=" + new String(tempString.toString().getBytes(StandardCharsets.UTF_8), charset));
            line = new String(tempString.toString().getBytes(), StandardCharsets.UTF_8);
            lineBack.onLine(line);
//            log.log(Level.WARNING,"line=" + line);
            tempString.delete(0, tempString.length());
        }
    }

    public interface AllBack {
        public void onStart(String fileName);

        public boolean needReadLine(String fileName);

        public void onFileStart(String fileName);

        public void onFileEnd(String fileName);

        public void onLine(String fileName, String line);

        public void onEnd(String fileName);
    }

    public interface LineBack {
        public void onStart(String fileName);

        public void onLine(String line);

        public void onEnd(String fileName);
    }

    public interface DirBack {
        public void onStart(String fileName);

        public void onDir(String line);

        public void onEnd(String fileName);
    }

}

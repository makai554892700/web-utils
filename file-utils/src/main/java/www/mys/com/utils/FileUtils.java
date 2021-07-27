package www.mys.com.utils;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileUtils {

    private static final Logger log = Logger.getLogger(FileUtils.class.getName());

    public static boolean transDE(InputStream deFileStream, String resultFilePath, String key) {
        if (key == null || key.length() != 256) {
            return false;
        }
        File resultFile = sureFileIsNew(resultFilePath);
        if (resultFile == null) {
            return false;
        }
        final FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(resultFilePath);
        } catch (Exception e) {
            return false;
        }
        final StringBuilder error = new StringBuilder();
        readByte(deFileStream, new ByteBack() {
            private byte[] tempByte;

            @Override
            public void onStart(String fileName) {
            }

            @Override
            public void onByte(byte[] data, int len) {
                try {
                    tempByte = deByte(data, len, key);
                    fileOutputStream.write(tempByte, 0, len);
                } catch (Exception e) {
                    error.append("-");
                }
            }

            @Override
            public void onEnd(String fileName) {
            }
        }, true, 256);
        return error.length() == 0;

    }

    public static boolean transDE(String deFilePath, String resultFilePath, String key) {
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(deFilePath);
        } catch (Exception e) {
            return false;
        }
        return transDE(inputStream, resultFilePath, key);
    }

    private static byte[] deByte(byte[] data, int len, String key) {
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++) {
            result[i] = (byte) (data[i] ^ key.charAt(i));
        }
        return result;
    }

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
            if (StreamUtils.inputStream2OutputStream(inputStream, byteArrayOutputStream) < 0) {
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
            result = StreamUtils.inputStream2OutputStream(inputStream, outputStream) > 0;
            CloseUtils.closeSilently(outputStream);
        }
        return result;
    }

    public static boolean readLine(InputStream is, LineBack lineBack) {
        return readLine(is, lineBack, false);
    }

    public static boolean readByte(InputStream inputStream, ByteBack byteBack, boolean close, int len) {
        if (byteBack == null) {
            if (close) {
                CloseUtils.closeSilently(inputStream);
            }
            return false;
        }
        byte[] tempByte = new byte[len];
        int length;
        try {
            while ((length = inputStream.read(tempByte)) != -1) {
                byteBack.onByte(tempByte, length);
            }
        } catch (Exception e) {
            log.log(Level.WARNING, "ready byte error.e==" + e + ";byteBack=" + byteBack);
            return false;
        } finally {
            if (close) {
                CloseUtils.closeSilently(inputStream);
            }
        }
        return true;
    }

    public static boolean readLine(InputStream inputStream, LineBack lineBack, boolean close) {
        if (lineBack == null) {
            if (close) {
                CloseUtils.closeSilently(inputStream);
            }
            return false;
        }
        lineBack.onStart(null);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        try {
            while (reader.ready()) {
                lineBack.onLine(reader.readLine());
            }
        } catch (Exception e) {
            log.log(Level.WARNING, "ready line error.e==" + e + ";lineBack=" + lineBack);
            return false;
        } finally {
            CloseUtils.closeReader(reader);
            CloseUtils.closeReader(inputStreamReader);
            if (close) {
                CloseUtils.closeSilently(inputStream);
            }
            lineBack.onEnd(null);
        }
        return true;
    }

    public static boolean readByte(String filePath, ByteBack byteBack, int len) {
        boolean result = false;
        if (byteBack != null && filePath != null && !filePath.isEmpty()) {
            byteBack.onStart(filePath);
            File file = new File(filePath);
            if (file.exists()) {
                InputStream inputStream;
                try {
                    inputStream = new FileInputStream(file);
                } catch (Exception e) {
                    byteBack.onEnd(filePath);
                    log.log(Level.WARNING, "new fileInputStream error;filePath=" + filePath + ";byteBack=" + byteBack);
                    return false;
                }
                result = readByte(inputStream, byteBack, true, len);
            }
            byteBack.onEnd(filePath);
        } else {
            log.log(Level.WARNING, "filePath is null or byteBack is null;filePath=" + filePath + ";byteBack=" + byteBack);
        }
        return result;
    }

    public static boolean readLine(String filePath, LineBack lineBack) {
        boolean result = false;
        if (lineBack != null && filePath != null && !filePath.isEmpty()) {
            lineBack.onStart(filePath);
            File file = new File(filePath);
            if (file.exists()) {
                InputStream inputStream;
                try {
                    inputStream = new FileInputStream(file);
                } catch (Exception e) {
                    lineBack.onEnd(filePath);
                    log.log(Level.WARNING, "new fileInputStream error;filePath=" + filePath + ";lineBack=" + lineBack);
                    return false;
                }
                result = readLine(inputStream, lineBack, true);
            }
            lineBack.onEnd(filePath);
        } else {
            log.log(Level.WARNING, "filePath is null or lineBack is null;filePath=" + filePath + ";lineBack=" + lineBack);
        }
        return result;
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

    public interface ByteBack {
        public void onStart(String fileName);

        public void onByte(byte[] data, int len);

        public void onEnd(String fileName);
    }

}

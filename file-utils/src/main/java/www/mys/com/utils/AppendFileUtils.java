package www.mys.com.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppendFileUtils {

    private static final Logger log = Logger.getLogger(AppendFileUtils.class.getName());
    private static ConcurrentHashMap<String, AppendFileUtils> concurrentHashMap = new ConcurrentHashMap<>();

    private java.io.File fileName;
    private FileWriter fileWriter;
    private PrintWriter printWriter;
    private boolean isInited;

    private AppendFileUtils(java.io.File file) {
        this.fileName = file;
        try {
            fileWriter = new FileWriter(file, true);
        } catch (IOException e) {
            log.log(Level.WARNING, "e=" + e);
            return;
        }
        printWriter = new PrintWriter(fileWriter);
        isInited = true;
    }

    public static AppendFileUtils getInstance(java.io.File file) {
        if (file == null || !file.exists()) {
            return null;
        }
        if (concurrentHashMap.containsKey(file.getAbsolutePath())) {
            return concurrentHashMap.get(file.getAbsolutePath());
        } else {
            AppendFileUtils appendFileUtils = new AppendFileUtils(file);
            concurrentHashMap.put(file.getAbsolutePath(), appendFileUtils);
            return appendFileUtils;
        }
    }

    //直接叠加数据
    public void appendString(String data) {
        if (isInited) {
            printWriter.print(data);
            printWriter.flush();
        }
    }

    //叠加一行数据
    public void appendLineString(String data) {
        if (isInited) {
            printWriter.println(data);
            printWriter.flush();
        }
    }

    //是否正确初始化
    public boolean isInited() {
        return isInited;
    }

    //每次用完一定要关闭
    public void endAppendFile() {
        if (isInited) {
            try {
                fileWriter.flush();
            } catch (Exception e) {
                log.log(Level.WARNING, "e=" + e);
            }
            CloseUtils.closeSilently(printWriter);
            CloseUtils.closeSilently(fileWriter);
            concurrentHashMap.remove(fileName.getAbsolutePath());
            isInited = false;
        }
    }

}

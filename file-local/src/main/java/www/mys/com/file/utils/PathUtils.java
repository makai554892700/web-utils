package www.mys.com.file.utils;

import www.mys.com.utils.FileUtils;

public class PathUtils {

    public static String ROOT_PATH;
    public static String TEMP_PATH;

    static {
        try {
            ROOT_PATH = System.getProperty("user.dir");
        } catch (Exception e) {
            System.out.println("root path error.e=" + e);
        }
        TEMP_PATH = ROOT_PATH + "/temp_path";
        FileUtils.sureDir(TEMP_PATH);
    }

}

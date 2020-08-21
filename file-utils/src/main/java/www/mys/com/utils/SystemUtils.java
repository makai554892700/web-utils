package www.mys.com.utils;

import java.util.Properties;

public class SystemUtils {

    public static final class SysType {
        public static final int UNKNOW = 0;
        public static final int WIN = 1;
        public static final int LNX = 2;
        public static final int UBT = 3;
    }

    public static final String SYS_MODEL = System.getProperty("sun.arch.data.model");
    public static final String SYS_OS = System.getProperty("os.name").toLowerCase();
    public static int SYS_TYPE = SysType.UNKNOW;

    static {
        if (SYS_OS.startsWith("win")) {
            SYS_TYPE = SysType.WIN;
        } else if (SYS_OS.startsWith("linux")) {
            String cmdResult = CMDUtils.run("cat /etc/issue").toLowerCase();
            if (cmdResult.contains("ubuntu")) {
                SYS_TYPE = SysType.UBT;
            } else {
                SYS_TYPE = SysType.LNX;
            }
        }
    }

}

package www.mys.com.utils;

import java.io.Closeable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CloseUtils {

    private static final Logger log = Logger.getLogger(CloseUtils.class.getName());

    public static void closeSilently(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                log.log(Level.WARNING, "e=" + e);
            }
        }
    }

    public static void closeReader(java.io.Reader reader) {
        if (reader != null) {
            try {
                reader.close();
            } catch (Exception e) {
                log.log(Level.WARNING, "e=" + e);
            }
        }
    }

}

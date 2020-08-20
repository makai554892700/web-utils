package www.mys.com.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MD5Utils {

    private static final Logger log = Logger.getLogger(RC4Utils.class.getName());

    public static String MD5(String sSecret, boolean is16) {
        return MD5(sSecret, StandardCharsets.UTF_8, is16);
    }

    public static String MD5(String sSecret, Charset charSet, boolean is16) {
        String result = "";
        try {
            MessageDigest bmd5 = MessageDigest.getInstance("MD5");
            bmd5.update(sSecret.getBytes(charSet));
            StringBuilder buf = new StringBuilder();
            byte[] b = bmd5.digest();// 加密
            for (int i : b) {
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
            result = is16 ? result.substring(8, 24) : result;
        } catch (NoSuchAlgorithmException e) {
            log.log(Level.WARNING, "e=" + e);
        }
        return result.toUpperCase();
    }

}

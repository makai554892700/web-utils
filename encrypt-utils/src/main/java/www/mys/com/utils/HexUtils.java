package www.mys.com.utils;

import java.nio.charset.Charset;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HexUtils {

    private static final Logger log = Logger.getLogger(HexUtils.class.getName());
    public static final String[] HEX_STR = new String[]{"0", "1", "2"
            , "3", "4", "5", "6", "7", "8", "9", "A", "B"
            , "C", "D", "E", "F"};

    public static String getHexStr(int len) {
        if (len < 1) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            stringBuilder.append(HEX_STR[random.nextInt(HEX_STR.length)]);
        }
        return stringBuilder.toString();
    }

    public static String bytesToHexString(byte[] srcs) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (srcs == null || srcs.length <= 0) {
            return null;
        }
        for (byte src : srcs) {
            int v = src & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static String strToHexString(String str, String charSet) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        return bytesToHexString(str.getBytes(Charset.forName(charSet)));
    }

    public static String hexStringToString(String hexString, String charSet) {
        String result = null;
        byte[] data = hexStringToBytes(hexString);
        try {
            result = new String(data, charSet);
        } catch (Exception e) {
            log.log(Level.WARNING, "e=" + e);
        }
        return result;
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }


}

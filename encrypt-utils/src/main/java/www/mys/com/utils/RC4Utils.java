package www.mys.com.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RC4Utils {

    private static final Logger log = Logger.getLogger(RC4Utils.class.getName());

    public static String deCode(String key, String value, String charSet) {
        if (null == value || "".equals(value)) {
            return "";
        }
        String tempStr;
        try {
            tempStr = new String(HexUtils.hexStringToBytes(value), charSet);
        } catch (Exception e) {
            log.log(Level.WARNING, "e=" + e);
            tempStr = new String(HexUtils.hexStringToBytes(value));
        }
        return RC4(key, tempStr, charSet);
    }

    public static String enCode(String key, String value, String charSet) {
        if (null == value || "".equals(value)) {
            return "";
        }
        String tempStr = RC4(key, value, charSet);
        byte[] tempByte;
        try {
            tempByte = tempStr.getBytes(charSet);
        } catch (Exception e) {
            tempByte = tempStr.getBytes();
        }
        return HexUtils.bytesToHexString(tempByte);
    }

    private static String RC4(String key, String value, String charSet) {
        int[] iS = new int[256];
        byte[] iK = new byte[256];
        for (int i = 0; i < 256; i++)
            iS[i] = i;
        for (short i = 0; i < 256; i++) {
            iK[i] = (byte) key.charAt((i % key.length()));
        }
        int j = 0;
        for (int i = 0; i < 255; i++) {
            j = (j + iS[i] + iK[i]) % 256;
            int temp = iS[i];
            iS[i] = iS[j];
            iS[j] = temp;
        }
        int i = 0;
        j = 0;
        char[] iInputChar = value.toCharArray();
        char[] iOutputChar = new char[iInputChar.length];
        for (int x = 0; x < iInputChar.length; x++) {
            i = (i + 1) % 256;
            j = (j + iS[i]) % 256;
            int temp = iS[i];
            iS[i] = iS[j];
            iS[j] = temp;
            int t = (iS[i] + (iS[j] % 256)) % 256;
            int iY = iS[t];
            char iCY = (char) iY;
            iOutputChar[x] = (char) (iInputChar[x] ^ iCY);
        }
        return new String(iOutputChar);
    }
}

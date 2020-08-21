package www.mys.com.utils;

import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPUtils {

    private static final Logger log = Logger.getLogger(IPUtils.class.getName());
    private static final String IP_SPLIT = "\\.";
    private static final String IP_ZERO = "0.0.0.0";
    private static Pattern pattern = Pattern.compile("((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})(\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})){3}");
    private static final String URL_GET_HTTPS_IP = "https://api.ip.sb/ip";

    public static String getProxyIP(Proxy proxy) {
        return getProxyIP(proxy, 5 * 1000);
    }

    public static String getProxyIP(Proxy proxy, int timeOut) {
        final StringBuilder result = new StringBuilder();
        HttpUtils.getURLResponse(URL_GET_HTTPS_IP
                , null, proxy, new HttpUtils.IWebCallback() {
                    @Override
                    public void onCallback(int status, String message, Map<String, List<String>> heard, byte[] data) {
                        if (data != null) {
                            String tempResult = new String(data, StandardCharsets.UTF_8).trim();
                            Matcher matcher = pattern.matcher(tempResult);
                            if (matcher.matches()) {
                                result.append(tempResult);
                            }
                        }
                    }

                    @Override
                    public void onFail(int status, String message) {
                        log.log(Level.WARNING, "onFail status=" + status + ";message=" + message);
                    }
                }, timeOut);
        return result.toString();
    }


    public static int ip2Key(String ip) {
        int result = 0;
        if (ip != null && !ip.isEmpty()) {
            String[] parts = ip.split(IP_SPLIT);
            if (parts.length == 4) {
                try {
                    result = ((Integer.parseInt(parts[0]) % 256) << 16) + ((Integer.parseInt(parts[1]) % 256) << 8)
                            + (Integer.parseInt(parts[2]) % 256);
                } catch (Exception e) {
                    log.log(Level.WARNING, "e=" + e);
                }
            } else {
                log.log(Level.WARNING, "parts.length=" + parts.length + ";ip=" + ip);
            }
        }
        return result;
    }

    public static long ip2LIP(String ip) {
        long result = ip2Key(ip);
        if (result != 0) {
            try {
                result += Integer.parseInt(ip.split(IP_SPLIT)[3]);
            } catch (Exception e) {
                log.log(Level.WARNING, "e=" + e);
            }
        }
        return result;
    }

    public static boolean isIpMath(String ip) {
        return ip2LIP(ip) != 0 || IP_ZERO.equals(ip);
    }

//    public static String getIP(HttpServletRequest request) {
//        String ip = request.getHeader("x-real-ip");
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("x-forwarded-for");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getRemoteAddr();
//        }
//        if (ip != null && !ip.isEmpty()) {
//            ip = ip.split(",")[0];
//        }
//        return ip;
//    }

}

package www.mys.com.utils;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPUtils {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(IPUtils.class);
    private static final String IP_SPLIT = "\\.";
    private static final String IP_ZERO = "0.0.0.0";
    private static Pattern pattern = Pattern.compile("((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})(\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})){3}");
    private static final String URL_GET_HTTPS_IP = "https://api.ip.sb/ip";
    //    private static final String URL_GET_HTTP_IP = "http://api.ip.sb/ip";
    private static final String URL_GET_HTTP_IP = "http://ip.t88.biz/api/hookweb/getIP";
    private static final String PROXY_IP_BASE = "96.30.192.187";
    private static final String URL_RPOXY_IP = "http://" + PROXY_IP_BASE + ":1100/api/getProxyIP/%s/%s";

    public static void main(String[] args) {
//        System.out.println("ip=" + getProxyIP(null, false));
//        System.out.println("ip=" + getProxyIP(null, true));
//        System.out.println("ip=" + getProxyIP(new Proxy(Proxy.Type.HTTP
//                , new InetSocketAddress("85.174.227.52", 59280)), false));
//        System.out.println("ip=" + getProxyIP(new Proxy(Proxy.Type.HTTP
//                , new InetSocketAddress("85.174.227.52", 59280)), true));
//        System.out.println("checkProxy=" + checkProxy(new Proxy(Proxy.Type.HTTP
//                , new InetSocketAddress("88.198.35.181", 33951)), false));
//        System.out.println("checkProxy=" + checkProxy(new Proxy(Proxy.Type.HTTP
//                , new InetSocketAddress("85.174.227.52", 59280)), false));
        System.out.println("checkProxy=" + proxyCheckProxy("88.198.35.181", 33951));
    }

    private static int times;

    public static boolean proxyCheckProxy(String host, Integer port) {
        if (StringUtils.isEmpty(host) || port < 1) {
            return false;
        }
        String proxyIP = proxyProxyIP(host, port);
        if (PROXY_IP_BASE.equals(proxyIP)) {
            log.error("proxy error.proxyIP=" + proxyIP + ";currentIP=" + PROXY_IP_BASE + ";host=" + host + ";port=" + port);
            return false;
        }
        if (times++ % 30 == 0) {
            log.error("proxy error.proxyIP=" + proxyIP + ";currentIP=" + PROXY_IP_BASE + ";host=" + host + ";port=" + port);
        }
        return true;
    }

    public static String proxyProxyIP(String host, Integer port) {
        final StringBuilder result = new StringBuilder();
        www.mys.com.common.utils.net.HttpUtils.getInstance().getURLResponse(String.format(URL_RPOXY_IP, host, port)
                , null, new www.mys.com.common.utils.net.HttpUtils.IWebCallback() {
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

                    }
                });
        return result.toString();
    }

    public static boolean checkProxy(Proxy proxy, boolean isHttps) {
        return checkProxy(proxy, isHttps, 2 * 1000);
    }

    public static boolean checkProxy(Proxy proxy, boolean isHttps, int timeOut) {
        if (proxy == null) {
            return false;
        }
        String proxyIP = getProxyIP(proxy, isHttps, timeOut);
        String currentIP = getProxyIP(null, isHttps, timeOut);
        boolean result = !proxyIP.isEmpty() && !proxyIP.equals(currentIP);
        if (!result) {
            log.error("proxy error.proxyIP=" + proxyIP + ";currentIP=" + currentIP + ";proxy=" + proxy.address());
        }
        log.error("proxy error.proxyIP=" + proxyIP + ";currentIP=" + currentIP + ";proxy=" + proxy.address());
        return result;
    }

    public static String getProxyIP(Proxy proxy, boolean isHttps) {
        return getProxyIP(proxy, isHttps, 5 * 1000);
    }

    public static String getProxyIP(Proxy proxy, boolean isHttps, int timeOut) {
        final StringBuilder result = new StringBuilder();
        www.mys.com.common.utils.net.HttpUtils.getInstance().getURLResponse(isHttps ? URL_GET_HTTPS_IP : URL_GET_HTTP_IP
                , null, proxy, new www.mys.com.common.utils.net.HttpUtils.IWebCallback() {
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

                    }
                }, timeOut);
        return result.toString();
    }

    public static String getIP(HttpServletRequest request) {
        String ip = request.getHeader("x-real-ip");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && !ip.isEmpty()) {
            ip = ip.split(",")[0];
        }
        return ip;
    }


    public static int ip2Key(String ip) {
        int result = 0;
        if (ip != null && !ip.isEmpty()) {
            String[] parts = ip.split(IP_SPLIT);
            if (parts.length == 4) {
                try {
                    result = ((Integer.valueOf(parts[0]) % 256) << 16) + ((Integer.valueOf(parts[1]) % 256) << 8) + (Integer.valueOf(parts[2]) % 256);
                } catch (Exception e) {
                    log.error("e=" + e);
                }
            } else {
                log.error("parts.length=" + parts.length + ";ip=" + ip);
            }
        }
        return result;
    }

    public static long ip2LIP(String ip) {
        long result = ip2Key(ip);
        if (result != 0) {
            try {
                result += Integer.valueOf(ip.split(IP_SPLIT)[3]);
            } catch (Exception e) {
                log.error("e=" + e);
            }
        }
        return result;
    }

    public static boolean isIpMath(String ip) {
        return ip2LIP(ip) != 0 || IP_ZERO.equals(ip);
    }

}

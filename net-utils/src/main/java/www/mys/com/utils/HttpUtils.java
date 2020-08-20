package www.mys.com.utils;

import www.mys.com.common.utils.RegularUtils;
import www.mys.com.common.utils.file.CMDUtils;
import www.mys.com.common.utils.file.CloseUtils;

import javax.net.ssl.*;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpUtils {

     public static int pingHost(String host) {
        return RegularUtils.getTimeOut(CMDUtils.run("ping " + RegularUtils.getHost(host, false) + " -n 1"));
    }

    public static final String HTTPS = "https";
    public static final String GET = "GET";
    public static final String POST = "POST";
    private static HttpUtils httpUtils;

    private HttpUtils() {
    }

    public static HttpUtils getInstance() {
        if (httpUtils == null) {
            httpUtils = new HttpUtils();
        }
        return httpUtils;
    }

    public interface IWebCallback {

        void onCallback(int status, String message, Map<String, List<String>> heard, byte[] data);

        void onFail(int status, String message);

    }

    public String getURLStrResponse(String urlString, HashMap<String, String> heads) {
        byte[] result = getURLResponse(urlString, heads);
        return result == null ? null : new String(result, StandardCharsets.UTF_8);
    }

    public byte[] getURLResponse(String urlString, HashMap<String, String> heads) {
        return getURLResponse(urlString, heads, 5 * 1000);
    }

    public byte[] getURLResponse(String urlString, HashMap<String, String> heads, int timeOut) {
        byte[] result = null;
        if (urlString != null) {
            HttpURLConnection conn = null; //连接对象
            InputStream is = null;
            ByteArrayOutputStream baos = null;
            try {
                URL url = new URL(urlString); //URL对象
                if (urlString.startsWith(HTTPS)) {
                    ignoreSsl();
                    conn = (HttpsURLConnection) url.openConnection();
                } else {
                    conn = (HttpURLConnection) url.openConnection();
                }
                conn.setConnectTimeout(timeOut);
                conn.setDoOutput(true);
                conn.setRequestMethod(GET);
                if (heads != null) {
                    for (String key : heads.keySet()) {
                        conn.addRequestProperty(key, heads.get(key));
                    }
                }
                is = conn.getInputStream();   //获取输入流，此时才真正建立链接
                baos = new ByteArrayOutputStream();
                byte[] temp = new byte[1024];
                int len;
                while ((len = is.read(temp)) != -1) {
                    baos.write(temp, 0, len);
                }
                result = baos.toByteArray();
            } catch (Exception e) {
            } finally {
                CloseUtils.closeSilently(is);
                CloseUtils.closeSilently(baos);
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }
        return result;
    }

    public void getURLResponse(String urlString, HashMap<String, String> heads, IWebCallback iWebCallback) {
        getURLResponse(urlString, heads, null, iWebCallback);
    }

    public void getURLResponse(String urlString, HashMap<String, String> heads, Proxy proxy, IWebCallback iWebCallback) {
        getURLResponse(urlString, heads, proxy, iWebCallback, 5 * 1000);
    }

    public void getURLResponse(String urlString, HashMap<String, String> heads, Proxy proxy
            , IWebCallback iWebCallback, int timeOut) {
        getURLResponse(urlString, heads, proxy, iWebCallback, timeOut, true);
    }

    public void getURLResponse(String urlString, HashMap<String, String> heads, Proxy proxy
            , IWebCallback iWebCallback, int timeOut, boolean followRedirect) {
        if (urlString != null) {
            HttpURLConnection conn = null; //连接对象
            InputStream is = null;
            ByteArrayOutputStream baos = null;
            try {
                URL url = new URL(urlString); //URL对象
                if (proxy == null) {
                    if (urlString.startsWith(HTTPS)) {
                        ignoreSsl();
                        conn = (HttpsURLConnection) url.openConnection();
                    } else {
                        conn = (HttpURLConnection) url.openConnection();
                    }
                } else {
                    if (urlString.startsWith(HTTPS)) {
                        ignoreSsl();
                        conn = (HttpsURLConnection) url.openConnection(proxy);
                    } else {
                        conn = (HttpURLConnection) url.openConnection(proxy);
                    }
                }
                conn.setConnectTimeout(timeOut);
                conn.setDoOutput(true);
                conn.setInstanceFollowRedirects(followRedirect);
                conn.setRequestMethod(GET);
                if (heads != null) {
                    for (String key : heads.keySet()) {
                        conn.addRequestProperty(key, heads.get(key));
                    }
                }
                is = conn.getInputStream();   //获取输入流，此时才真正建立链接
                baos = new ByteArrayOutputStream();
                byte[] temp = new byte[1024];
                int len;
                while ((len = is.read(temp)) != -1) {
                    baos.write(temp, 0, len);
                }
                if (iWebCallback != null) {
                    iWebCallback.onCallback(conn.getResponseCode(), conn.getResponseMessage(), conn.getHeaderFields(), baos.toByteArray());
                }
            } catch (Exception e) {
                int code = 600;
                try {
                    code = conn == null ? 600 : conn.getResponseCode();
                } catch (Exception e1) {
                }
                if (iWebCallback != null) {
                    iWebCallback.onFail(code, e.toString());
                }
            } finally {
                CloseUtils.closeSilently(is);
                CloseUtils.closeSilently(baos);
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }
    }

    public byte[] postURLResponse(String urlString, HashMap<String, String> headers, byte[] postData) {
        return postURLResponse(urlString, headers, postData, 5 * 1000);
    }

    public byte[] postURLResponse(String urlString, HashMap<String, String> headers, byte[] postData, int timeOut) {
        byte[] result = null;
        if (urlString != null) {
            HttpURLConnection conn = null; //连接对象
            InputStream is = null;
            ByteArrayOutputStream baos = null;
            try {
                URL url = new URL(urlString); //URL对象
                if (urlString.startsWith(HTTPS)) {
                    ignoreSsl();
                    conn = (HttpsURLConnection) url.openConnection();
                } else {
                    conn = (HttpURLConnection) url.openConnection();
                }
                conn.setConnectTimeout(timeOut);
                conn.setDoOutput(true);
                conn.setRequestMethod(POST); //使用post请求
                conn.setRequestProperty("Charsert", "UTF-8");
                if (headers != null) {
                    for (Map.Entry<String, String> temp : headers.entrySet()) {
                        conn.setRequestProperty(temp.getKey(), temp.getValue());
                    }
                }
                conn.getOutputStream().write(postData);
                is = conn.getInputStream();   //获取输入流，此时才真正建立链接
                baos = new ByteArrayOutputStream();
                byte[] temp = new byte[1024];
                int len;
                while ((len = is.read(temp)) != -1) {
                    baos.write(temp, 0, len);
                }
                result = baos.toByteArray();
            } catch (Exception e) {
            } finally {
                CloseUtils.closeSilently(is);
                CloseUtils.closeSilently(baos);
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }
        return result;
    }

    public void postURLResponse(String urlString, HashMap<String, String> headers,
                                byte[] postData, IWebCallback iWebCallback) {
        postURLResponse(urlString, headers, postData, iWebCallback, 5 * 1000);
    }

    public void postURLResponse(String urlString, HashMap<String, String> headers,
                                byte[] postData, IWebCallback iWebCallback, int timeOut) {
        if (urlString != null) {
            HttpURLConnection conn = null; //连接对象
            InputStream is = null;
            ByteArrayOutputStream baos = null;
            try {
                URL url = new URL(urlString); //URL对象
                if (urlString.startsWith(HTTPS)) {
                    ignoreSsl();
                    conn = (HttpsURLConnection) url.openConnection();
                } else {
                    conn = (HttpURLConnection) url.openConnection();
                }
                conn.setConnectTimeout(timeOut);
                conn.setDoOutput(true);
                conn.setRequestMethod(POST); //使用post请求
                conn.setRequestProperty("Charsert", "UTF-8");
                if (headers != null) {
                    for (Map.Entry<String, String> temp : headers.entrySet()) {
                        conn.setRequestProperty(temp.getKey(), temp.getValue());
                    }
                }
                conn.getOutputStream().write(postData);
                is = conn.getInputStream();   //获取输入流，此时才真正建立链接
                baos = new ByteArrayOutputStream();
                byte[] temp = new byte[1024];
                int len;
                while ((len = is.read(temp)) != -1) {
                    baos.write(temp, 0, len);
                }
                if (iWebCallback != null) {
                    iWebCallback.onCallback(conn.getResponseCode(), conn.getResponseMessage(), conn.getHeaderFields(), baos.toByteArray());
                }
            } catch (Exception e) {
                int code = 600;
                try {
                    code = conn == null ? 600 : conn.getResponseCode();
                } catch (Exception e1) {
                }
                if (iWebCallback != null) {
                    iWebCallback.onFail(code, e.toString());
                }
            } finally {
                CloseUtils.closeSilently(is);
                CloseUtils.closeSilently(baos);
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }
    }

    public static void trustAllHttpsCertificates() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[1];
        TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }

    public static void ignoreSsl() throws Exception {
        HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String urlHostName, SSLSession session) {
                return true;
            }
        };
        trustAllHttpsCertificates();
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }

    public static class miTM implements TrustManager, X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted(X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(X509Certificate[] certs) {
            return true;
        }

        public void checkServerTrusted(X509Certificate[] certs, String authType)
                throws CertificateException {
            return;
        }

        public void checkClientTrusted(X509Certificate[] certs, String authType)
                throws CertificateException {
            return;
        }
    }
}

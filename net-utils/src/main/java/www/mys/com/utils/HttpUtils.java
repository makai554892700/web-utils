package www.mys.com.utils;

import javax.net.ssl.*;
import java.io.*;
import java.io.InputStream;
import java.net.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpUtils {

    private static final Logger log = Logger.getLogger(HttpUtils.class.getName());

    private static final String HTTPS = "https";
    private static final String GET = "GET";
    private static final String PUT = "PUT";
    private static final String POST = "POST";

    public interface IWebCallback {

        void onCallback(int status, String message, Map<String, List<String>> heard, byte[] data);

        void onFail(int status, String message);

    }

    public static String getURLStrResponse(String urlString, HashMap<String, String> heads, Charset charset) {
        return new String(getURLResponse(urlString, heads), charset);
    }

    public static String getURLStrResponse(String urlString, HashMap<String, String> heads) {
        return new String(getURLResponse(urlString, heads), StandardCharsets.UTF_8);
    }

    public static byte[] getURLResponse(String urlString, HashMap<String, String> heads) {
        return getURLResponse(urlString, heads, 5 * 1000);
    }

    public static byte[] getURLResponse(String urlString, HashMap<String, String> heads, int timeOut) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        commonRequest(GET, urlString, heads, null, new IWebCallback() {
            @Override
            public void onCallback(int status, String message, Map<String, List<String>> heard, byte[] data) {
                try {
                    byteArrayOutputStream.write(data);
                } catch (Exception e) {
                    log.log(Level.WARNING, "write data error.e=" + e);
                }
            }

            @Override
            public void onFail(int status, String message) {
                log.log(Level.WARNING, "getURLResponse onFail.status=" + status + ";message=" + message);
            }
        }, timeOut, null, false);
        byte[] result = byteArrayOutputStream.toByteArray();
        closeSilently(byteArrayOutputStream);
        return result;
    }

    public static void getURLResponse(String urlString, HashMap<String, String> heads, IWebCallback iWebCallback) {
        getURLResponse(urlString, heads, null, iWebCallback);
    }

    public static void getURLResponse(String urlString, HashMap<String, String> heads, Proxy proxy, IWebCallback iWebCallback) {
        getURLResponse(urlString, heads, proxy, iWebCallback, 5 * 1000);
    }

    public static void getURLResponse(String urlString, HashMap<String, String> heads, Proxy proxy
            , IWebCallback iWebCallback, int timeOut) {
        getURLResponse(urlString, heads, proxy, iWebCallback, timeOut, true);
    }

    public static void getURLResponse(String urlString, HashMap<String, String> heads, Proxy proxy
            , IWebCallback iWebCallback, int timeOut, boolean followRedirect) {
        commonRequest(GET, urlString, heads, null, iWebCallback, timeOut, proxy, followRedirect);
    }

    public static byte[] postURLResponse(String urlString, HashMap<String, String> headers, byte[] postData) {
        return postURLResponse(urlString, headers, postData, 5 * 1000);
    }

    public static byte[] postURLResponse(String urlString, HashMap<String, String> headers, byte[] postData, int timeOut) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        commonRequest(POST, urlString, headers, postData, new IWebCallback() {
            @Override
            public void onCallback(int status, String message, Map<String, List<String>> heard, byte[] data) {
                try {
                    byteArrayOutputStream.write(data);
                } catch (Exception e) {
                    log.log(Level.WARNING, "write data error.e=" + e);
                }
            }

            @Override
            public void onFail(int status, String message) {
                log.log(Level.WARNING, "getURLResponse postURLResponse.status=" + status + ";message=" + message);
            }
        }, timeOut, null, false);
        byte[] result = byteArrayOutputStream.toByteArray();
        closeSilently(byteArrayOutputStream);
        return result;
    }

    public static void postURLResponse(String urlString, HashMap<String, String> headers
            , byte[] postData, IWebCallback iWebCallback) {
        postURLResponse(urlString, headers, postData, iWebCallback, 5 * 1000);
    }

    public static void postURLResponse(String urlString, HashMap<String, String> headers
            , byte[] postData, IWebCallback iWebCallback, int timeOut) {
        commonRequest(POST, urlString, headers, postData, iWebCallback, timeOut, null, false);
    }


    private static void commonRequest(String requestType, String urlString, HashMap<String, String> headers
            , byte[] postData, IWebCallback iWebCallback, int timeOut, Proxy proxy, boolean followRedirect) {
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
                conn.setRequestMethod(requestType);
                if (headers != null) {
                    for (Map.Entry<String, String> temp : headers.entrySet()) {
                        conn.setRequestProperty(temp.getKey(), temp.getValue());
                    }
                }
                switch (requestType) {
                    case POST:
                    case PUT:
                        if (postData != null) {
                            conn.getOutputStream().write(postData);
                        }
                        break;
                }
                is = conn.getInputStream();
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
                closeSilently(is);
                closeSilently(baos);
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

    public static void closeSilently(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                log.log(Level.WARNING, "e=" + e);
            }
        }
    }

}

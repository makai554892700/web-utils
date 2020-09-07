package www.mys.com.utils;

import javax.net.ssl.*;
import java.io.*;
import java.io.InputStream;
import java.net.*;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpUtils {

    private static final Logger log = Logger.getLogger(HttpUtils.class.getName());

    private static final String HTTPS = "https";
    private static final String GET = "GET";
    private static final String DELETE = "DELETE";
    private static final String PUT = "PUT";
    private static final String POST = "POST";

    public interface IWebCallback {

        void onCallback(int status, String message, Map<String, List<String>> heard, byte[] data);

        void onFail(int status, String message);

    }

    public static byte[] getURLResponse(String urlString, HashMap<String, String> heads) {
        return getURLResponse(GET, urlString, heads, 5 * 1000);
    }

    public static byte[] deleteURLResponse(String urlString, HashMap<String, String> heads) {
        return getURLResponse(DELETE, urlString, heads, 5 * 1000);
    }

    public static byte[] putURLResponse(String urlString, HashMap<String, String> headers, byte[] postData) {
        return postURLResponse(PUT, urlString, headers, postData, 5 * 1000);
    }

    public static byte[] postURLResponse(String urlString, HashMap<String, String> headers, byte[] postData) {
        return postURLResponse(POST, urlString, headers, postData, 5 * 1000);
    }

    public static void getURLResponse(String urlString, HashMap<String, String> heads, Proxy proxy
            , IWebCallback iWebCallback) {
        getURLResponse(urlString, heads, proxy, iWebCallback, 5 * 1000);
    }

    public static void postURLResponse(String urlString, HashMap<String, String> headers
            , byte[] postData, IWebCallback iWebCallback) {
        postURLResponse(urlString, headers, postData, iWebCallback, 5 * 1000);
    }

    public static byte[] getURLResponse(String requestType, String urlString, HashMap<String, String> heads, int timeOut) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        commonRequest(requestType, urlString, heads, "".getBytes(), new IWebCallback() {
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

    public static void getURLResponse(String urlString, HashMap<String, String> heads, Proxy proxy
            , IWebCallback iWebCallback, int timeOut) {
        getURLResponse(urlString, heads, proxy, iWebCallback, timeOut, true);
    }

    public static void getURLResponse(String urlString, HashMap<String, String> heads, Proxy proxy
            , IWebCallback iWebCallback, int timeOut, boolean followRedirect) {
        commonRequest(GET, urlString, heads, "".getBytes(), iWebCallback, timeOut, proxy, followRedirect);
    }

    public static void postURLResponse(String urlString, HashMap<String, String> headers
            , byte[] postData, IWebCallback iWebCallback, int timeOut) {
        commonRequest(POST, urlString, headers, postData, iWebCallback, timeOut, null, false);
    }

    public static byte[] postURLResponse(String requestType, String urlString, HashMap<String, String> headers
            , byte[] postData, int timeOut) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        commonRequest(requestType, urlString, headers, postData, new IWebCallback() {
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

    public static void commonRequest(String requestType, String urlString, HashMap<String, String> headers
            , byte[] data, IWebCallback iWebCallback, int timeOut, Proxy proxy, boolean followRedirect) {
        File tempFile;
        try {
            tempFile = File.createTempFile(String.valueOf(System.currentTimeMillis()) + new Random().nextInt(1000)
                    , ".io");
        } catch (Exception e) {
            log.log(Level.WARNING, "create temp file error.e=" + e);
            return;
        }
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(tempFile);
            fileOutputStream.write(data == null ? "".getBytes() : data);
            fileOutputStream.flush();
        } catch (Exception e) {
            log.log(Level.WARNING, "get file output stream error.e=" + e);
            tempFile.delete();
            return;
        }
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(tempFile);
        } catch (Exception e) {
            log.log(Level.WARNING, "get file input stream error.e=" + e);
            tempFile.delete();
            return;
        }
        commonRequest(requestType, urlString, headers, fileInputStream, iWebCallback, timeOut, proxy, followRedirect);
        closeSilently(fileInputStream);
        tempFile.delete();
    }

    public static void commonRequest(String requestType, String urlString, HashMap<String, String> headers
            , InputStream dataIs, IWebCallback iWebCallback, int timeOut, Proxy proxy, boolean followRedirect) {
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

                byte[] temp = new byte[1024];
                int len;
                switch (requestType) {
                    case POST:
                    case PUT:
                        if (dataIs != null) {
                            while ((len = dataIs.read(temp)) != -1) {
                                conn.getOutputStream().write(temp, 0, len);
                            }
                        }
                        break;
                }
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
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

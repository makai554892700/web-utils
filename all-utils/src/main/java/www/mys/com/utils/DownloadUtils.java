package www.mys.com.utils;

import javax.net.ssl.HttpsURLConnection;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DownloadUtils {

    private static final Logger log = Logger.getLogger(DownloadUtils.class.getName());

    private static final String HTTPS = "https";
    private static final String GET = "get";

    public static UrlResponse url2InputStream(String urlString) {
        return url2InputStream(urlString, null, 5 * 1000);
    }

    public static UrlResponse url2InputStream(String urlString, HashMap<String, String> heads) {
        return url2InputStream(urlString, heads, 5 * 1000);
    }

    public static UrlResponse url2InputStream(String urlString, int timeOut) {
        return url2InputStream(urlString, null, timeOut);
    }

    public static UrlResponse url2InputStream(String urlString, HashMap<String, String> heads, int timeOut) {
        UrlResponse result = null;
        if (!StringUtils.isEmpty(urlString)) {
            HttpURLConnection conn = null; //连接对象
            InputStream inputStream;
            try {
                URL url = new URL(urlString); //URL对象
                if (urlString.startsWith(HTTPS)) {
                    HttpUtils.ignoreSsl();
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
                inputStream = conn.getInputStream();
                String fileName = conn.getHeaderField("Content-Disposition");
                if (StringUtils.isEmpty(fileName)) {
                    String[] tempStr = urlString.split("/");
                    fileName = tempStr[tempStr.length - 1];
                } else {
                    int start = fileName.indexOf("filename=\"");
                    int end = fileName.indexOf("\"");
                    if (end > start) {
                        fileName = fileName.substring(start, end);
                    }
                }
                result = new UrlResponse(conn, inputStream, fileName
                        , conn.getContentType(), conn.getContentLengthLong());
            } catch (Exception e) {
                log.log(Level.WARNING, "url2InputStream error.e=" + e);
            }
        }
        return result;
    }

    public static class UrlResponse {
        private HttpURLConnection conn;
        private InputStream inputStream;
        private String originalFilename;
        private String contentType;
        private long size;

        public UrlResponse() {
        }

        public UrlResponse(HttpURLConnection conn, InputStream inputStream, String originalFilename, String contentType, long size) {
            this.inputStream = inputStream;
            this.originalFilename = originalFilename;
            this.contentType = contentType;
            this.size = size;
        }

        public void endOption() {
            if (inputStream != null) {
                CloseUtils.closeSilently(inputStream);
            }
            if (conn != null) {
                try {
                    conn.disconnect();
                } catch (Exception e) {
                }
            }
        }

        public HttpURLConnection getConn() {
            return conn;
        }

        public void setConn(HttpURLConnection conn) {
            this.conn = conn;
        }

        public InputStream getInputStream() {
            return inputStream;
        }

        public void setInputStream(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        public String getOriginalFilename() {
            return originalFilename;
        }

        public void setOriginalFilename(String originalFilename) {
            this.originalFilename = originalFilename;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }

        @Override
        public String toString() {
            return "UrlResponse{" +
                    "conn=" + conn +
                    ", inputStream=" + inputStream +
                    ", originalFilename='" + originalFilename + '\'' +
                    ", contentType='" + contentType + '\'' +
                    ", size=" + size +
                    '}';
        }
    }

}

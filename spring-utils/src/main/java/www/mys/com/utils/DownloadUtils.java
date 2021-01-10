package www.mys.com.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class DownloadUtils {

    private static final String JSON_APPLICATION = "application/json";

    private static final Logger log = LoggerFactory.getLogger(DownloadUtils.class.getName());

    public static boolean downloadFile(HttpServletRequest request
            , HttpServletResponse response, InputStream inputStream
            , String fileName, long fileLen, String contentType
            , boolean closeInputStream, long maxAge) throws Exception {
        fileName = fixStr(request, fileName);
        if (!StringUtils.isEmpty(contentType)) {
            response.setContentType(contentType);
        } else {
            response.setContentType("application/octet-stream");
        }
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setHeader("X-Actual-Content-Length", String.valueOf(fileLen));
        cors(response, maxAge);
        OutputStream out = response.getOutputStream();
        byte[] buffer = new byte[102400];
        int len;
        try {
            while ((len = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            log.info("download file error.e=" + e);
            return false;
        } finally {
            if (closeInputStream) {
                inputStream.close();
            }
            out.flush();
            out.close();
        }
        return true;
    }


    public static boolean returnMessage(HttpServletRequest request, HttpServletResponse response
            , String message, boolean cors, long maxAge) throws Exception {
        message = fixStr(request, message);
        response.setContentType(JSON_APPLICATION);
        OutputStream out = response.getOutputStream();
        byte[] buffer = message.getBytes();
        try {
            out.write(buffer, 0, buffer.length);
            out.flush();
            return false;
        } catch (Exception e) {
            log.info("return message error.e=" + e);
        } finally {
            out.close();
        }
        if (cors) {
            cors(response, maxAge);
        }
        return true;
    }

    private static String fixStr(HttpServletRequest request, String str) throws Exception {
        if (request.getHeader("User-Agent").toUpperCase().contains("MSIE") ||
                request.getHeader("User-Agent").toUpperCase().contains("TRIDENT")
                || request.getHeader("User-Agent").toUpperCase().contains("EDGE")) {
            str = java.net.URLEncoder.encode(str, "UTF-8");
        } else {
            str = new String(str.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        }
        return str;
    }

    public static void cors(HttpServletResponse response, long maxAge) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, HEAD");
        if (maxAge > 0) {
            response.setHeader("Access-Control-Max-Age", String.valueOf(maxAge));
            response.setHeader("Cache-Control", "max-age=" + maxAge);
        }
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Expose-Headers", "Cache-Control, Accept-Ranges, Content-Encoding, Content-Length, Content-Range, X-Actual-Content-Length, Content-Disposition");
    }
}

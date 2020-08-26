package www.mys.com.utils;

import com.alibaba.fastjson.JSON;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WeChatMiniUtils {
    private static final Logger log = Logger.getLogger(WeChatMiniUtils.class.getName());
    private static final String GET_SESSION_KEY_FORMAT = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
    private static final String GET_ACCESS_TOKEN_FORMAT = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    public static ResponseWXMini getSessionKey(String appId, String secret, String authCode) {
        String host = String.format(GET_SESSION_KEY_FORMAT, appId, secret, authCode);
        String response = HttpUtils.getURLStrResponse(host, null);
        try {
            return JSON.parseObject(response, ResponseWXMini.class);
        } catch (Exception e) {
            log.log(Level.WARNING, "Format response error.e=" + e + ";response=" + response);
            return null;
        }
    }

    public static String decryptData(String encryptedData, String sessionKey, String iv) {
        byte[] dataByte = Base64.decode(encryptedData);
        byte[] keyByte = Base64.decode(sessionKey);
        byte[] ivByte = Base64.decode(iv);
        try {
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + 1;
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                return new String(resultByte, StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            log.log(Level.WARNING, "decryptData error.e=" + e);
        }
        return null;
    }

    public static AccessTokenResponse getAccessToken(String appid, String secret) {
        String url = String.format(GET_ACCESS_TOKEN_FORMAT, appid, secret);
        String response = HttpUtils.getURLStrResponse(url, null);
        return JSON.parseObject(response, AccessTokenResponse.class);
    }


    public static class AccessTokenResponse {
        private String openid;
        private String sessionKey;
        private String unionid;
        private String errcode;
        private String errmsg;

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getSessionKey() {
            return sessionKey;
        }

        public void setSessionKey(String sessionKey) {
            this.sessionKey = sessionKey;
        }

        public String getUnionid() {
            return unionid;
        }

        public void setUnionid(String unionid) {
            this.unionid = unionid;
        }

        public String getErrcode() {
            return errcode;
        }

        public void setErrcode(String errcode) {
            this.errcode = errcode;
        }

        public String getErrmsg() {
            return errmsg;
        }

        public void setErrmsg(String errmsg) {
            this.errmsg = errmsg;
        }

        @Override
        public String toString() {
            return "WXResponse{" +
                    "openid='" + openid + '\'' +
                    ", sessionKey='" + sessionKey + '\'' +
                    ", unionid='" + unionid + '\'' +
                    ", errcode='" + errcode + '\'' +
                    ", errmsg='" + errmsg + '\'' +
                    '}';
        }
    }

}

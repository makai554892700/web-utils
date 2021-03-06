package www.mys.com.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JWTUtils {

    private static final Logger log = Logger.getLogger(JWTUtils.class.getName());

    public static ResponseToken createToken(String privateKeyStr, long expirationTime
            , Map<String, Object> claims) throws Exception {
        return generateToken(privateKeyStr, expirationTime, claims);
    }

    public static ResponseToken refreshToken(String privateKey, String publicKey, long expiration
            , String oldToken) throws BaseException {
        Claims claims = commonGetClaims(publicKey, oldToken, true);
        try {
            return createToken(privateKey, expiration, claims);
        } catch (Exception e) {
            throw new BaseException(new ExceptionInfo() {
                @Override
                public String getMsg() {
                    return "create token error.";
                }

                @Override
                public Integer getCode() {
                    return -3;
                }
            });
        }
    }

    public static String getUsernameFromToken(String publicKeyStr, String token, boolean checkTime) {
        Claims claims = commonGetClaims(publicKeyStr, token, checkTime);
        return claims.getSubject();
    }

    public static String getValueFromToken(String publicKeyStr, String token, String key, boolean checkTime) {
        return getValueFromToken(commonGetClaims(publicKeyStr, token, checkTime), key);
    }

    public static String getValueFromToken(Claims claims, String key) {
        Object result = claims.get(key);
        if (result == null) {
            return null;
        }
        return result.toString();
    }

    public static boolean isTokenExpired(String publicKey, String token) {
        try {
            return isExpired(getClaimsFromToken(publicKey, token));
        } catch (Exception e) {
            log.log(Level.WARNING, "isTokenExpired error.e=" + e);
            return false;
        }
    }

    public static Claims commonGetClaims(String publicKeyStr, String token, boolean checkTime) {
        Claims claims;
        try {
            claims = getClaimsFromToken(publicKeyStr, token);
        } catch (Exception e) {
            log.log(Level.WARNING, "getUsernameFromToken error.e=" + e);
            throw new BaseException(new ExceptionInfo() {
                @Override
                public String getMsg() {
                    return "token error.";
                }

                @Override
                public Integer getCode() {
                    return -3;
                }
            });
        }
        if (checkTime && isExpired(claims)) {
            throw new BaseException(new ExceptionInfo() {
                @Override
                public String getMsg() {
                    return "token out of time.";
                }

                @Override
                public Integer getCode() {
                    return -3;
                }
            });
        }
        return claims;
    }

    private static boolean isExpired(Claims claims) {
        return claims.getExpiration() == null || claims.getExpiration().before(new Date());
    }

    private static Claims getClaimsFromToken(String publicKeyStr, String jwtStr) throws Exception {
        PublicKey publicKey = RsaUtils.getPublicKey(publicKeyStr.getBytes());
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(jwtStr).getBody();
    }

    private static ResponseToken generateToken(String privateKeyStr, long expirationTime, Map<String, Object> claims) throws Exception {
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);
        PrivateKey privateKey = RsaUtils.getPrivateKey(privateKeyStr.getBytes());
        String tokenStr = Jwts.builder().setClaims(claims)
                .setExpiration(expirationDate)
//                .signWith(SignatureAlgorithm.HS512, privateKey)
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
        return new ResponseToken(expirationDate, tokenStr);
    }

    public static class ResponseToken implements Serializable {

        private Date expireTime;
        private String token;

        public ResponseToken() {
        }

        public ResponseToken(Date expireTime, String token) {
            this.expireTime = expireTime;
            this.token = token;
        }

        public Date getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(Date expireTime) {
            this.expireTime = expireTime;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        @Override
        public String toString() {
            return "ResponseToken{" +
                    "expireTime=" + expireTime +
                    ", token='" + token + '\'' +
                    '}';
        }
    }

}

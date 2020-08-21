package www.mys.com.utils;

import com.alibaba.fastjson.JSON;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WeChatUtils {

    private static final Logger log = Logger.getLogger(WeChatUtils.class.getName());
    private static final String GET_ACCESS_TOKEN_FORMAT = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
    private static final String GET_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s";
    private static final String GET_FIRST_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    private static final String GET_ORDER = "https://api.mch.weixin.qq.com/pay/orderquery";

    public static boolean checkWXOrder(String appId, String key, String mchId, String orderNumber) {
        WXPayResponse wxPayResponse = getWXPayResponseByOutTradeNo(appId, key, mchId, orderNumber);
//        log.log(Level.WARNING,"wxPayResponse=" + wxPayResponse);
        return wxPayResponse != null && "SUCCESS".equals(wxPayResponse.getReturnCode())
                && "SUCCESS".equals(wxPayResponse.getResultCode())
                && "SUCCESS".equals(wxPayResponse.getTradeState());
    }

    public static String getTransactionIdByOutTradeNo(String appId, String key, String mchId, String outTradeNo) {
        WXPayResponse wxPayResponse = getWXPayResponseByOutTradeNo(appId, key, mchId, outTradeNo);
        if (wxPayResponse != null) {
            return wxPayResponse.getTransactionId();
        }
        return null;
    }

    private static WXPayResponse getWXPayResponseByOutTradeNo(String appId, String key, String mchId, String outTradeNo) {
        SortedMap<String, String> temp = new TreeMap<String, String>() {
            {
                put("appid", appId);//应用ID
                put("mch_id", mchId);//商户号
                put("nonce_str", MD5Utils.MD5(String.valueOf(System.currentTimeMillis())
                        , false));//随机字符串
                put("out_trade_no", outTradeNo);//商户订单号
//                put("transaction_id", orderNumber);//商户订单号
            }
        };
        temp.put("sign", getSign(temp, key));
        String xml = getXml(temp);
        byte[] result = HttpUtils.postURLResponse(GET_ORDER
                , null, xml.getBytes());
        return XMLUtils.getObject(new String(result), WXPayResponse.class);
    }

    public static ResponseWX getWXResponse(String appId, String key, String mchId, String callBackUrl
            , String goodsName, String goodsDesc, String orderNumber, String price, String ip) {
        SortedMap<String, String> temp = new TreeMap<String, String>() {
            {
                put("appid", appId);//应用ID
                put("mch_id", mchId);//商户号
                put("nonce_str", MD5Utils.MD5(String.valueOf(System.currentTimeMillis()), false));//随机字符串
                put("body", goodsName);//商品描述
                put("attach", goodsDesc);//附加数据
                put("out_trade_no", orderNumber);//商户订单号
                put("total_fee", price);//总金额
                put("spbill_create_ip", ip);//终端IP
                put("notify_url", callBackUrl);//通知地址
                put("trade_type", "APP");//交易类型
            }
        };
        temp.put("sign", getSign(temp, key));
        String xml = getXml(temp);
        byte[] result = HttpUtils.postURLResponse(GET_FIRST_ORDER, null, xml.getBytes());
        log.log(Level.WARNING, "get result=" + new String(result));
        if (result.length == 0) {
            return null;
        }
        WXResponse response = XMLUtils.getObject(new String(result), WXResponse.class);
        if (response != null && "SUCCESS".equals(response.getReturnCode())
                && "SUCCESS".equals(response.getResultCode())
                && response.getPrepayId() != null) {
            ResponseWX responseWX = new ResponseWX();
            responseWX.setAppid(response.getAppid());
            responseWX.setNoncestr(response.getNonceStr());
            responseWX.setPackageName("Sign=WXPay");
            responseWX.setPartnerid(response.getMchId());
            responseWX.setTimestamp(String.valueOf(System.currentTimeMillis() / 1000));
            responseWX.setPrepayid(response.getPrepayId());
            responseWX.setSign(getSign(new TreeMap<String, String>() {
                {
                    put("appid", responseWX.getAppid());//应用ID
                    put("partnerid", responseWX.getPartnerid());//商户号
                    put("noncestr", responseWX.getNoncestr());//商品描述
                    put("package", responseWX.getPackageName());//随机字符串
                    put("prepayid", responseWX.getPrepayid());//商户订单号
                    put("timestamp", responseWX.getTimestamp());//附加数据
                }
            }, key));
            responseWX.setOrderNumber(orderNumber);
            return responseWX;
        }
        log.log(Level.WARNING, "getWXResponse=" + new String(result));
        return null;
    }

    private static String getXml(SortedMap<String, String> data) {
        StringBuilder result = new StringBuilder("<xml>");
        for (Map.Entry<String, String> kv : data.entrySet()) {
            result.append("<").append(kv.getKey()).append(">").append(kv.getValue())
                    .append("</").append(kv.getKey()).append(">");
        }
        return result.append("</xml>").toString();
    }

    private static String getSign(SortedMap<String, String> data, String key) {
        String result = null;
        if (data != null) {
            StringBuilder stringBuilder = new StringBuilder();
            boolean isFirst = true;
            for (Map.Entry<String, String> kv : data.entrySet()) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    stringBuilder.append("&");
                }
                stringBuilder.append(kv.getKey());
                stringBuilder.append("=");
                stringBuilder.append(kv.getValue());
            }
            stringBuilder.append("&key=").append(key);
            return MD5Utils.MD5(stringBuilder.toString(), false).toUpperCase();
        }
        return result;
    }

    public static WeChatUserResponse getUser(String appId, String secret, String authCode) {
        WeChatUserResponse result = null;
        String host = String.format(GET_ACCESS_TOKEN_FORMAT, appId, secret, authCode);
        byte[] response = HttpUtils.getURLResponse(host, null);
        if (response.length > 0) {
            AccessTokenResponse accessTokenResponse;
            try {
                accessTokenResponse = JSON.parseObject(new String(response), AccessTokenResponse.class);
            } catch (Exception e) {
                log.log(Level.WARNING, "Format response error.e=" + e + ";response=" + new String(response));
                return null;
            }
            if (accessTokenResponse != null && accessTokenResponse.accessToken != null) {
                response = HttpUtils.getURLResponse(host, null);
                if (response.length > 0) {
                    host = String.format(GET_USER_INFO, accessTokenResponse.accessToken, accessTokenResponse.openid);
                    response = HttpUtils.getURLResponse(host, null);
                    try {
                        result = JSON.parseObject(new String(response), WeChatUserResponse.class);
                        if (result.unionid == null) {
                            log.log(Level.WARNING, "Get user info error." + ";response=" + new String(response));
                            result = null;
                        }
                    } catch (Exception e) {
                        log.log(Level.WARNING, "Format response error.e=" + e + ";response=" + new String(response));
                    }
                }
            }
        }
        return result;
    }

    public static class WeChatUserResponse {
        private String openid;
        private String nickname;
        private int sex;
        private String language;
        private String city;
        private String province;
        private String country;
        private String headimgurl;
        private String unionid;

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getHeadimgurl() {
            return headimgurl;
        }

        public void setHeadimgurl(String headimgurl) {
            this.headimgurl = headimgurl;
        }

        public String getUnionid() {
            return unionid;
        }

        public void setUnionid(String unionid) {
            this.unionid = unionid;
        }

        @Override
        public String toString() {
            return "WeChatUserResponse{" +
                    "openid='" + openid + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", sex=" + sex +
                    ", language='" + language + '\'' +
                    ", city='" + city + '\'' +
                    ", province='" + province + '\'' +
                    ", country='" + country + '\'' +
                    ", headimgurl='" + headimgurl + '\'' +
                    ", unionid='" + unionid + '\'' +
                    '}';
        }
    }

    private static class AccessTokenResponse {
        private String accessToken;
        private int expiresIn;
        private String refreshToken;
        private String openid;
        private String scope;
        private String unionid;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public int getExpiresIn() {
            return expiresIn;
        }

        public void setExpiresIn(int expiresIn) {
            this.expiresIn = expiresIn;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public String getUnionid() {
            return unionid;
        }

        public void setUnionid(String unionid) {
            this.unionid = unionid;
        }
    }

}

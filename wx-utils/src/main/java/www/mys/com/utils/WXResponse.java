package www.mys.com.utils;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "xml")
public class WXResponse {

    @JacksonXmlProperty(localName = "return_code")
    private String returnCode;//返回码
    @JacksonXmlProperty(localName = "return_msg")
    private String returnMsg;//返回信息
    @JacksonXmlProperty(localName = "appid")
    private String appid;//appid
    @JacksonXmlProperty(localName = "mch_id")
    private String mchId;//商户号
    @JacksonXmlProperty(localName = "nonce_str")
    private String nonceStr;//随机字符串
    @JacksonXmlProperty(localName = "sign")
    private String sign;//签名
    @JacksonXmlProperty(localName = "result_code")
    private String resultCode;//结果码
    @JacksonXmlProperty(localName = "err_code")
    private String errCode;//错误码
    @JacksonXmlProperty(localName = "err_code_des")
    private String errCodeDes;//错误描述
    @JacksonXmlProperty(localName = "prepay_id")
    private String prepayId;//支付id
    @JacksonXmlProperty(localName = "trade_type")
    private String tradeType;//支付类型
    @JacksonXmlProperty(localName = "device_info")
    private String deviceInfo;//设备信息

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrCodeDes() {
        return errCodeDes;
    }

    public void setErrCodeDes(String errCodeDes) {
        this.errCodeDes = errCodeDes;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    @Override
    public String toString() {
        return "WXResponse{" +
                "returnCode='" + returnCode + '\'' +
                ", returnMsg='" + returnMsg + '\'' +
                ", appid='" + appid + '\'' +
                ", mchId='" + mchId + '\'' +
                ", nonceStr='" + nonceStr + '\'' +
                ", sign='" + sign + '\'' +
                ", resultCode='" + resultCode + '\'' +
                ", errCode='" + errCode + '\'' +
                ", errCodeDes='" + errCodeDes + '\'' +
                ", prepayId='" + prepayId + '\'' +
                ", tradeType='" + tradeType + '\'' +
                ", deviceInfo='" + deviceInfo + '\'' +
                '}';
    }
}

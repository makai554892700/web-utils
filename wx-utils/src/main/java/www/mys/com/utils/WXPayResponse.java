package www.mys.com.utils;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "xml")
public class WXPayResponse {

    @JacksonXmlProperty(localName = "return_code")
    private String returnCode;
    @JacksonXmlProperty(localName = "return_msg")
    private String returnMsg;
    @JacksonXmlProperty(localName = "appid")
    private String appid;
    @JacksonXmlProperty(localName = "mch_id")
    private String mchId;
    @JacksonXmlProperty(localName = "nonce_str")
    private String nonceStr;
    @JacksonXmlProperty(localName = "sign")
    private String sign;
    @JacksonXmlProperty(localName = "result_code")
    private String resultCode;
    @JacksonXmlProperty(localName = "err_code")
    private String errCode;
    @JacksonXmlProperty(localName = "err_code_des")
    private String errCodeDes;
    @JacksonXmlProperty(localName = "device_info")
    private String deviceInfo;
    @JacksonXmlProperty(localName = "openid")
    private String openid;
    @JacksonXmlProperty(localName = "is_subscribe")
    private String isSubscribe;
    @JacksonXmlProperty(localName = "trade_type")
    private String tradeType;
    @JacksonXmlProperty(localName = "trade_state")
    private String tradeState;
    @JacksonXmlProperty(localName = "bank_type")
    private String bankType;
    @JacksonXmlProperty(localName = "total_fee")
    private String totalFee;
    @JacksonXmlProperty(localName = "fee_type")
    private String feeType;
    @JacksonXmlProperty(localName = "cash_fee")
    private String cashFee;
    @JacksonXmlProperty(localName = "cash_fee_type")
    private String cashFeeType;
    @JacksonXmlProperty(localName = "settlement_total_fee")
    private String settlementTotalFee;
    @JacksonXmlProperty(localName = "coupon_fee")
    private String couponFee;
    @JacksonXmlProperty(localName = "coupon_count")
    private String couponCount;
    @JacksonXmlProperty(localName = "transaction_id")
    private String transactionId;
    @JacksonXmlProperty(localName = "out_trade_no")
    private String outTradeNo;
    @JacksonXmlProperty(localName = "attach")
    private String attach;
    @JacksonXmlProperty(localName = "time_end")
    private String timeEnd;
    @JacksonXmlProperty(localName = "trade_state_desc")
    private String tradeStateDesc;

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

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getIsSubscribe() {
        return isSubscribe;
    }

    public void setIsSubscribe(String isSubscribe) {
        this.isSubscribe = isSubscribe;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeState() {
        return tradeState;
    }

    public void setTradeState(String tradeState) {
        this.tradeState = tradeState;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getCashFee() {
        return cashFee;
    }

    public void setCashFee(String cashFee) {
        this.cashFee = cashFee;
    }

    public String getCashFeeType() {
        return cashFeeType;
    }

    public void setCashFeeType(String cashFeeType) {
        this.cashFeeType = cashFeeType;
    }

    public String getSettlementTotalFee() {
        return settlementTotalFee;
    }

    public void setSettlementTotalFee(String settlementTotalFee) {
        this.settlementTotalFee = settlementTotalFee;
    }

    public String getCouponFee() {
        return couponFee;
    }

    public void setCouponFee(String couponFee) {
        this.couponFee = couponFee;
    }

    public String getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(String couponCount) {
        this.couponCount = couponCount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getTradeStateDesc() {
        return tradeStateDesc;
    }

    public void setTradeStateDesc(String tradeStateDesc) {
        this.tradeStateDesc = tradeStateDesc;
    }

    @Override
    public String toString() {
        return "WXPayResponse{" +
                "returnCode='" + returnCode + '\'' +
                ", returnMsg='" + returnMsg + '\'' +
                ", appid='" + appid + '\'' +
                ", mchId='" + mchId + '\'' +
                ", nonceStr='" + nonceStr + '\'' +
                ", sign='" + sign + '\'' +
                ", resultCode='" + resultCode + '\'' +
                ", errCode='" + errCode + '\'' +
                ", errCodeDes='" + errCodeDes + '\'' +
                ", deviceInfo='" + deviceInfo + '\'' +
                ", openid='" + openid + '\'' +
                ", isSubscribe='" + isSubscribe + '\'' +
                ", tradeType='" + tradeType + '\'' +
                ", tradeState='" + tradeState + '\'' +
                ", bankType='" + bankType + '\'' +
                ", totalFee='" + totalFee + '\'' +
                ", feeType='" + feeType + '\'' +
                ", cashFee='" + cashFee + '\'' +
                ", cashFeeType='" + cashFeeType + '\'' +
                ", settlementTotalFee='" + settlementTotalFee + '\'' +
                ", couponFee='" + couponFee + '\'' +
                ", couponCount='" + couponCount + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", outTradeNo='" + outTradeNo + '\'' +
                ", attach='" + attach + '\'' +
                ", timeEnd='" + timeEnd + '\'' +
                ", tradeStateDesc='" + tradeStateDesc + '\'' +
                '}';
    }
}

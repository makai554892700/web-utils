package www.mys.com.utils;

public class SendBack {
    public int code;
    public String errorMsg;
    public String data;

    public SendBack() {
    }

    public SendBack(int code, String errorMsg, String data) {
        this.code = code;
        this.errorMsg = errorMsg;
        this.data = data;
    }

    @Override
    public String toString() {
        return "SendBack{" +
                "code=" + code +
                ", errorMsg='" + errorMsg + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
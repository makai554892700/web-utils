package www.mys.com.utils;

public enum BaseResultEnum {
    DATA_ERROR(0, "request data error."),
    NO_DATA(1, "database have no data."),
    DATA_ALREADY_EXIST(2, "data already exist."),
    SQL_ERROR(3, "option data error."),
    NO_SUCH_DATA(4, "data not exist."),
    TOKEN_OUT_TIME(5, "token out of date."),
    TOKEN_ERROR(6, "token error."),
    UNKNOW_ERROR(-1, "unknow error.");

    private String msg;
    private Integer code;

    public Integer getCode() {
        return code;
    }

    BaseResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

package www.mys.com.utils;

public class ResultUtils {

    public static int SUCCESS_CODE = 200;
    public static int FIELD_CODE = -1;
    public static int ERROR_CODE = -2;

    public static <T> Result<T> succeed(T data) {
        return new Result<T>(SUCCESS_CODE, "success", data);
    }

    public static <T> Result<T> field(T data) {
        return new Result<T>(FIELD_CODE, "field", data);
    }

    public static <T> Result<T> error(T data) {
        return new Result<T>(ERROR_CODE, "error", data);
    }

    public static <T> Result<T> consume(Integer code, String message, T data) {
        return new Result<T>(code, message, data);
    }

}

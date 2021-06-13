package www.mys.com.utils;

public class ResultUtils {

    public static Integer SUCCESS_CODE = 200;
    public static Integer FIELD_CODE = -1;
    public static Integer ERROR_CODE = -2;

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

    public static <T> T getResult(Result<T> response) {
        if (response == null) {
            return null;
        }
        if (!response.getCode().equals(ResultUtils.SUCCESS_CODE)) {
            throw new BaseException(response.getMessage(), response.getCode());
        } else {
            return response.getData();
        }
    }

}

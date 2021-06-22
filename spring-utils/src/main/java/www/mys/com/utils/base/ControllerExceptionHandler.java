package www.mys.com.utils.base;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import www.mys.com.utils.BaseException;

import java.util.logging.Level;
import java.util.logging.Logger;

//controller层错误拦截，用于拦截错误页面返回给用户，给用户正确的格式
@ControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger log = Logger.getLogger(ControllerExceptionHandler.class.getName());

    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public ResponseEntity<String> onField(BaseException baseException) {
        log.log(Level.WARNING, "onField error message=" + baseException);
        return ResponseEntity.status(baseException.getCode()).body(baseException.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<String> onError(Exception e) {
        log.log(Level.WARNING, "onError error message=" + e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

}

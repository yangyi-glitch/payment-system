package mav.shan.payment.handler;

import lombok.extern.slf4j.Slf4j;
import mav.shan.common.custexception.BusinessException;
import mav.shan.common.utils.ResultUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static mav.shan.common.utils.ResultUtils.error;

/**
 * 统一异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(BusinessException.class)
//    public ResultUtils businessException(HttpServletRequest request, BusinessException e) {
//        return error(e.getMessage());
//    }

    @ExceptionHandler(value = Exception.class)
    public ResultUtils defaultExceptionHandler(HttpServletRequest req, Throwable ex) {
        log.error("[defaultExceptionHandler]", ex);
        return error(ex.getMessage());
    }
}

package com.leyou.common.advice;

import com.leyou.common.exception.LyExceptiion;
import com.leyou.common.vo.ExceptionResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 通用异常处理
 *
 * @author llh
 */
@ControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(LyExceptiion.class)
    public ResponseEntity<ExceptionResult> handleException(LyExceptiion e) {
        return ResponseEntity.status(e.getExceptionEnum().getCode())
                .body(new ExceptionResult(e.getExceptionEnum()));
    }
}

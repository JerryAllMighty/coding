package com.seowon.coding.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * TODO #10: [운영 - 예외 처리 및 로깅 전략]
     * 시니어/PL급 수준의 예외 처리 체계를 구축하세요.
     * 1. 비즈니스 예외(BusinessException)와 시스템 예외를 구분하여 처리
     * 2. 사용자에게는 정제된 에러 메시지와 에러 코드(ErrorCode Enum) 반환
     * 3. 로깅 시 MDC(Mapped Diagnostic Context) 등을 활용하여 요청 트레이싱(Trace ID) 정보 포함 방안 고려
     * 4. 각 예외 상황(4xx, 5xx)에 맞는 적절한 HTTP Status 반환
     */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllExceptions(Exception e) {
        log.error("Unhandled Exception: ", e);
        return ResponseEntity.internalServerError().build();
    }
}

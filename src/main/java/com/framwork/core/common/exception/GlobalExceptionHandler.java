package com.framwork.core.common.exception;

import com.framwork.core.common.message.CommonMessageService;
import jakarta.servlet.http.HttpServletRequest;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ErrorMessageProperties errorMessageProperties;
    private final CommonMessageService commonMessageService;

    @ExceptionHandler(BizException.class)
    public ResponseEntity<ApiErrorResponse> handleBizException(BizException ex, HttpServletRequest request) {
        String defaultCode = errorMessageProperties.getDefaultCode();
        String code = StringUtils.hasText(ex.getCode()) ? ex.getCode() : defaultCode;
        String fallback = StringUtils.hasText(ex.getMessage())
                ? ex.getMessage()
                : commonMessageService.getMessage(defaultCode, "An unexpected error occurred.");
        String message = commonMessageService.getMessage(code, fallback);

        ApiErrorResponse response = new ApiErrorResponse(
                OffsetDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                code,
                message,
                request.getRequestURI()
        );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorResponse> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
        log.error("RuntimeException converted to BizException", ex);
        BizException bizException = new BizException(errorMessageProperties.getDefaultCode(), ex);
        return handleBizException(bizException, request);
    }
}

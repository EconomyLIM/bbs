package bbs.board.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * date           : 2024-11-26
 * created by     : 임경재
 * description    :
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final String slackUrl;

    public GlobalExceptionHandler(final @Value("${slack.webhook.url}") String slackUrl) {
        this.slackUrl = slackUrl;
    }

    /**
     * 보안을 위한 200 Status 로 반환
     */
    @ExceptionHandler(exception = CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(final CustomException e) {
        log.error(e.getMessage(), e);
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode());
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }

    @ExceptionHandler(exception = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(final MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        List<Map<String, String>> errorList = new ArrayList<>();

        for (ObjectError allError : allErrors) {
            Map<String, String> errorDetails = new HashMap<>();

            if (allError instanceof FieldError) {
                FieldError fieldError = (FieldError) allError;
                errorDetails.put("field", fieldError.getField());
                errorDetails.put("message", fieldError.getDefaultMessage());
            } else {
                errorDetails.put("object", allError.getObjectName());
                errorDetails.put("message", allError.getDefaultMessage());
            }

            errorList.add(errorDetails);
        }
        ErrorCode errorCode = ErrorCode.BAD_REQUEST;
        ErrorResponse errorResponse = new ErrorResponse(errorCode, errorList);

        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }

    @ExceptionHandler(exception = RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(final RuntimeException e) {
        log.error(e.getMessage(), e);
        ErrorCode errorCode = ErrorCode.BAD_REQUEST;
        ErrorResponse errorResponse = new ErrorResponse(errorCode);
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }

    @ExceptionHandler(exception = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(final Exception e, HttpServletRequest request){
        log.error(e.getMessage(), e);
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
        ErrorResponse errorResponse = new ErrorResponse(errorCode);

        // 요청 정보 추출
        String requestUrl = request.getRequestURL().toString();
        String httpMethod = request.getMethod();
        String queryString = request.getQueryString(); // 쿼리 파라미터 (null 가능)

        // 예외 발생 위치 정보
        StackTraceElement stackTraceElement = e.getStackTrace()[0]; // 첫 번째 스택 트레이스 정보
        String className = stackTraceElement.getClassName();
        String methodName = stackTraceElement.getMethodName();
        int lineNumber = stackTraceElement.getLineNumber();

        // Slack 메시지 JSON 생성
        String json = makeSlackMessage(e, className, methodName, lineNumber, httpMethod, requestUrl, queryString);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Request example = objectMapper.readValue(json, Request.class);

            HttpResponse<Response> object = Unirest.post(slackUrl)
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .body(example)
                    .asObject(Response.class);

            log.warn(object.getBody().toString());

        }catch (JsonProcessingException ex){
            log.error(ex.getMessage(), ex);
        }

        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }

    private static String makeSlackMessage(final Exception e, final String className, final String methodName, final int lineNumber, final String httpMethod, final String requestUrl, final String queryString) {
        String json = """
    {
        "text": "오류 알림기",
        "attachments": [
            {
                "title": "의도치 않은 오류가 발생했습니다.",
                "text": "오류 메시지: %s\\n발생 위치: %s.%s(Line: %d)\\nHTTP 요청: %s %s%s"
            }
        ]
    }
    """.formatted(
                e.getMessage(), // 오류 메시지
                className,      // 발생 클래스
                methodName,     // 발생 메소드
                lineNumber,     // 발생 라인
                httpMethod,     // HTTP 메소드
                requestUrl,     // 호출된 URL
                queryString != null ? "?" + queryString : "" // 쿼리 문자열 (있을 경우 추가)
        );
        return json;
    }

    @Data
    static class Response{
        String code;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Request {
        private String text;
        private List<Attachment> attachments;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Attachment {
        private String title;
        private String text;
    }
}

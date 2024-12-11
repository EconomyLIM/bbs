package bbs.board.exception;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * date           : 2024-11-26
 * created by     : 임경재
 * description    :
 */
@Getter
public class ErrorResponse {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private String code;
    private String message;
    private List<Map<String, String>> fieldError;

    public ErrorResponse() {
    }

    public ErrorResponse(ErrorCode errorCode) {
        this.code = "BAD";
        this.message = errorCode.getErrorMessage();
    }

    public ErrorResponse(ErrorCode errorCode, List<Map<String, String>> fieldError) {
        this.code = "BAD";
        this.message = errorCode.getErrorMessage();
        this.fieldError = fieldError;
    }
}

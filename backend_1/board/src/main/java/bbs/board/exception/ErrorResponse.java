package bbs.board.exception;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * date           : 2024-11-26
 * created by     : 임경재
 * description    :
 */
@Getter
public class ErrorResponse {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private ErrorCode errorCode;
    private String customAddMessage;

    public ErrorResponse() {
    }

    public ErrorResponse(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorResponse(ErrorCode errorCode, String customAddMessage) {
        this.errorCode = errorCode;
        this.customAddMessage = customAddMessage;
    }
}

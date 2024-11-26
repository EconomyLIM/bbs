package bbs.board.exception;

import lombok.Getter;

/**
 * date           : 2024-11-26
 * created by     : 임경재
 * description    :
 */
@Getter
public enum ErrorCode {

    INVALID_AUTHENTICATION(401, "권한이 없습니다.")
    , NO_SEARCH_MEMBER(401, "아이디나 비밀번호가 다릅니다")
    , BAD_REQUEST(400, "잘못된 요청입니다.")
    , INTERNAL_ERROR(500, "내부 서버 오류")

    ;

    private int errorCode;
    private String errorMessage;

    ErrorCode() {
    }

    ErrorCode(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}

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
    , ID_PASSWORD_NOT_NULL(400, "아이디나 비밀번호가 빈값입니다.")
    , NO_SEARCH_MEMBER(400, "아이디가 존재하지 않거나 비밀번호가 다릅니다.")
    , ALREADY_LIKE_BOARD(400, "이미 추천/비추천 한 게시물입니다.")
    , BAD_REQUEST(400, "잘못된 요청입니다.")
    , NOT_FOUND(404, "페이지를 찾을 수 없습니다.")
    , NOT_USER_FOUND(404, "사용자를 찾을 수 없습니다.")
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

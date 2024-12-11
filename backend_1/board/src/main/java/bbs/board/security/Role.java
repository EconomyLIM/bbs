package bbs.board.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * date           : 2024-12-10
 * created by     : 임경재
 * description    :
 */
@Getter
@RequiredArgsConstructor
public enum Role {
    NOT_REGISTERED("ROLE_NOT_REGISTERED", "회원가입 이전 사용자"),
    USER("ROLE_USER", "일반 사용자"),
    ADMIN("ROLE_ADMIN", "관리자");

    private final String key;
    private final String title;
}

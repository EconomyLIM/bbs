package bbs.board.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * date           : 2024-12-10
 * created by     : 임경재
 * description    :
 */
@RequiredArgsConstructor
@Getter
public enum JwtRule {
    JWT_ISSUE_HEADER("Set-Cookie"),
    JWT_RESOLVE_HEADER("Cookie"),
    ACCESS_PREFIX("access"),
    REFRESH_PREFIX("refresh");

    private final String value;
}

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
public enum TokenStatus {
    AUTHENTICATED,
    EXPIRED,
    INVALID
}

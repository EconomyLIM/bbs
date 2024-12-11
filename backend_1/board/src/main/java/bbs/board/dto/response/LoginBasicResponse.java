package bbs.board.dto.response;

import bbs.board.domain.Member;
import bbs.board.dto.common.BasicResponse;
import lombok.Getter;

/**
 * date           : 2024-11-26
 * created by     : 임경재
 * description    :
 */
@Getter
public class LoginBasicResponse extends BasicResponse {
    private Member member;

    public LoginBasicResponse() {
    }

    public LoginBasicResponse(Member member) {
        this.member = member;
    }
}

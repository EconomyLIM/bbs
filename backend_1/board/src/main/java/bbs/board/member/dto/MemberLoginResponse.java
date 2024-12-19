package bbs.board.member.dto;

import bbs.board.member.entity.Member;
import lombok.Getter;

/**
 * date           : 2024-12-03
 * created by     : 임경재
 * description    :
 */
@Getter
public class MemberLoginResponse {
    private Member member;

    private MemberLoginResponse() {
    }

    private MemberLoginResponse(final Member member) {
        super();
        this.member = member;
    }

    public static MemberLoginResponse of(final Member member) {
        return new MemberLoginResponse(member);
    }
}

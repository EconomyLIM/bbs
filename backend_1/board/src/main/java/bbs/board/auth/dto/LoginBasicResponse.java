package bbs.board.auth.dto;

import bbs.board.auth.entity.RefreshToken;
import bbs.board.member.entity.Member;
import bbs.board.member.dto.MemberDTO;
import bbs.board.common.dto.BasicResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * date           : 2024-11-26
 * created by     : 임경재
 * description    :
 */
@Getter
public class LoginBasicResponse extends BasicResponse {
    private MemberDTO memberdto;
    private String accessToken;
    @Setter
    private RefreshToken refreshToken;

    private LoginBasicResponse() {
    }

    public LoginBasicResponse(final Member member, final String accessToken, final RefreshToken refreshToken) {
        this.memberdto = new MemberDTO(member);
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static LoginBasicResponse of(final Member member, final String accessToken, final RefreshToken refreshToken) {
        return new LoginBasicResponse(member, accessToken, refreshToken);
    }
}

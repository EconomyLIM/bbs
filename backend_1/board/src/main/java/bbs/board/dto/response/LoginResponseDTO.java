package bbs.board.dto.response;

import bbs.board.domain.Member;
import bbs.board.dto.common.ResponseDTO;
import lombok.Getter;

/**
 * date           : 2024-11-26
 * created by     : 임경재
 * description    :
 */
@Getter
public class LoginResponseDTO extends ResponseDTO {
    private Member member;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(Member member) {
        this.member = member;
    }
}

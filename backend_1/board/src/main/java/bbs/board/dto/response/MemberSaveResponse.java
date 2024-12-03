package bbs.board.dto.response;

import bbs.board.dto.common.ResponseDTO;
import lombok.Getter;

/**
 * date           : 2024-12-03
 * created by     : 임경재
 * description    :
 */
@Getter
public class MemberSaveResponse extends ResponseDTO {
    private Long memberId;

    private MemberSaveResponse() {
    }

    private MemberSaveResponse(final Long memberId) {
        super();
        this.memberId = memberId;
    }

    public static MemberSaveResponse of(final Long memberId) {
        return new MemberSaveResponse(memberId);
    }
}

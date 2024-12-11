package bbs.board.dto.response;

import bbs.board.dto.common.BasicResponse;
import lombok.Getter;

/**
 * date           : 2024-12-03
 * created by     : 임경재
 * description    :
 */
@Getter
public class MemberSaveBasicResponse extends BasicResponse {
    private Long memberId;

    private MemberSaveBasicResponse() {
    }

    private MemberSaveBasicResponse(final Long memberId) {
        super();
        this.memberId = memberId;
    }

    public static MemberSaveBasicResponse of(final Long memberId) {
        return new MemberSaveBasicResponse(memberId);
    }
}

package bbs.board.dto.common;

import lombok.Builder;
import lombok.Getter;

/**
 * date           : 2024-11-26
 * created by     : 임경재
 * description    :
 */
@Builder
@Getter
public class ResponseDTO {

    private String code;
    private String message;

    public ResponseDTO() {
        this.code = "OK";
        this.message = null;
    }

    public ResponseDTO(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

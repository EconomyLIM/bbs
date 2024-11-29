package bbs.board.dto.common;

import lombok.Builder;

/**
 * date           : 2024-11-26
 * created by     : 임경재
 * description    :
 */
@Builder
public class ResponseDTO {

    private String code;
    private String message;


    public ResponseDTO() {
        this.code = "200";
        this.message = "OK";
    }

    public ResponseDTO(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

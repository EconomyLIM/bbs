package bbs.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * date           : 2024-11-26
 * created by     : 임경재
 * description    :
 */
@Builder
public class ResponseDTO {

    public String code;
    public String message;

    public ResponseDTO() {
        this.code = "200";
        this.message = "OK";
    }

    public ResponseDTO(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

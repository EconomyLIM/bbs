package bbs.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * date           : 2024-11-26
 * created by     : 임경재
 * description    :
 */
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ResponseDTO {

    public String code;
    public String message;
}

package bbs.board.dto.common;

import lombok.Getter;

/**
 * date           : 2024-11-26
 * created by     : 임경재
 * description    :
 */
@Getter
public class BasicResponse {

    private String code;
    private String message;

    public BasicResponse() {
        this.code = "OK";
        this.message = null;
    }

    public BasicResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static BasicResponse of (){
        return new BasicResponse();
    }
}

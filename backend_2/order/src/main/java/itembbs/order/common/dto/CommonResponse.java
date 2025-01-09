package itembbs.order.common.dto;

import lombok.Getter;

/**
 * date           : 2025-01-09
 * created by     : 임경재
 * description    :
 */
@Getter
public class CommonResponse {

    private String code;
    private String message;

    public CommonResponse() {
        this.code = "OK";
        this.message = null;
    }

    public CommonResponse(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public static CommonResponse ok() {
        return new CommonResponse();
    }
}

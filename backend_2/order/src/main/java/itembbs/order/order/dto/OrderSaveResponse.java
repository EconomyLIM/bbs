package itembbs.order.order.dto;

import itembbs.order.common.dto.CommonResponse;
import lombok.Data;
import lombok.Getter;

/**
 * date           : 2025-01-09
 * created by     : 임경재
 * description    :
 */
@Getter
public class OrderSaveResponse extends CommonResponse {
    private Long orderId;

    private OrderSaveResponse() {
    }

    public OrderSaveResponse(final Long orderId) {
        this.orderId = orderId;
    }
}

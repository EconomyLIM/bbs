package itembbs.order.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * date           : 2025-01-09
 * created by     : 임경재
 * description    :
 */
@AllArgsConstructor @NoArgsConstructor @Data
public class OrderDTO {

    private Long id;
    private int currentPrice;
    private int quantity;
}

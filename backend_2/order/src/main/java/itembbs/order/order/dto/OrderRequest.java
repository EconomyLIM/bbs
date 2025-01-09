package itembbs.order.order.dto;

import itembbs.order.order.domain.Address;
import itembbs.order.order.domain.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * date           : 2025-01-09
 * created by     : 임경재
 * description    :
 */
@Data @NoArgsConstructor @AllArgsConstructor
public class OrderRequest {
    Address address;
    private List<OrderDTO> items;
}

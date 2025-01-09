package itembbs.order.order.controller;

import itembbs.order.member.domain.Member;
import itembbs.order.order.dto.OrderRequest;
import itembbs.order.order.dto.OrderSaveResponse;
import itembbs.order.order.service.OrderService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * date           : 2025-01-09
 * created by     : 임경재
 * description    :
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<OrderSaveResponse> saveOrder(OrderRequest orderRequest) {
        OrderSaveResponse response = orderService.order(orderRequest, new Member("test@test.com", "1234!"));
        return ResponseEntity.ok(response);
    }

    @PostConstruct
//    @Profile("test")
    public void init() {

    }
}

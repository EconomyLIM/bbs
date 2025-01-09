package itembbs.order.order.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * date           : 2025-01-09
 * created by     : 임경재
 * description    :
 */
@SpringBootTest
class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @Test
    @Transactional
    public void findOrders() throws Exception{
        // given
        List<Order> orders = orderRepository.customFindItems();

        // when

        // then
        for (Order order : orders) {
            System.out.println("order = " + order);
        }
    }
}
package itembbs.order.order.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * date           : 2025-01-09
 * created by     : 임경재
 * description    :
 */
public interface OrderRepository extends JpaRepository<Order, Long>, OrderCustomRepository {
}

package itembbs.order.order.domain;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * date           : 2025-01-09
 * created by     : 임경재
 * description    :
 */

public interface OrderCustomRepository {

    List<Order> customFindItems();
}

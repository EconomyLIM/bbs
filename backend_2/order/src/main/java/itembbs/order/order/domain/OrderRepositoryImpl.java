package itembbs.order.order.domain;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * date           : 2025-01-09
 * created by     : 임경재
 * description    :
 */
@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderCustomRepository {

    private final EntityManager em;

    @Override
    public List<Order> customFindItems() {
//        return em.createQuery("select o from Order o" +
//                        " join fetch o.delivery d " +
//                        " join fetch o.member m " +
//                        " join fetch o.orderItems oi" +
//                        " join fetch oi.item i ", Order.class)
//                .setFirstResult(1)
//                .setMaxResults(10)
//                .getResultList();
        return em.createQuery("select o from Order o" +
                        " join fetch o.delivery d " +
                        " join fetch o.member m " +
                        " join o.orderItems oi" +
                        " join oi.item i ", Order.class)
                .setFirstResult(1)
                .setMaxResults(10)
                .getResultList();
    }
}

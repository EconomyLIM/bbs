package itembbs.order.order.domain;

import itembbs.order.member.domain.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * date           : 2025-01-09
 * created by     : 임경재
 * description    :
 */
@Entity
@Table(name = "orders")
public class Order {

    @Getter
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime orderDate;

    private int totalPrice;

    protected Order() {
    }

    public Order(final Member member, final OrderStatus orderStatus, final LocalDateTime orderDate) {
        this.member = member;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
    }

    public static Order createOrder(Member member, List<OrderItem> orderItems, Delivery delivery) {
        Order order = new Order(member, OrderStatus.ORDER, LocalDateTime.now());
        order.settingDelivery(delivery);
        order.addOrderItems(orderItems);
        return order;
    }

    public void addOrderItems(List<OrderItem> orderItems){
        for (OrderItem orderItem : orderItems) {
            this.orderItems.add(orderItem);
            orderItem.setOrder(this);
        }
    }

    public void settingDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

}

package itembbs.order.order.domain;

import itembbs.order.item.domain.Item;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Setter;

/**
 * date           : 2025-01-09
 * created by     : 임경재
 * description    :
 */
@Entity
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @Setter
    private Order order;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private int price;
    private int quantity;

    protected OrderItem() {
    }

    public OrderItem(final Order order, final Item item, final int price, final int quantity) {
        this.order = order;
        this.item = item;
        this.price = price;
        this.quantity = quantity;
    }

    public OrderItem(final Item item, final int price, final int quantity) {
        this.item = item;
        this.price = price;
        this.quantity = quantity;
    }
}

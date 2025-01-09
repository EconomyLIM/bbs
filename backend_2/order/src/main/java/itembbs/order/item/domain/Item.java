package itembbs.order.item.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * date           : 2025-01-03
 * created by     : 임경재
 * description    :
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Getter
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_id")
    private Long id;

    @Column(name = "item_name")
    @Getter
    private String itemName;

    private int price;
    @Getter
    private int stock;

    public Item(final String itemName, final int price, final int stock) {
        this.itemName = itemName;
        this.price = price;
        this.stock = stock;
    }

    public void decreaseStock(int stock) {
        int afterStock = this.stock - stock;
        if (afterStock < 0) {
            throw new IllegalArgumentException("stock less than zero");
        }
        this.stock = afterStock;
    }

}

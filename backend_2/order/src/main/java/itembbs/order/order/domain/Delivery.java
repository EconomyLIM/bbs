package itembbs.order.order.domain;

import jakarta.persistence.*;
import lombok.Setter;

/**
 * date           : 2025-01-09
 * created by     : 임경재
 * description    :
 */
@Entity
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "delivery")
    @Setter
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    protected Delivery() {
    }

    public Delivery(final Address address, final DeliveryStatus deliveryStatus) {
        this.address = address;
        this.deliveryStatus = deliveryStatus;
    }
}

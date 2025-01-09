package itembbs.order.order.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;

/**
 * date           : 2025-01-09
 * created by     : 임경재
 * description    :
 */
@AllArgsConstructor
@Embeddable
public class Address {

    private String city;
    private String street;
    private String zipcode;

    public Address() {
    }
}

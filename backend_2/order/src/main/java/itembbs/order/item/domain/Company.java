package itembbs.order.item.domain;

import jakarta.persistence.*;

/**
 * date           : 2025-01-10
 * created by     : 임경재
 * description    :
 */
@Entity
public class Company {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "company_id")
    private Long id;

    private String name;
    private String companyNumber;

    protected Company() {
    }

    public Company(final String name, final String companyNumber) {
        this.name = name;
        this.companyNumber = companyNumber;
    }
}

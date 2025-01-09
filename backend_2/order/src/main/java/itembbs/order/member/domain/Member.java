package itembbs.order.member.domain;

import jakarta.persistence.*;
import lombok.Getter;

/**
 * date           : 2025-01-09
 * created by     : 임경재
 * description    :
 */
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true, nullable = false)
    @Getter
    private String email;
    @Column(nullable = false)
    private String password;
    private String username;
    private String nickname;
    private int point;

    protected Member() {
    }

    public Member(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public Member(final String email, final String password, final String username, final String nickname) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.nickname = nickname;
    }
}

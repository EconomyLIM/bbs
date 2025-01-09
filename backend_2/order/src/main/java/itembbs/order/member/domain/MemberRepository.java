package itembbs.order.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * date           : 2025-01-09
 * created by     : 임경재
 * description    :
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    public Optional<Member> findByEmail(String email);
}

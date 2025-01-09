package itembbs.order.common.init;

import itembbs.order.member.domain.Member;
import itembbs.order.member.domain.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

/**
 * date           : 2025-01-09
 * created by     : 임경재
 * description    :
 */
@RequiredArgsConstructor
public class MemberInit {
    private final MemberRepository memberRepository;

    @Transactional
    @PostConstruct
    public void init() {
        memberRepository.save(new Member("test@test.com", "1234!"));
    }
}

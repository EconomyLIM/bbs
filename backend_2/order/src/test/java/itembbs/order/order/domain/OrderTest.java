package itembbs.order.order.domain;

import itembbs.order.member.domain.Member;
import itembbs.order.member.domain.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * date           : 2025-01-09
 * created by     : 임경재
 * description    :
 */
@SpringBootTest
@Transactional
class OrderTest {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("주문에 성공해야한다.")
    public void insertOrder() throws Exception{
        // given
        Member member = new Member("test123@test.com", "1234!", "username", "nickname");
        memberRepository.save(member);

        // when

        // then
    }
}
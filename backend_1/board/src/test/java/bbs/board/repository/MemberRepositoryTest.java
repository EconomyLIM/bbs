package bbs.board.repository;

import bbs.board.dao.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;


    @Test
    @Transactional
    @Rollback(value = false)
    void 회원가입에_성공해야한다(){
        Member member = Member.builder()
                .username("test1")
                .password("testPassword")
                .email("test123@test.com")
                .build();
        memberRepository.save(member);

        Assertions.assertThat(memberRepository.findById(member.getId())).isEqualTo(member);
    }

}
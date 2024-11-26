package bbs.board.repository;

import bbs.board.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;


    @Test
    @Transactional
    void 회원가입에_성공해야한다 (){
        Member member = Member.builder()
                .username("test1")
                .password("testPassword")
                .email("test123@test.com")
                .build();
        memberRepository.save(member);

        Assertions.assertThat(memberRepository.findById(member.getId())).isEqualTo(member);
    }

    @Test
    void 회원조회에_성공해야한다 (){
        Member member = Member.builder()
                .username("test1")
                .password("testPassword")
                .email("test123@test.com")
                .build();
        memberRepository.save(member);
        Assertions.assertThat(memberRepository.findById(member.getId())).isEqualTo(member);
    }

}
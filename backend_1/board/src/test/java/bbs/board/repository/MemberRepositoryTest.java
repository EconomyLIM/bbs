package bbs.board.repository;

import bbs.board.member.entity.Member;
import bbs.board.member.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;


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

    @Test
    @Transactional
    @Rollback(false)
    public void searchMemberByEmail() throws Exception{
        // given
        Member member = new Member("testMember1");
        memberRepository.save(member);
        // when
        em.flush();
        em.clear();
        Member findMember = memberRepository.findByEmail(member.getEmail()).orElse(null);


        // then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
    }

}
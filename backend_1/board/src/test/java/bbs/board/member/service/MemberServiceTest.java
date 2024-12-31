package bbs.board.member.service;

import bbs.board.member.dto.MemberDTO;
import bbs.board.member.dto.MemberUpdateRequest;
import bbs.board.member.entity.Member;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * date           : 2024-12-31
 * created by     : 임경재
 * description    :
 */
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("회원 수정에 성공해야한다.")
    void updateMember(){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setEmail("test2@test.com");
        memberDTO.setPassword("123456");
        memberDTO.setNickname("nick");
        memberDTO.setUsername("user");

        memberService.saveMember(memberDTO);

        MemberUpdateRequest request = new MemberUpdateRequest();
        request.setEmail("test2@test.com");
        request.setUsername("changeUser");
        request.setNickname("changeNick");
        request.setCurrentPassword("123456");
        request.setChangePassword("123456!");
        memberService.update(request);

        em.flush();
        em.clear();

        Member findMember = memberService.findMember(request.getEmail());

        assertThat(findMember.getEmail()).isEqualTo(request.getEmail());
        assertThat(findMember.getNickname()).isEqualTo(request.getNickname());
        assertThat(findMember.getUsername()).isEqualTo(request.getUsername());
    }

}
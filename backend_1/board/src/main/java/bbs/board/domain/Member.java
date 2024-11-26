package bbs.board.domain;

import bbs.board.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter @NoArgsConstructor @AllArgsConstructor
@SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR"
        , sequenceName = "member_seq"
        , initialValue = 1
        , allocationSize = 1
)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
    @Column(name = "member_id")
    private Long id;

    private String email;
    private String password;
    private String username;
    private String nickname;

    public Member(String email, String password, String username, String nickname) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.nickname = nickname;
    }

    public static Member newMemberWithMemberDTO(MemberDTO memberDTO) {
        return new Member(
                memberDTO.getEmail()
                , memberDTO.getPassword()
                , memberDTO.getUsername()
                , memberDTO.getNickname()
        );
    }
}

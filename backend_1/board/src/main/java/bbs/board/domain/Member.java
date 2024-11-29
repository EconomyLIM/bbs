package bbs.board.domain;

import bbs.board.dto.MemberDTO;
import bbs.board.exception.CustomException;
import bbs.board.exception.ErrorCode;
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

    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private String username;
    private String nickname;
    private Integer point;

    // just test constructor
    public Member(String email) {
        this.email = email;
    }

    public Member(String email, String password, String username, String nickname) {
        emailAndPasswordValidation(email,password);

        this.email = email;
        this.password = password;
        this.username = username;
        this.nickname = nickname;
        point = 0;
    }

    public static Member newMemberWithMemberDTO(MemberDTO memberDTO) {
        emailAndPasswordValidation(memberDTO.getEmail(), memberDTO.getPassword());

        return new Member(
                memberDTO.getEmail()
                , memberDTO.getPassword()
                , memberDTO.getUsername()
                , memberDTO.getNickname()
        );
    }

    public static void emailAndPasswordValidation(String email, String password) {
        if (email == null || password == null) {
            throw new CustomException(ErrorCode.ID_PASSWORD_NOT_NULL);
        }
    }
}

package bbs.board.member.entity;

import bbs.board.member.dto.MemberDTO;
import bbs.board.exception.CustomException;
import bbs.board.exception.ErrorCode;
import bbs.board.member.dto.MemberUpdateRequest;
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
    @Column(nullable = false)
    private String password;
    private String username;
    private String nickname;
    private int point;

//    @Enumerated(EnumType.STRING)
//    private Role role;

    // constructor for test
    public Member(String email) {
        this(email, "1234!", "testUsername", "럭키세븐");
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

    public void addPoint(){
        this.point += 5;
    }

    public void deletePoint(){
        this.point -= 5;
    }

    public void update(final MemberUpdateRequest request) {
        this.nickname = request.getNickname();
        this.username = request.getUsername();
        this.password = request.getChangePassword();
    }
}

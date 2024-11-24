package bbs.board.dao;

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
    private Long id;
    private String password;
    private String username;
    private String email;
    private String nickname;
}

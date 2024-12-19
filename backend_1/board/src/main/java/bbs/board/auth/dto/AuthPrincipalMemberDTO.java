package bbs.board.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
@AllArgsConstructor
public class AuthPrincipalMemberDTO {

    private String email;
    private String nickname;
    private String username;

    public AuthPrincipalMemberDTO(final String email) {
        this.email = email;
    }

    public AuthPrincipalMemberDTO(final String email, final String nickname) {
        this.email = email;
        this.nickname = nickname;
    }
}

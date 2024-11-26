package bbs.board.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * date           : 2024-11-26
 * created by     : 임경재
 * description    :
 */
@Data @NoArgsConstructor @AllArgsConstructor
public class MemberDTO {

    @Email @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    @NotEmpty
    private String username;
    @NotEmpty
    private String nickname;
}

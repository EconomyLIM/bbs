package bbs.board.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

/**
 * date           : 2024-11-26
 * created by     : 임경재
 * description    :
 */
@Getter
public class LoginDTO {

    @NotEmpty @Email
    private String email;
    private String password;

}

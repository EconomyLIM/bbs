package bbs.board.member.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * date           : 2024-12-27
 * created by     : 임경재
 * description    :
 */
@Data @NoArgsConstructor @AllArgsConstructor
public class MemberUpdateRequest {

    @NotEmpty
    private String email;
    @NotEmpty
    private String nickname;
    @NotEmpty
    private String username;
    @NotEmpty
    private String currentPassword;
    @NotEmpty
    private String changePassword;
}

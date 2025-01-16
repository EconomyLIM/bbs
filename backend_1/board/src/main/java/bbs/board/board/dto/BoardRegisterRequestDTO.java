package bbs.board.board.dto;

import bbs.board.board.entity.Keyword;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * date           : 2024-11-29
 * created by     : 임경재
 * description    :
 */
@Data
@NoArgsConstructor @AllArgsConstructor
public class BoardRegisterRequestDTO {

    @NotNull @NotEmpty
    private String title;
    @NotNull @NotEmpty
    private String content;
    private String memberEmail;
    private String nickname;
    private List<Keyword> keywords;
    private Long categoryId;

    public BoardRegisterRequestDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public BoardRegisterRequestDTO(final String title, final String content, final String memberEmail, final String nickname, final Long categoryId) {
        this.title = title;
        this.content = content;
        this.memberEmail = memberEmail;
        this.nickname = nickname;
        this.categoryId = categoryId;
    }

    public BoardRegisterRequestDTO(final String title, final String content, final String memberEmail, final String nickname) {
        this.title = title;
        this.content = content;
        this.memberEmail = memberEmail;
        this.nickname = nickname;
    }
}

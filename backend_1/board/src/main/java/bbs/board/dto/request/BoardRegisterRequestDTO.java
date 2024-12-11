package bbs.board.dto.request;

import bbs.board.domain.Category;
import bbs.board.domain.Keyword;
import bbs.board.domain.Member;
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

    private String title;
    private String content;
    private String memberEmail;
    private List<Keyword> keywords;
    private Category category;

    public BoardRegisterRequestDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public BoardRegisterRequestDTO(final String title, final String content, final String memberEmail) {
        this.title = title;
        this.content = content;
        this.memberEmail = memberEmail;
    }
}

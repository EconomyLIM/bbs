package bbs.board.board.dto;

import bbs.board.category.entity.Category;
import bbs.board.board.entity.Keyword;
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
public class BoardUpdateRequestDTO {

    private Long boardId;
    private String title;
    private String content;
    private List<Keyword> keywords;
    private Category category;
}

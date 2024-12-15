package bbs.board.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class BoardDeleteRequestDTO {

    private Long boardId;
    private String memberEmail;
}

package bbs.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class BoardDTO {

    private Long id;
    private String title;
    private String content;
    private String memberEmail;
    private LocalDateTime updateDate;
    private int likedCnt;
}

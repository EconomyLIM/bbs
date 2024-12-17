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
    private String nickname;
    private LocalDateTime updateDate;
    private int likedCnt;
    private String registeredDate;

    private Long categoryId;
    private String categoryName;

    private boolean isMine;
}

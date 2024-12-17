package bbs.board.dto.response;

import bbs.board.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * date           : 2024-11-29
 * created by     : 임경재
 * description    :
 */
@Getter
@NoArgsConstructor @AllArgsConstructor
public class BoardResponse {
    private Long id;
    private String title;
    private String content;
    private int likedCnt;
    private String memberEmail;
    private String nickname;
    private String categoryName;

    public BoardResponse(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.likedCnt = board.getLikedCnt();
        this.memberEmail = board.getMember().getEmail();
        this.nickname = board.getMember().getNickname();
        this.categoryName = board.getCategory().getCategoryName();
    }
}

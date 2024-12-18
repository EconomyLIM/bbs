package bbs.board.comment.entity;

import lombok.Getter;

/**
 * date           : 2024-12-18
 * created by     : 임경재
 * description    :
 */
@Getter
public enum CommentStatus {
    REGISTERED, UPDATED, DELETED;

    CommentStatus() {
    }
}

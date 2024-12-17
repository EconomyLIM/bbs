package bbs.board.comment.entity;

import bbs.board.domain.BaseEntity;
import bbs.board.domain.Board;
import bbs.board.domain.Member;
import bbs.board.comment.dto.SaveCommentRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor @AllArgsConstructor
@SequenceGenerator(
        name = "comment_seq_generator",    // 식별자 생성기의 이름
        sequenceName = "Comment_SEQ", // Oracle에 존재하는 시퀀스 이름
        allocationSize = 1         // 시퀀스 증가 값 (Oracle 시퀀스 INCREMENT BY와 일치시켜야 함)
)
@Table(name = "comments")
@Getter
public class Comment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_seq_generator")
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String commentContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID", nullable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    private List<Comment> replies = new ArrayList<>();

    private int likedCnt;

    public Comment(final SaveCommentRequest request) {
        this.id = request.getCommentId();
        this.commentContent = request.getCommentContent();
    }

    public Comment(final Long id) {
        this.id = id;
    }

    public Comment(final Member findMember, final Board findBoard, final SaveCommentRequest request, final Comment parentComment) {
        this.member = findMember;
        this.board = findBoard;
        if (parentComment != null) {
            this.parentComment = parentComment;
            parentComment.getReplies().add(this);
        }
        this.commentContent = request.getCommentContent();
    }

    public void setBoard(Board board){
        this.board = board;
        board.getComments().add(this);
    }

    @Override
    public String toString() {
        return "[" + this.id + " / " + this.commentContent + " / " + " ] ";
    }
}

package bbs.board.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder @NoArgsConstructor @AllArgsConstructor
@SequenceGenerator(
        name = "comment_seq_generator",    // 식별자 생성기의 이름
        sequenceName = "Comment_SEQ", // Oracle에 존재하는 시퀀스 이름
        allocationSize = 1         // 시퀀스 증가 값 (Oracle 시퀀스 INCREMENT BY와 일치시켜야 함)
)
@Table(name = "comments")
@Getter
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_seq_generator")
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String commentContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    private LocalDateTime registerDate;
    private LocalDateTime updateDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    @Getter
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    @Getter
    private List<Comment> replies = new ArrayList<>();

    public void setBoard(Board board){
        this.board = board;
        board.getComments().add(this);
    }

    public void addChildComment(Comment comment){
        replies.add(comment);
    }

    @Override
    public String toString() {
        return "[" + this.id + " / " + this.commentContent + " / " + " ] ";
    }
}

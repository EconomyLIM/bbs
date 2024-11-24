package bbs.board.dao;

import bbs.board.repository.BoardRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder @NoArgsConstructor @AllArgsConstructor
@SequenceGenerator(
        name = "comment_seq_generator",    // 식별자 생성기의 이름
        sequenceName = "Comment_SEQ", // Oracle에 존재하는 시퀀스 이름
        allocationSize = 1         // 시퀀스 증가 값 (Oracle 시퀀스 INCREMENT BY와 일치시켜야 함)
)
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_seq_generator")
    @Column(name = "comment_id")
    private Long id;
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    private LocalDateTime registerDate;
    private LocalDateTime updateDate;

    public void setBoard(Board board){
        this.board = board;
    }
}

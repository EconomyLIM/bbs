package bbs.board.board.entity;

import jakarta.persistence.*;
import lombok.Setter;

/**
 * date           : 2024-11-28
 * created by     : 임경재
 * description    :
 */
@Entity
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    @Setter
    private Board board;

    private String keywordName;

    public Keyword() {
    }

    public Keyword(String keywordName) {
        this.keywordName = keywordName;
    }
}

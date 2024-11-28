package bbs.board.domain;

import jakarta.persistence.*;

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
    private Board board;

    private String keywordName;
}

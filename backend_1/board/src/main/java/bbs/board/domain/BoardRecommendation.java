package bbs.board.domain;

import bbs.board.dto.common.RecommendationType;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

/**
 * date           : 2024-11-29
 * created by     : 임경재
 * description    :
 */
@Entity
@NoArgsConstructor
public class BoardRecommendation {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecommendationType recommendType;

    public BoardRecommendation(Member member, Board board, RecommendationType recommendationType) {
        this.member = member;
        this.board = board;
        this.recommendType = recommendationType;
    }
}

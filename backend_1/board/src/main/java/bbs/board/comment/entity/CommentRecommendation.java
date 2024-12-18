package bbs.board.comment.entity;

import bbs.board.domain.Member;
import bbs.board.dto.common.RecommendationType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRecommendation {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_recommend_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email", nullable = false)
    private Member member;

    private LocalDateTime registeredDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecommendationType recommendationType;


    public CommentRecommendation(final Comment comment, final Member member, RecommendationType recommendationType) {
        this.comment = comment;
        this.member = member;
        this.recommendationType = recommendationType;
        registeredDate = LocalDateTime.now();
    }
}

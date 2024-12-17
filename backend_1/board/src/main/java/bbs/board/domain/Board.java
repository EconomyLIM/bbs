package bbs.board.domain;

import bbs.board.category.entity.Category;
import bbs.board.comment.entity.Comment;
import bbs.board.dto.common.RecommendationType;
import bbs.board.dto.request.BoardLikedDTO;
import bbs.board.dto.request.BoardRegisterRequestDTO;
import bbs.board.dto.request.BoardUpdateRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @NoArgsConstructor @AllArgsConstructor @Builder
@SequenceGenerator(
        name = "board_seq_generator",
        sequenceName = "Board_SEQ",
        allocationSize = 1
)
public class Board extends BaseEntity {

    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_seq_generator")
    private Long id;
    private String title;

    @Lob
    private String content;
    private int likedCnt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private String nickname;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Keyword> keywords = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public void  update (BoardUpdateRequestDTO boardUpdateRequestDTO){
        title = boardUpdateRequestDTO.getTitle();
        content = boardUpdateRequestDTO.getContent();
        keywords.clear();
        if (boardUpdateRequestDTO.getKeywords() != null && !boardUpdateRequestDTO.getKeywords().isEmpty()){
            keywords.addAll(boardUpdateRequestDTO.getKeywords());
        }
        category = boardUpdateRequestDTO.getCategory();
    }

    public void updatedLiked(BoardLikedDTO boardLikedDTO){
        RecommendationType recommendationType = boardLikedDTO.getRecommendationType();
        if (recommendationType == RecommendationType.LIKE){
            likedCnt += 1;
        }else{
            likedCnt -= 1;
        }

    }

    public void addComment(Comment comment){
        comments.add(comment);
        comment.setBoard(this);
    }

    public Board(BoardRegisterRequestDTO boardRequestDTO, Member member, Category category) {
        this.title = boardRequestDTO.getTitle();
        this.content = boardRequestDTO.getContent();
        List<Keyword> keywords = boardRequestDTO.getKeywords();
        if (keywords != null && !keywords.isEmpty()){
            for (Keyword keyword : keywords) {
                keyword.setBoard(this);
            }
        }
        this.keywords = keywords;
        this.member = member;
        this.category = category;
        this.likedCnt = 0;
        this.nickname = boardRequestDTO.getNickname();
    }

    public Board(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
    }
}

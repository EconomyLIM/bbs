package bbs.board.repository;

import bbs.board.dao.Board;
import bbs.board.dao.Comment;
import bbs.board.dao.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;

    @Test
    void 게시물에_댓글등록을_성공해야한다 (){
        Board board = Board.builder()
                .title("testTitle1")
                .content("testContent")
                        .comments(new ArrayList<Comment>()).build();

        Member member = Member.builder()
                .username("test1")
                .password("testPassword")
                .email("test123@test.com")
                .build();


        Comment comment = Comment.builder()
                .commentContent("testComment")
                .member(member)
                .build();

        board.addComment(comment);
        memberRepository.save(member);
        boardRepository.save(board);
        commentRepository.save(comment);
    }

    @Test
    void 댓글의_답글을_다는것을_성공해야한다 () {
        Board findBoard = boardRepository.findById(1L);
        Comment findComment = findBoard.getComments().get(0);
        Member findMember = memberRepository.findById(1L);

        Comment childComment = Comment.builder()
                .commentContent("testComment2")
                .parentComment(findComment)
                .updateDate(LocalDateTime.now())
                .board(findBoard)
                .member(findMember)
                .build();

        findComment.addChildComment(childComment);
        commentRepository.save(childComment);

        assertThat(childComment.getParentComment().getId()).isEqualTo(1L);
        assertThat(findComment.getReplies()).hasSize(2);

    }

    @Test
    @DisplayName("게시글의 기준으로 댓글과 답글 조회를 성공해야 한다.")
    void searchComment(){
        Board findBoard = boardRepository.findById(1L);
        List<Comment> commentsInBoard = commentRepository.getCommentsInBoard(findBoard);
        for (Comment comment : commentsInBoard) {
            System.out.println(comment);
            List<Comment> replies = comment.getReplies();
            for (Comment reply : replies) {
                System.out.println("reply = " + reply);
            }
        }

        assertThat(commentsInBoard).hasSize(1);
    }

}
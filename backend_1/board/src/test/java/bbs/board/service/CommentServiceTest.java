package bbs.board.service;

import bbs.board.comment.service.CommentService;
import bbs.board.board.entity.Board;
import bbs.board.member.entity.Member;
import bbs.board.comment.dto.FindCommentByBoardRequest;
import bbs.board.comment.dto.SaveCommentRequest;
import bbs.board.board.repository.BoardRepository;
import bbs.board.member.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

/**
 * date           : 2024-12-11
 * created by     : 임경재
 * description    :
 */
@SpringBootTest
@Transactional
@Rollback(false)
@Slf4j
class CommentServiceTest {

    @Autowired
    CommentService commentService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    void init (){
        Member member = new Member("test@test.com");
        memberRepository.save(member);
        Board board = new Board("testTitle", "testContent", member);
        boardRepository.save(board);
    }

    @AfterEach
    void after (){
//        boardRepository.deleteAll();
//        memberRepository.deleteAll();
    }

    @Test
    void 댓글_하나_저장에_성공해야한다(){

    }

    @Test
    void 답글_저장에_성공해야한다(){
        SaveCommentRequest request = new SaveCommentRequest();
        request.setBoardId(1L);
        request.setMemberEmail("test@test.com");
        request.setCommentContent("testContent");
        commentService.save(request);

        log.info("request.getCommentId() = {}", request.getCommentId());
        SaveCommentRequest secondRequest = new SaveCommentRequest();
        secondRequest.setBoardId(1L);
        secondRequest.setMemberEmail("test@test.com");
        secondRequest.setCommentContent("testContent2222222");
//        secondRequest.setParentComment(request);
        commentService.save(secondRequest);

        FindCommentByBoardRequest findRequest = new FindCommentByBoardRequest();
        findRequest.setBoardId(1L);

    }

}
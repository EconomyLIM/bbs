package bbs.board.service;

import bbs.board.domain.Board;
import bbs.board.domain.Comment;
import bbs.board.domain.Member;
import bbs.board.dto.request.FindCommentByBoardRequest;
import bbs.board.dto.request.SaveCommentRequest;
import bbs.board.repository.BoardRepository;
import bbs.board.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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
        secondRequest.setParentComment(request);
        commentService.save(secondRequest);

        FindCommentByBoardRequest findRequest = new FindCommentByBoardRequest();
        findRequest.setBoardId(1L);

    }

}
package bbs.board.repository;

import bbs.board.entity.Board;
import bbs.board.entity.Member;
import bbs.board.dto.BoardDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    void 글등록을_성공해야한다 () {
        // given
        Member member = Member.builder()
                .username("test2")
                .password("testPassword")
                .email("test123@test.com")
                .build();
        memberRepository.save(member);

        Board board = Board.builder()
                .title("testTitle2")
                .content("testContent2")
                .member(member)
                .registerDate(LocalDateTime.now()).build();

        // when
        boardRepository.save(board);

        // then
        Assertions.assertThat(board.getId()).isEqualTo(2);
    }

    @Test
    @Transactional
    void 글조회를_성공해야한다 () {
        List<Board> all = boardRepository.findAll();
        for (Board board : all) {
            System.out.println("board = " + board);
            System.out.println("board.getMember = " + board.getMember());
        }

        Assertions.assertThat(all).hasSize(3);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void 글수정을_성공해야한다 () {
        BoardDTO boardDTO = BoardDTO.
                builder().id(1L)
                .title("updateTitle1")
                .content("updateTitle2")
                .build();
        boardRepository.update(boardDTO);

        Board findBoard = boardRepository.findById(boardDTO.getId());
        Assertions.assertThat(findBoard.getTitle()).isEqualTo(boardDTO.getTitle());

    }

}
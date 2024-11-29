package bbs.board.service;

import bbs.board.domain.Board;
import bbs.board.domain.Category;
import bbs.board.domain.Keyword;
import bbs.board.domain.Member;
import bbs.board.dto.common.BoardRecommendationType;
import bbs.board.dto.request.BoardLikedDTO;
import bbs.board.dto.request.BoardRegisterRequestDTO;
import bbs.board.dto.request.BoardUpdateRequestDTO;
import bbs.board.exception.CustomException;
import bbs.board.repository.CategoryRepository;
import bbs.board.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * date           : 2024-11-29
 * created by     : 임경재
 * description    :
 */
@SpringBootTest
@Transactional
@Rollback(false)
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager em;

//    @BeforeEach
//    void categorySetup(){
//        // given
//        Category topCategory1 = new Category("TopDepth1", 1);
//        Category topCategory2 = new Category("TopDepth2", 1);
//
//        categoryRepository.save(topCategory1);
//        categoryRepository.save(topCategory2);
//
//        Category subCategory1 = new Category("subDepth1", 2);
//        subCategory1.addParentCategoryInChild(topCategory1);
//
//        Category subCategory2 = new Category("subDepth1", 2);
//        subCategory2.addParentCategoryInChild(topCategory2);
//        categoryRepository.save(subCategory1);
//        categoryRepository.save(subCategory2);
//    }

    @Test
    @DisplayName("게시글 등록과 수정을 성공해야 한다.")
    @Transactional
    @Rollback(false)
    public void register() throws Exception{
        // given
        Member member = new Member("testMember1");
        memberRepository.save(member);

        Category topDepth1 = new Category("TopDepth1", 1);
        categoryRepository.save(topDepth1);

        BoardRegisterRequestDTO br = new BoardRegisterRequestDTO();
        br.setTitle("title1");
        br.setContent("content1");
        br.setMember(member);
        br.setCategory(topDepth1);

        List<Keyword> list = new ArrayList<>();
        list.add(new Keyword("testKeyword1"));
        list.add(new Keyword("testKeyword2"));
        br.setKeywords(list);

        // when
        Long savedId = boardService.save(br);
        em.flush();
        em.clear();
        // then
        assertThat(savedId).isEqualTo(1);

        Board findBoard = boardService.findById(savedId);
        BoardUpdateRequestDTO boardUpdateRequestDTO = new BoardUpdateRequestDTO();
        boardUpdateRequestDTO.setTitle("updateTitle2");
        boardUpdateRequestDTO.setContent("updateContent2");
        findBoard.update(boardUpdateRequestDTO);

        em.flush();
        em.clear();

        Board afterUpdateFindBoard = boardService.findById(savedId);
        assertThat(afterUpdateFindBoard.getTitle()).isEqualTo("updateTitle2");
    }

    @Test
    @DisplayName("게시글 좋아요가 정상작동 해야된다.")
    public void updateLikedCnt() throws Exception{
        // given
        Member member = new Member("testMember1");
        memberRepository.save(member);

        BoardRegisterRequestDTO br = new BoardRegisterRequestDTO();
        br.setMember(member);
        br.setTitle("title1");
        br.setContent("content1");

        Long savedId = boardService.save(br);
        System.out.println("savedId = " + savedId);

        BoardLikedDTO boardLikedDTO = new BoardLikedDTO();
        boardLikedDTO.setMemberId(member.getId());
        boardLikedDTO.setBoardId(savedId);
        boardLikedDTO.setEmail(member.getEmail());
        boardLikedDTO.setRecommendationType(BoardRecommendationType.LIKE);

        BoardLikedDTO boardLikedDT2 = new BoardLikedDTO();
        boardLikedDT2.setMemberId(member.getId());
        boardLikedDT2.setBoardId(savedId);
        boardLikedDT2.setEmail(member.getEmail());
        boardLikedDT2.setRecommendationType(BoardRecommendationType.LIKE);

        em.flush();
        em.clear();
        // when
        boardService.likedBoard(boardLikedDTO);
        em.flush();
        em.clear();
        System.out.println("========================================");

        // then
        Board afterFindBoard = boardService.findById(savedId);
        assertThat(afterFindBoard.getLikedCnt()).isEqualTo(1);

        assertThatThrownBy(()->boardService.likedBoard(boardLikedDT2)).isInstanceOf(Exception.class);
    }
}
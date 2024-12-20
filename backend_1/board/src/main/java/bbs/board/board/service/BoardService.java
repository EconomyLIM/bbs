package bbs.board.board.service;

import bbs.board.category.entity.Category;
import bbs.board.category.repository.CategoryRepository;
import bbs.board.board.entity.Board;
import bbs.board.board.entity.BoardRecommendation;
import bbs.board.member.entity.Member;
import bbs.board.auth.dto.AuthPrincipalMemberDTO;
import bbs.board.common.dto.BasicResponse;
import bbs.board.common.dto.CommonPageSizes;
import bbs.board.board.dto.BoardLikedDTO;
import bbs.board.board.dto.BoardRegisterRequestDTO;
import bbs.board.board.dto.BoardSearchRequestDTO;
import bbs.board.board.dto.BoardUpdateRequestDTO;
import bbs.board.board.dto.BoardFindByIdBasicResponse;
import bbs.board.board.dto.BoardListBasicResponse;
import bbs.board.board.dto.BoardResponse;
import bbs.board.board.dto.BoardSaveBasicResponse;
import bbs.board.exception.CustomException;
import bbs.board.exception.ErrorCode;
import bbs.board.board.repository.BoardRecommendationRepository;
import bbs.board.board.repository.BoardRepository;
import bbs.board.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * date           : 2024-11-26
 * created by     : 임경재
 * description    :
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final BoardRecommendationRepository boardRecommendationRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public BoardSaveBasicResponse save (BoardRegisterRequestDTO boardRequestDTO) {
        Member findMember = memberRepository.findByEmail(boardRequestDTO.getMemberEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_USER_FOUND));

        findMember.addPoint();

        Category findCategory = categoryRepository.findById(boardRequestDTO.getCategoryId())
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));

        Board savedBoard = boardRepository.save(new Board(boardRequestDTO, findMember, findCategory));
        return BoardSaveBasicResponse.of(savedBoard.getId());
    }

    @Transactional
    public BoardSaveBasicResponse update (Long id, BoardUpdateRequestDTO boardRequestDTO) {
//        Board findBoard = boardRepository
//                .findById(id)
//                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        Board findBoard = boardRepository
                .findJoinFetchBoardById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));

        findBoard.update(boardRequestDTO);
        return BoardSaveBasicResponse.of(findBoard.getId());
    }

    @Transactional
    public BasicResponse delete (Long id, String memberEmail) {
        Board findBoard = boardRepository
                .findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));

        if (!findBoard.getMember().getEmail().equals(memberEmail)){
            throw new CustomException(ErrorCode.NO_PERMISSION);
        }

        findBoard.getMember().deletePoint();

        boardRepository.delete(findBoard);
        return BasicResponse.of();
    }

    public BoardFindByIdBasicResponse findById(final Long id, final AuthPrincipalMemberDTO memberDto) {
//        Board board = boardRepository
//                .findById(id)
//                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND));

        Board board = boardRepository
                .findJoinFetchBoardById(id)
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND));

        String email = null;
        if (memberDto.getEmail() != null) {
            email = memberDto.getEmail();
        }

        return BoardFindByIdBasicResponse.of(board, email);
    }

    public BoardListBasicResponse findAll(BoardSearchRequestDTO boardSearchRequestDTO) {

        int page = getPage(boardSearchRequestDTO);

        PageRequest pageRequest = PageRequest.of(page, CommonPageSizes.BOARD_PAGE_SIZE.getPageSize());
        Page<Board> result = boardRepository.findAll(pageRequest);
        List<Board> content = result.getContent();
        List<BoardResponse> list = content.stream().map(BoardResponse::new).toList();

        return BoardListBasicResponse.of(list, boardSearchRequestDTO.getPage(), result.getTotalPages());
    }

    private static int getPage(final BoardSearchRequestDTO boardSearchRequestDTO) {
        int page = 0;
        if (boardSearchRequestDTO.getPage() >= 1){
            page = boardSearchRequestDTO.getPage() - 1;
        }
        return page;
    }

    public BoardListBasicResponse findAllBySearchKeyword(BoardSearchRequestDTO request) {

        List<Board> boardBySearch = boardRepository.findBoardBySearch(request);
        int boardBySearchCnt = boardRepository.findBoardBySearchCnt(request);
        List<BoardResponse> list = boardBySearch.stream().map(BoardResponse::new).toList();

        return BoardListBasicResponse.of(list, request.getPage(), (boardBySearchCnt / CommonPageSizes.BOARD_PAGE_SIZE.getPageSize() ) + 1);
    }


    @Transactional
    public void likedBoard(BoardLikedDTO boardLikedDTO){

        // board_id와 email 로 추천한 것 찾기
        BoardRecommendation byEmailAndBoard = boardRecommendationRepository.findByEmailAndBoard(boardLikedDTO.getMemberEmail(), boardLikedDTO.getBoardId());
        if(byEmailAndBoard != null){
            throw new CustomException(ErrorCode.ALREADY_LIKE_BOARD);
        }

        // id로 member find
        Member member = memberRepository.findByEmail(boardLikedDTO.getMemberEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));

        // id로 board find
        Board board = boardRepository.findById(boardLikedDTO.getBoardId())
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));

        BoardRecommendation boardRecommendation = new BoardRecommendation(member, board, boardLikedDTO.getRecommendationType());
        boardRecommendationRepository.save(boardRecommendation);
        board.updatedLiked(boardLikedDTO);
    }

}

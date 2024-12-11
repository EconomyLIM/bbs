package bbs.board.service;

import bbs.board.domain.Board;
import bbs.board.domain.BoardRecommendation;
import bbs.board.domain.Member;
import bbs.board.dto.common.CommonPageSizes;
import bbs.board.dto.request.BoardLikedDTO;
import bbs.board.dto.request.BoardRegisterRequestDTO;
import bbs.board.dto.request.BoardSearchRequestDTO;
import bbs.board.dto.request.BoardUpdateRequestDTO;
import bbs.board.dto.response.BoardFindByIdBasicResponse;
import bbs.board.dto.response.BoardListBasicResponse;
import bbs.board.dto.response.BoardResponse;
import bbs.board.dto.response.BoardSaveBasicResponse;
import bbs.board.exception.CustomException;
import bbs.board.exception.ErrorCode;
import bbs.board.repository.BoardRecommendationRepository;
import bbs.board.repository.BoardRepository;
import bbs.board.repository.MemberRepository;
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

    @Transactional
    public BoardSaveBasicResponse save (BoardRegisterRequestDTO boardRequestDTO) {
        Member findMember = memberRepository.findByEmail(boardRequestDTO.getMemberEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        Board savedBoard = boardRepository.save(new Board(boardRequestDTO, findMember));
        return BoardSaveBasicResponse.of(savedBoard.getId());
    }

    @Transactional
    public BoardSaveBasicResponse update (Long id, BoardUpdateRequestDTO boardRequestDTO) {
        Board findBoard = boardRepository
                .findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        findBoard.update(boardRequestDTO);
        return BoardSaveBasicResponse.of(findBoard.getId());
    }

    public BoardFindByIdBasicResponse findById(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND));
        return BoardFindByIdBasicResponse.of(board);
    }

    public BoardListBasicResponse findAll(BoardSearchRequestDTO boardSearchRequestDTO) {
        PageRequest pageRequest = PageRequest.of(boardSearchRequestDTO.getPage() - 1, CommonPageSizes.BOARD_PAGE_SIZE.getPageSize());
        Page<Board> result = boardRepository.findAll(pageRequest);
        List<Board> content = result.getContent();
        List<BoardResponse> list = content.stream().map(BoardResponse::new).toList();

        return BoardListBasicResponse.of(list, boardSearchRequestDTO.getPage(), result.getTotalPages());
    }

    public BoardListBasicResponse findAllBySearchKeyword(BoardSearchRequestDTO boardSearchRequestDTO) {
        List<Board> boardBySearch = boardRepository.findBoardBySearch(boardSearchRequestDTO);
        List<BoardResponse> list = boardBySearch.stream().map(BoardResponse::new).toList();
        return BoardListBasicResponse.of(list, 0, 0);
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

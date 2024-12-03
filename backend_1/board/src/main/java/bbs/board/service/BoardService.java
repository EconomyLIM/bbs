package bbs.board.service;

import bbs.board.domain.Board;
import bbs.board.domain.BoardRecommendation;
import bbs.board.domain.Member;
import bbs.board.dto.common.CommonPage;
import bbs.board.dto.request.BoardLikedDTO;
import bbs.board.dto.request.BoardRegisterRequestDTO;
import bbs.board.dto.request.BoardSearchRequestDTO;
import bbs.board.dto.request.BoardUpdateRequestDTO;
import bbs.board.dto.response.BoardFindByIdResponse;
import bbs.board.dto.response.BoardListResponse;
import bbs.board.dto.response.BoardResponseDTO;
import bbs.board.dto.response.BoardSaveResponse;
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
import java.util.Optional;

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
    public BoardSaveResponse save (BoardRegisterRequestDTO boardRequestDTO) {
        Board save = boardRepository.save(new Board(boardRequestDTO));
        return BoardSaveResponse.of(save.getId());
    }

    @Transactional
    public BoardSaveResponse update (Long id, BoardUpdateRequestDTO boardRequestDTO) {
        BoardFindByIdResponse findBoard = findById(id);
        findBoard.getBoard().update(boardRequestDTO);
        return BoardSaveResponse.of(findBoard.getBoard().getId());
    }

    public BoardFindByIdResponse findById(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND));
        return BoardFindByIdResponse.of(board);
    }

    public BoardListResponse findAll(BoardSearchRequestDTO boardSearchRequestDTO) {
        PageRequest pageRequest = PageRequest.of(boardSearchRequestDTO.getPage(), CommonPage.BOARD_PAGE_SIZE.getPageSize());
        Page<Board> result = boardRepository.findAll(pageRequest);
        List<Board> content = result.getContent();
        List<BoardResponseDTO> list = content.stream().map(BoardResponseDTO::new).toList();

        return BoardListResponse.of(list);
    }

    public BoardListResponse findAllBySearchKeyword(BoardSearchRequestDTO boardSearchRequestDTO) {
        List<Board> boardBySearch = boardRepository.findBoardBySearch(boardSearchRequestDTO);
        List<BoardResponseDTO> list = boardBySearch.stream().map(BoardResponseDTO::new).toList();
        return BoardListResponse.of(list);
    }




    @Transactional
    public void likedBoard(BoardLikedDTO boardLikedDTO){

        // board_id와 email 로 추천한 것 찾기
        BoardRecommendation byEmailAndBoard = boardRecommendationRepository.findByEmailAndBoard(boardLikedDTO.getMemberId(), boardLikedDTO.getBoardId());
        if(byEmailAndBoard != null){
            throw new CustomException(ErrorCode.ALREADY_LIKE_BOARD);
        }

        // id로 member find
        Member member = memberRepository.findById(boardLikedDTO.getMemberId());

        // id로 board find
        Board board = boardRepository.findById(boardLikedDTO.getBoardId())
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));

        BoardRecommendation boardRecommendation = new BoardRecommendation(member, board, boardLikedDTO.getRecommendationType());
        boardRecommendationRepository.save(boardRecommendation);
        board.updatedLiked(boardLikedDTO);
    }

}

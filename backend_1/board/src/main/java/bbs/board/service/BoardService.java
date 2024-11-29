package bbs.board.service;

import bbs.board.domain.Board;
import bbs.board.domain.BoardRecommendation;
import bbs.board.domain.Member;
import bbs.board.dto.common.CommonPage;
import bbs.board.dto.request.BoardLikedDTO;
import bbs.board.dto.request.BoardRegisterRequestDTO;
import bbs.board.dto.request.BoardSearchRequestDTO;
import bbs.board.dto.response.BoardResponseDTO;
import bbs.board.exception.CustomException;
import bbs.board.exception.ErrorCode;
import bbs.board.repository.BoardRecommendationRepository;
import bbs.board.repository.BoardRepository;
import bbs.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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
    public Long save (BoardRegisterRequestDTO boardRequestDTO) {
        Board save = boardRepository.save(new Board(boardRequestDTO));
        return save.getId();
    }

    public Board findById(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    public List<BoardResponseDTO> findAll(BoardSearchRequestDTO boardSearchRequestDTO) {
        PageRequest pageRequest = PageRequest.of(boardSearchRequestDTO.getPage(), CommonPage.BoardPage.getPageSize());
        Page<Board> result = boardRepository.findAll(pageRequest);
        List<Board> content = result.getContent();
        return content.stream().map(BoardResponseDTO::new).toList();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void likedBoard(BoardLikedDTO boardLikedDTO){
        //
        BoardRecommendation byEmailAndBoard = boardRecommendationRepository.findByEmailAndBoard(boardLikedDTO.getMemberId(), boardLikedDTO.getBoardId());
        if(byEmailAndBoard != null){
            throw new CustomException(ErrorCode.ALREADY_LIKE_BOARD);
        }

//        Member member = memberRepository.findByEmail(boardLikedDTO.getEmail())
//                .orElseThrow(() -> new CustomException(ErrorCode.NO_SEARCH_MEMBER));

        Member member = memberRepository.findById(boardLikedDTO.getMemberId());
//        Member member = memberRepository.findByEmail(boardLikedDTO.getEmail()).orElse(null);

        Board board = boardRepository.findById(boardLikedDTO.getBoardId())
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
//        Board board = boardRepository.findById(boardLikedDTO.getBoardId()).orElse(null);

        BoardRecommendation boardRecommendation = new BoardRecommendation(member, board, boardLikedDTO.getRecommendationType());
        boardRecommendationRepository.save(boardRecommendation);
        board.updatedLiked(boardLikedDTO);
    }

}

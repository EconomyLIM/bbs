package bbs.board.controller;

import bbs.board.dto.AuthPrincipalMemberDTO;
import bbs.board.dto.common.BasicResponse;
import bbs.board.dto.request.BoardLikedDTO;
import bbs.board.dto.request.BoardRegisterRequestDTO;
import bbs.board.dto.request.BoardSearchRequestDTO;
import bbs.board.dto.request.BoardUpdateRequestDTO;
import bbs.board.dto.response.BoardFindByIdBasicResponse;
import bbs.board.dto.response.BoardListBasicResponse;
import bbs.board.dto.response.BoardSaveBasicResponse;
import bbs.board.service.BoardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * date           : 2024-12-03
 * created by     : 임경재
 * description    :
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "예제 API", description = "Swagger 테스트용 API")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/board")
    public ResponseEntity<BoardListBasicResponse> getBoardList(final BoardSearchRequestDTO dto){
        BoardListBasicResponse boardResponse = boardService.findAll(dto);
        return ResponseEntity.ok(boardResponse);
    }

    @GetMapping("/board/search")
    public ResponseEntity<BoardListBasicResponse> getBoardListBySearchKeyword(final BoardSearchRequestDTO dto){
        return ResponseEntity.ok(boardService.findAllBySearchKeyword(dto));
    }

    @GetMapping("/board/{id}")
    public ResponseEntity<BoardFindByIdBasicResponse> getBoardById(
            @PathVariable final Long id
            , @AuthenticationPrincipal final AuthPrincipalMemberDTO memberDto){
        return ResponseEntity.ok(boardService.findById(id , memberDto) );
    }

    @PatchMapping("/board/{id}")
    public ResponseEntity<BoardSaveBasicResponse> updateBoardById(
            @PathVariable final Long id
            , @RequestBody final BoardUpdateRequestDTO dto
            , @AuthenticationPrincipal final AuthPrincipalMemberDTO memberDto){

        return ResponseEntity.ok(boardService.update(id, dto));
    }

    @DeleteMapping("/board/{id}")
    public ResponseEntity<BasicResponse> deleteBoardById(
            @PathVariable final Long id
            , @AuthenticationPrincipal final AuthPrincipalMemberDTO memberDto){

        return ResponseEntity.ok(boardService.delete(id, memberDto.getEmail()));
    }

    @PostMapping("/board")
    public ResponseEntity<BoardSaveBasicResponse> registerBoard(
            @RequestBody final BoardRegisterRequestDTO dto
           , @AuthenticationPrincipal final AuthPrincipalMemberDTO memberDto){
        dto.setMemberEmail(memberDto.getEmail());
        dto.setNickname(memberDto.getNickname());
        return ResponseEntity.ok(boardService.save(dto));
    }

    @PostMapping("/board/liked")
    public ResponseEntity<BasicResponse> registerBoard(@RequestBody final BoardLikedDTO dto, @AuthenticationPrincipal final AuthPrincipalMemberDTO memberDTO){
        dto.setMemberEmail(memberDTO.getEmail());
        boardService.likedBoard(dto);
        return ResponseEntity.ok(new BasicResponse());
    }
}

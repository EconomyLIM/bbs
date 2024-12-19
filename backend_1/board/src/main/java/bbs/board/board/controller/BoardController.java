package bbs.board.board.controller;

import bbs.board.auth.dto.AuthPrincipalMemberDTO;
import bbs.board.common.dto.BasicResponse;
import bbs.board.board.dto.BoardLikedDTO;
import bbs.board.board.dto.BoardRegisterRequestDTO;
import bbs.board.board.dto.BoardSearchRequestDTO;
import bbs.board.board.dto.BoardUpdateRequestDTO;
import bbs.board.board.dto.BoardFindByIdBasicResponse;
import bbs.board.board.dto.BoardListBasicResponse;
import bbs.board.board.dto.BoardSaveBasicResponse;
import bbs.board.board.service.BoardService;
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
@Tag(name = "게시판 API", description = "게시판 CRUD API")
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

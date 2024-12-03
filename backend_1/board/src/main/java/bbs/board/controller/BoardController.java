package bbs.board.controller;

import bbs.board.dto.common.ResponseDTO;
import bbs.board.dto.request.BoardRegisterRequestDTO;
import bbs.board.dto.request.BoardSearchRequestDTO;
import bbs.board.dto.request.BoardUpdateRequestDTO;
import bbs.board.dto.response.BoardFindByIdResponse;
import bbs.board.dto.response.BoardListResponse;
import bbs.board.dto.response.BoardResponseDTO;
import bbs.board.dto.response.BoardSaveResponse;
import bbs.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * date           : 2024-12-03
 * created by     : 임경재
 * description    :
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/board")
    public ResponseEntity<BoardListResponse> getBoardList(final BoardSearchRequestDTO dto){
        BoardListResponse boardResponse = boardService.findAll(dto);
        return ResponseEntity.ok(boardResponse);
    }

    @GetMapping("/board/search")
    public ResponseEntity<BoardListResponse> getBoardListBySearchKeyword(final BoardSearchRequestDTO dto){
        return ResponseEntity.ok(boardService.findAllBySearchKeyword(dto));
    }

    @GetMapping("/board/{id}")
    public ResponseEntity<BoardFindByIdResponse> getBoardById(@PathVariable final Long id){
        return ResponseEntity.ok(boardService.findById(id));
    }

    @PatchMapping("/board/{id}")
    public ResponseEntity<BoardSaveResponse> updateBoardById(@PathVariable final Long id, @RequestBody final BoardUpdateRequestDTO dto){
        return ResponseEntity.ok(boardService.update(id, dto));
    }

    @PostMapping("/board")
    public ResponseEntity<BoardSaveResponse> registerBoard(@RequestBody final BoardRegisterRequestDTO dto){
        return ResponseEntity.ok(boardService.save(dto));
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init(){
        for (int i = 0; i < 100; i++) {
            BoardRegisterRequestDTO requestDTO = new BoardRegisterRequestDTO("title" + i, "content" + i);
            boardService.save(requestDTO);
        }
    }
}

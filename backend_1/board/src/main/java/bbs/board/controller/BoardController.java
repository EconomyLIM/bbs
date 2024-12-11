package bbs.board.controller;

import bbs.board.domain.Member;
import bbs.board.dto.common.BasicResponse;
import bbs.board.dto.request.BoardLikedDTO;
import bbs.board.dto.request.BoardRegisterRequestDTO;
import bbs.board.dto.request.BoardSearchRequestDTO;
import bbs.board.dto.request.BoardUpdateRequestDTO;
import bbs.board.dto.response.BoardFindByIdBasicResponse;
import bbs.board.dto.response.BoardListBasicResponse;
import bbs.board.dto.response.BoardSaveBasicResponse;
import bbs.board.repository.MemberRepository;
import bbs.board.service.BoardService;
import bbs.board.service.LoginService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
    private final MemberRepository memberRepository;

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
    public ResponseEntity<BoardFindByIdBasicResponse> getBoardById(@PathVariable final Long id){
        return ResponseEntity.ok(boardService.findById(id));
    }

    @PatchMapping("/board/{id}")
    public ResponseEntity<BoardSaveBasicResponse> updateBoardById(@PathVariable final Long id, @RequestBody final BoardUpdateRequestDTO dto){
        return ResponseEntity.ok(boardService.update(id, dto));
    }

    @PostMapping("/board")
    public ResponseEntity<BoardSaveBasicResponse> registerBoard(@RequestBody final BoardRegisterRequestDTO dto){
        return ResponseEntity.ok(boardService.save(dto));
    }

    private final LoginService loginService;
    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init(){
        Member member = new Member("test@test.com");
        memberRepository.save(member);

        for (int i = 0; i < 100; i++) {
            BoardRegisterRequestDTO requestDTO = new BoardRegisterRequestDTO("title" + i, "content" + i, member.getEmail());
            boardService.save(requestDTO);
        }
    }

    @PostMapping("/board/liked")
    public ResponseEntity<BasicResponse> registerBoard(@RequestBody final BoardLikedDTO dto){
        boardService.likedBoard(dto);
        return ResponseEntity.ok(new BasicResponse());
    }
}

package bbs.board.controller;

import bbs.board.dto.AuthPrincipalMemberDTO;
import bbs.board.dto.common.BasicResponse;
import bbs.board.dto.request.FindCommentByBoardRequest;
import bbs.board.dto.request.SaveCommentRequest;
import bbs.board.dto.response.CommentDTO;
import bbs.board.dto.response.FindCommentByBoardBasicResponse;
import bbs.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * date           : 2024-12-11
 * created by     : 임경재
 * description    :
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment/save")
    public ResponseEntity<BasicResponse> saveComment(
            @RequestBody final SaveCommentRequest comment
            , @AuthenticationPrincipal final AuthPrincipalMemberDTO memberDto) {
        comment.setMemberEmail(memberDto.getEmail());
        return ResponseEntity.ok(commentService.save(comment));
    }

    @DeleteMapping
    public ResponseEntity<BasicResponse> deleteComment(@RequestBody final SaveCommentRequest comment) {

        return ResponseEntity.ok(commentService.save(comment));
    }

    @GetMapping("/comment")
    public ResponseEntity<FindCommentByBoardBasicResponse> findCommentByBoard(final FindCommentByBoardRequest request) {
        FindCommentByBoardBasicResponse response = commentService.findByBoard(request);
        log.info("response: {}", response);
        if (response != null) {
            if (response.getComments() != null) {
                for (CommentDTO comment : response.getComments()) {
                    log.info("comment: {}", comment);
                }
            }
        }
        return ResponseEntity.ok(response);
    }
}

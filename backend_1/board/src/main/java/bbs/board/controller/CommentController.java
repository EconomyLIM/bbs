package bbs.board.controller;

import bbs.board.dto.common.BasicResponse;
import bbs.board.dto.request.FindCommentByBoardRequest;
import bbs.board.dto.request.SaveCommentRequest;
import bbs.board.dto.response.FindCommentByBoardBasicResponse;
import bbs.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * date           : 2024-12-11
 * created by     : 임경재
 * description    :
 */
@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment/save")
    public ResponseEntity<BasicResponse> saveComment(@RequestBody final SaveCommentRequest comment) {
        return ResponseEntity.ok(commentService.save(comment));
    }

    @DeleteMapping
    public ResponseEntity<BasicResponse> deleteComment(@RequestBody final SaveCommentRequest comment) {
        return ResponseEntity.ok(commentService.save(comment));
    }

    @GetMapping("/comment")
    public ResponseEntity<FindCommentByBoardBasicResponse> findCommentByBoard(final FindCommentByBoardRequest request) {
        return ResponseEntity.ok(commentService.findByBoard(request));
    }
}

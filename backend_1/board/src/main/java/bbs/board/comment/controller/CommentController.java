package bbs.board.comment.controller;

import bbs.board.dto.AuthPrincipalMemberDTO;
import bbs.board.dto.common.BasicResponse;
import bbs.board.dto.request.FindCommentByBoardRequest;
import bbs.board.comment.dto.SaveCommentRequest;
import bbs.board.comment.dto.CommentDTO;
import bbs.board.comment.dto.FindCommentByBoardBasicResponse;
import bbs.board.comment.service.CommentService;
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
    public ResponseEntity<FindCommentByBoardBasicResponse> findCommentByBoard(
            final FindCommentByBoardRequest request
            , @AuthenticationPrincipal final AuthPrincipalMemberDTO memberDto) {

        FindCommentByBoardBasicResponse response = commentService.findByBoard(request, memberDto.getEmail());

        return ResponseEntity.ok(response);
    }
}

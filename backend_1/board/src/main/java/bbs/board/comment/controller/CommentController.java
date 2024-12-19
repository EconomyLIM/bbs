package bbs.board.comment.controller;

import bbs.board.comment.dto.*;
import bbs.board.auth.dto.AuthPrincipalMemberDTO;
import bbs.board.common.dto.BasicResponse;
import bbs.board.comment.dto.FindCommentByBoardRequest;
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

    @GetMapping("/comment")
    public ResponseEntity<FindCommentByBoardBasicResponse> findCommentByBoard(
            final FindCommentByBoardRequest request
            , @AuthenticationPrincipal final AuthPrincipalMemberDTO memberDto) {

        FindCommentByBoardBasicResponse response = commentService.findByBoard(request, memberDto.getEmail());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/comment")
    public ResponseEntity<BasicResponse> deleteCommentByBoard(
            @RequestBody final CommentDeleteRequest request
            , @AuthenticationPrincipal final AuthPrincipalMemberDTO memberDto) {
        request.setMemberEmail(memberDto.getEmail());
        commentService.deleteComment(request);
        return ResponseEntity.ok(new BasicResponse());
    }

    @PostMapping("/comment/like")
    public ResponseEntity<BasicResponse> recommendComment(
            @RequestBody final CommentRecommendationRequest request
            , @AuthenticationPrincipal final AuthPrincipalMemberDTO memberDto){

        request.setMemberEmail(memberDto.getEmail());
        commentService.likedComment(request);
        return ResponseEntity.ok(new BasicResponse());
    }

    @PatchMapping("/comment")
    public ResponseEntity<BasicResponse> updateComment(
            @RequestBody final CommentUpdateRequest request
            , @AuthenticationPrincipal final AuthPrincipalMemberDTO memberDto){
        request.setMemberEmail(memberDto.getEmail());
        commentService.updateComment(request);
        return ResponseEntity.ok(new BasicResponse());
    }
}

package bbs.board.comment.service;

import bbs.board.comment.dto.CommentRecommendationRequest;
import bbs.board.comment.entity.CommentRecommendation;
import bbs.board.domain.Board;
import bbs.board.comment.entity.Comment;
import bbs.board.domain.Member;
import bbs.board.dto.common.BasicResponse;
import bbs.board.dto.request.FindCommentByBoardRequest;
import bbs.board.comment.dto.SaveCommentRequest;
import bbs.board.comment.dto.FindCommentByBoardBasicResponse;
import bbs.board.exception.CustomException;
import bbs.board.exception.ErrorCode;
import bbs.board.repository.BoardRepository;
import bbs.board.comment.repository.CommentRepository;
import bbs.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * date           : 2024-12-11
 * created by     : 임경재
 * description    :
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public BasicResponse save(SaveCommentRequest request) {
        Member findMember = memberRepository.findByEmail(request.getMemberEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));

        Board findBoard = boardRepository.findById(request.getBoardId())
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));

        Comment findParentComment = null;
        if (request.getParentCommentId() != null) {
            findParentComment = commentRepository.findById(request.getParentCommentId());
        }

        Comment comment = new Comment(findMember, findBoard, request, findParentComment);

        commentRepository.save(comment);
        request.setCommentId(comment.getId());
        return BasicResponse.of();
    }

    @Transactional
    public FindCommentByBoardBasicResponse findByBoard(final FindCommentByBoardRequest request, final String memberEmail) {

        Board findBoard = boardRepository.findById(request.getBoardId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        List<Comment> commentsInBoard = commentRepository.getCommentsInBoard(findBoard);

        return FindCommentByBoardBasicResponse.of(commentsInBoard, memberEmail);
    }

    @Transactional
    public void likedComment(CommentRecommendationRequest request){
        CommentRecommendation findCommentRecommendation = commentRepository.findByEmailAndBoard(request.getMemberEmail(), request.getCommentId());
    }
}

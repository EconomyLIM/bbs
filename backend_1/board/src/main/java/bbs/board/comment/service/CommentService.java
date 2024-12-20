package bbs.board.comment.service;

import bbs.board.comment.dto.*;
import bbs.board.comment.entity.CommentRecommendation;
import bbs.board.comment.repository.CommentRecommendationRepository;
import bbs.board.board.entity.Board;
import bbs.board.comment.entity.Comment;
import bbs.board.member.entity.Member;
import bbs.board.common.dto.BasicResponse;
import bbs.board.comment.dto.FindCommentByBoardRequest;
import bbs.board.exception.CustomException;
import bbs.board.exception.ErrorCode;
import bbs.board.board.repository.BoardRepository;
import bbs.board.comment.repository.CommentRepository;
import bbs.board.member.repository.MemberRepository;
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
    private final CommentRecommendationRepository recommendationRepository;
    private final CommentRecommendationRepository commentRecommendationRepository;

    @Transactional
    public BasicResponse save(SaveCommentRequest request) {
        Member findMember = memberRepository.findByEmail(request.getMemberEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));

        Board findBoard = boardRepository.findById(request.getBoardId())
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));

        Comment findParentComment = null;
        if (request.getParentCommentId() != null) {
            findParentComment = commentRepository.findById(request.getParentCommentId())
                    .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
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
        CommentRecommendation findCommentRecommendation = commentRecommendationRepository.findByEmailAndBoard(request.getMemberEmail(), request.getCommentId());

        if (findCommentRecommendation != null){
            throw new CustomException(ErrorCode.ALREADY_LIKE_COMMENT);
        }

        Member findMember = memberRepository.findByEmail(request.getMemberEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));

        Comment findComment = commentRepository.findById(request.getCommentId())
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));

        CommentRecommendation commentRecommendation = new CommentRecommendation(findComment, findMember, request.getRecommendationType());
        recommendationRepository.save(commentRecommendation);
        findComment.updateLiked(request);
    }

    @Transactional
    public void deleteComment(CommentDeleteRequest request){
        Comment findComment = commentRepository.findById(request.getCommentId())
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));

        if (!findComment.getMember().getEmail().equals(request.getMemberEmail())){
            throw new CustomException(ErrorCode.NO_PERMISSION);
        }

        findComment.deleteComment();
    }

    @Transactional
    public void updateComment(CommentUpdateRequest request){
        Comment findComment = commentRepository.findById(request.getCommentId())
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));

        if (!findComment.getMember().getEmail().equals(request.getMemberEmail())){
            throw new CustomException(ErrorCode.NO_PERMISSION);
        }

        findComment.update(request);

    }
}

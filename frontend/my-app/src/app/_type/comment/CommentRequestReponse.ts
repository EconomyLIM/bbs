/**
 * 댓글
 */
import {ApiResponse} from "@/app/_type/CommonResponse";

export interface Comments {
    commentId: string
    , parentCommentId: string
    , commentContent: string
    , memberEmail: string
    , nickname: string
    , registered: string
    , likedCnt: number
    , childComments: Comments[]
    , mine: boolean
    , status: 'REGISTERED' | 'UPDATED' | 'DELETED'
}

export interface FindCommentByBoardResponse extends ApiResponse{
    comments: Comments[]
}

export interface SaveCommentRequest{
    boardId: string
    , memberEmail: string
    , commentId: string
    , parentCommentId?: string | null
    , commentContent: string
}

export interface FindCommentByBoardRequest{
    boardId: string
}

export interface CommentLikedRequest {
    commentId: string
    , recommendationType : "LIKE" | "DISLIKE"
}

export interface CommentDeleteRequest {
    commentId: string
}

export interface UpdateCommentRequest{
    commentId: string
    , updatedContent: string
}

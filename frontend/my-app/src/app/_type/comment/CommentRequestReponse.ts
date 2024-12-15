/**
 * 댓글
 */
import {ApiResponse} from "@/app/_type/CommonResponse";

export interface Comments {
    commentId: bigint
    , commentContent: string
    , memberEmail: string
    , nickname: string
    , registered: string
    , likedCnt: number
    , childComment: Comments[]
}

export interface FindCommentByBoardResponse extends ApiResponse{
    comments: Comments[]
}

export interface SaveCommentRequest{
    boardId: string
    , memberEmail: string
    , commentId?: bigint
    , parentComment?: Comments | null
    , commentContent: string
}

export interface FindCommentByBoardRequest{
    boardId: string
}

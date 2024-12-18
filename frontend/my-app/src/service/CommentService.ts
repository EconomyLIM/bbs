import {requestApiFetch} from "@/lib/requestApiFetch";
import {ApiResponse} from "@/app/_type/CommonResponse";
import {
    CommentDeleteRequest,
    CommentLikedRequest,
    FindCommentByBoardRequest,
    FindCommentByBoardResponse,
    SaveCommentRequest, UpdateCommentRequest
} from "@/app/_type/comment/CommentRequestReponse";


export const saveComment = async (data: SaveCommentRequest) :Promise<ApiResponse> => {
    return await requestApiFetch<ApiResponse>('POST', '/comment/save', data);
}

export const findCommentByBoard = async (data: FindCommentByBoardRequest) :Promise<FindCommentByBoardResponse> => {
    return await requestApiFetch<FindCommentByBoardResponse>('GET', '/comment', data);
}

export const commentLiked = async (data: CommentLikedRequest) :Promise<ApiResponse> => {
    return await requestApiFetch<ApiResponse>('POST', '/comment/like', data);
}

export const deleteComment = async (data: CommentDeleteRequest) :Promise<ApiResponse> => {
    return await requestApiFetch<ApiResponse>('DELETE', '/comment', data);
}

export const updateComment = async (data: UpdateCommentRequest) :Promise<ApiResponse> => {
    return await requestApiFetch<ApiResponse>('PATCH', '/comment', data);
}


import {requestApiFetch} from "@/lib/requestApiFetch";
import {ApiResponse} from "@/app/_type/CommonResponse";
import {
    FindCommentByBoardRequest,
    FindCommentByBoardResponse,
    SaveCommentRequest
} from "@/app/_type/comment/CommentRequestReponse";


export const saveComment = async (data: SaveCommentRequest) :Promise<ApiResponse> => {
    return await requestApiFetch<ApiResponse>('POST', '/comment/save', data);
}

export const findCommentByBoard = async (data: FindCommentByBoardRequest) :Promise<FindCommentByBoardResponse> => {
    return await requestApiFetch<FindCommentByBoardResponse>('GET', '/comment', data);
}
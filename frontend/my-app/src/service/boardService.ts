import {requestApiFetch} from "@/lib/requestApiFetch";
import {
    BoardDeleteRequest,
    BoardDetailRequest,
    BoardDetailResponse, BoardLikedRequest,
    BoardListResponse, BoardRegisterRequest, BoardRegisterResponse,
    BoardRequest
} from "@/app/_type/board/BoardRequestResponse";
import {ApiResponse} from "@/app/_type/CommonResponse";


export const getBoardList = async (data: BoardRequest) :Promise<BoardListResponse> => {
    return await requestApiFetch<BoardListResponse>('GET', '/board', data);
}

export const getBoardDetail = async (data: BoardDetailRequest) :Promise<BoardDetailResponse> => {
    return await requestApiFetch<BoardDetailResponse>('GET', `/board/${data.boardId}`);
}

export const getBoardRegister = async (data: BoardRegisterRequest) :Promise<BoardRegisterResponse> => {
    return await requestApiFetch<BoardRegisterResponse>('POST', `/board`, data);
}


export const boardLiked = async (data: BoardLikedRequest) :Promise<ApiResponse> => {
    return await requestApiFetch<ApiResponse>('POST', `/board/liked`, data);
}

export const boardDelete = async (data: BoardDeleteRequest) :Promise<ApiResponse> => {
    return await requestApiFetch<ApiResponse>('DELETE', `/board/${data.boardId}`);
}
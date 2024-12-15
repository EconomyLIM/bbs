import {ApiResponse} from "@/app/_type/CommonResponse";


/**
 * 게시판 조회
 */

export interface Board {
    id: string
    , title: string
    , content: string
    , likedCnt: number
    , memberEmail: string
    , nickname: string
    , mine: boolean
}

export interface BoardResponse extends ApiResponse{
    board: Board
}

export interface BoardListResponse extends ApiResponse{
    currentPage : number
    , totalPage : number
    , list: Board[]
}

export interface BoardRequest{
    page: number
}

export interface BoardDetailRequest{
    boardId: string
}

export interface BoardDetailResponse extends ApiResponse{
    board: Board
}

/**
 * 게시판 저장
 */

export interface BoardRegisterRequest {
    title: string
    , content: string
    , memberEmail: string
}

export interface BoardRegisterResponse extends ApiResponse{
    boardId: number
}

/**
 * 게시판 삭제
 */

export interface BoardDeleteRequest{
    boardId: string
}

/**
 * 댓글 저장
 */


/**
 *  추천 비추천
 */

export interface BoardLikedRequest {
    memberEmail: string
    , boardId: string
    , recommendationType : "LIKE" | "DISLIKE"
}
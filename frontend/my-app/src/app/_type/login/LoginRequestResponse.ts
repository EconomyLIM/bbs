import {ApiResponse} from "@/app/_type/CommonResponse";


export interface LoginRequest{
    email: string
    , password: string
}

export interface MemberDTO{
    email: string
    , username: string
    , nickname: string
}

export interface LoginResponse extends ApiResponse{
    memberdto: MemberDTO
    , accessToken:string
}
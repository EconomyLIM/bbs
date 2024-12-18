import {requestApiFetch} from "@/lib/requestApiFetch";
import {LoginRequest, LoginResponse} from "@/app/_type/login/LoginRequestResponse";
import {ApiResponse} from "@/app/_type/CommonResponse";


export const login = async (data: LoginRequest) :Promise<LoginResponse> => {
    return await requestApiFetch<LoginResponse>('POST', '/login', data);
}

export const checkedLogin = async (data: string) :Promise<ApiResponse> => {
    return await requestApiFetch<ApiResponse>('POST', '/validate-token');
}
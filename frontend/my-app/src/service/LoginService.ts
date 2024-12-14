import {requestApiFetch} from "@/lib/requestApiFetch";
import {LoginRequest, LoginResponse} from "@/app/_type/login/LoginRequestResponse";


export const login = async (data: LoginRequest) :Promise<LoginResponse> => {
    return await requestApiFetch<LoginResponse>('POST', '/login', data);
}
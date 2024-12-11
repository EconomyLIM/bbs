import axios, {AxiosRequestConfig} from "axios";
import {ApiResponse} from "@/app/_type/CommonResponse";

const axiosInstance = axios.create();



export const requestApiFetch = async <T extends ApiResponse>( // result 의 responseCode 를 사용하기 위해 기본응답값 상속
    method: 'GET' | 'POST' | 'PUT' | 'DELETE' | 'PATCH',
    url: string,
    body: object = {},
    useInterceptor : boolean = true
): Promise<T> => {
    // const token = localStorage.getItem('token'); // 'accessToken'을 실제 키로 변경
    const token = null;
    const config: AxiosRequestConfig = {
        headers: {
            'Content-Type': 'application/json',
            ...(token ? {Authorization: `Bearer ${token}`} : {})
        },
    };

    try {
        const axiosToUse = useInterceptor ? axiosInstance : axios; // 기본 axios 또는 인터셉터가 적용된 axiosInstance 선택

        const fullUrl = url.startsWith('http') ? url : `${process.env.NEXT_PUBLIC_API_BASE_URL}${url}`;
        if (!fullUrl) throw new Error('Base URL is not defined');

        const response = await axiosToUse.post('/api/fetch', {
            method,
            url: url,
            body,
        }, config);

        const result: T = response.data;
        console.log(result);

        return result;
    } catch (error) {
        console.error('Request error:', error);
        throw error;
    }
};
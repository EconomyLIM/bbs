import axios, {AxiosRequestConfig} from "axios";
import {ApiResponse} from "@/app/_type/CommonResponse";

const axiosInstance = axios.create();

// axiosInstance.interceptors.response.use(
//     (response) => {
//         const data = response.data;
//         // 여기서 응답 바디 내 code 또는 responseCode를 검사
//         if (data && data.responseCode === 'FORBIDDEN') {
//             // Alert 후 리다이렉트
//             if (typeof window !== 'undefined') {
//                 alert('권한이 없습니다. 다시 로그인해주세요.');
//                 const currentUrl = window.location.href;
//                 window.location.href = `/login?redirecturl=${encodeURIComponent(currentUrl)}`;
//             }
//             // Forbidden 상태 처리 후 더 이상 진행하지 않도록 Promise.reject
//             return Promise.reject(new Error('FORBIDDEN'));
//         }
//
//         // 정상 응답 시
//         return response;
//     },
//     (error) => {
//         // HTTP 에러(네트워크 에러, 5xx, 4xx) 처리
//         return Promise.reject(error);
//     }
// );



export const requestApiFetch = async <T extends ApiResponse>( // result 의 responseCode 를 사용하기 위해 기본응답값 상속
    method: 'GET' | 'POST' | 'PUT' | 'DELETE' | 'PATCH',
    url: string,
    body: object = {},
    useInterceptor : boolean = true
): Promise<T> => {
    const token = localStorage.getItem('token'); // 'accessToken'을 실제 키로 변경
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

        // "FORBIDDEN" 응답 처리
        if (result && result.code === 'FORBIDDEN') {
            alert('다시 로그인해주세요.');
            window.location.href = `/login?redirecturl=${encodeURIComponent(url)}`;
            // 로그인 페이지로 이동 후에는 함수 진행 중단
            return result;
        }

        return result;
    } catch (error) {
        console.error('Request error:', error);
        throw error;
    }
};
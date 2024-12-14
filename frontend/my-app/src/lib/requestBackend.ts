import axios, {AxiosRequestConfig} from 'axios';

type AllowedMethods = 'GET' | 'POST' | 'PUT' | 'DELETE';


export const requestBackend = async (
    method: AllowedMethods,
    endpoint: string,
    paramsOrData: object = {},
    config: AxiosRequestConfig = {},
) => {
    try {
        // 환경 변수에서 TSC_BACK_URL 가져오기
        const baseUrl = process.env.NEXT_BACK_URL;
        if (!baseUrl) {
            throw new Error('TSC_BACK_URL is not defined in the environment variables');
        }

        // 전체 URL 생성
        const fullUrl = `${baseUrl}${endpoint}`;

        console.log("fullUrl = ", fullUrl);

        // axios 설정 생성
        const axiosConfig = method === 'GET'
            ? {params: paramsOrData, ...config}
            : {...config, data: paramsOrData};

        // axios 요청
        const response = await axios({
            method,
            url: fullUrl,
            ...axiosConfig,
        });

        // 응답 데이터 반환
        if(process.env.NODE_ENV === 'development'){
            console.log({...response.data,url:fullUrl});
        }

        return response.data;
    } catch (error) {
        console.error(`Error with ${method.toUpperCase()} request to backend : {url : ${endpoint}`, error);
        throw error;
    }
};

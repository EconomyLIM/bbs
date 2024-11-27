import {requestApiFetch} from "../../lib/requestApiFetch";
import {ApiResponse} from "../_type/CommonResponse";


export default function TestComponent(){

    const aaa = () => {
        const getPayInformation = async (): Promise<ApiResponse> => {
            return await requestApiFetch<ApiResponse>('POST', '/member/add');
        };

        console.log(getPayInformation());
    }

    return(
        <>
            <h3>테스트 컴포넌트입니다.</h3>
            <button onClick={aaa}>23123</button>
        </>
    )
}
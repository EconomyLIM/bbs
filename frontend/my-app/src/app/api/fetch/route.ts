import {NextResponse} from "next/server";
import {requestBackend} from "../../../lib/requestBackend";

export async function POST(request: Request) {
    try {
        const requestBody = await request.json();
        if(!requestBody.method || !requestBody.url || !requestBody.body){
            return NextResponse.json({ error: 'method or url or body is null' }, { status: 500 });
        }

        // Authorization 헤더 추출
        const authorizationHeader = request.headers.get('Authorization');

        // 요청 백엔드로 전달할 헤더 설정
        const config = {
            headers: {
                Authorization: authorizationHeader ??'', // Authorization 헤더를 백엔드 요청에 포함
                'Content-Type': 'application/json',
            },
        };

        const data = await requestBackend(requestBody.method, requestBody.url, requestBody.body, config);
        return NextResponse.json(data);
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
    } catch (error) {
        
        return NextResponse.json({ error: 'Failed to fetch data' }, { status: 500 });
    }
}
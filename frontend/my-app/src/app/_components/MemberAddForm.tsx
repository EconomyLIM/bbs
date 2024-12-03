"use client"
import {requestApiFetch} from "../../lib/requestApiFetch";
import {ApiResponse} from "../_type/CommonResponse";
import {useState} from "react";
import {useRouter} from "next/navigation";

interface MemberAdd {
    email: string;
    password: string;
    username: string;
    nickname: string;
}

export default function MemberAddForm() {

    const router = useRouter();
    const [memberAddForm, setMemberAddForm] = useState<MemberAdd>({
        email: '',
        password: '',
        username: '',
        nickname: '',
    });

    const memberAddApi = async () => {
        const getPayInformation = async (): Promise<ApiResponse> => {
            return await requestApiFetch<ApiResponse>('POST', '/member/add', memberAddForm);
        };
        const payInformation = await getPayInformation();


        if (payInformation.code === 'OK'){
            router.push("/");
        }
    };

    const handle = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setMemberAddForm((prevForm) => ({
            ...prevForm,
            [name]: value,
        }));
    };

    return (
        <>
            <div className={"container"}>
                <form role="form" action="/members/new" method="post">
                    <div className={"form-group"}>
                        <label htmlFor={"email"}>이메일</label>
                        <input
                            type="text"
                            name="email"
                            id="email"
                            placeholder="이메일을 입력하세요"
                            value={memberAddForm.email}
                            onChange={handle}
                            className={"form-control"}
                        />
                    </div>
                    <div className={"form-group"}>
                        <label htmlFor={"password"}>비밀번호</label>
                        <input
                            type="password"
                            name="password"
                            id="password"
                            placeholder="비밀번호를 입력하세요"
                            value={memberAddForm.password}
                            onChange={handle}
                            className={"form-control"}
                        />
                    </div>
                    <div className={"form-group"}>
                        <label htmlFor={"username"}>이름</label>
                        <input
                            type="text"
                            name="username"
                            id="username"
                            placeholder="이름을 입력하세요"
                            value={memberAddForm.username}
                            onChange={handle}
                            className={"form-control"}
                        />
                    </div>
                    <div className={"form-group"}>
                        <label htmlFor={"nickname"}>닉네임</label>
                        <input
                            type="text"
                            name="nickname"
                            id="nickname"
                            placeholder="닉네임을 입력하세요"
                            value={memberAddForm.nickname}
                            onChange={handle}
                            className={"form-control"}
                        />
                    </div>
                    <button type={"button"} className={"btn btn-primary"} onClick={memberAddApi}>
                        저장
                    </button>
                </form>
            </div>
        </>
    );
}

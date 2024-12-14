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
            <main>
                <div className={"auth-container"}>
                    <h2>Sign Up</h2>
                    <form>
                        <label htmlFor="email">Email</label>
                        <input type="email" id="email" placeholder="you@example.com" value={memberAddForm.email}
                               onChange={handle} required/>

                        <label htmlFor="password">Password</label>
                        <input type="password" id="password" placeholder="********" value={memberAddForm.password}
                               onChange={handle} required/>

                        <label htmlFor="nickname">User Name</label>
                        <input type="text" id="nickname" placeholder="Your nickname" value={memberAddForm.username}
                               onChange={handle} required/>

                        <label htmlFor="nickname">Nickname</label>
                        <input type="text" id="nickname" placeholder="Your nickname" value={memberAddForm.nickname}
                               onChange={handle} required/>

                        <button type={"button"} onClick={memberAddApi}>
                            회원가입
                        </button>
                    </form>
                    <p className="switch-link">
                        Already have an account? <a href="login.html">Login here</a>
                    </p>
                </div>
            </main>
        </>
    );
}

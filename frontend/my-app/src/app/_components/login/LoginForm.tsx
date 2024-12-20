"use client"
import {useState} from "react";
import {LoginRequest} from "@/app/_type/login/LoginRequestResponse";
import {login} from "@service/LoginService";
import {useRouter} from "next/navigation";


export default function LoginForm() {
    const router = useRouter();
    const [loginFormData, setLoginFormData] = useState<LoginRequest>({
        email: '',
        password: '',
    });

    const loginAPI = async () => {

        const loginResponse = await login(loginFormData);
        if (loginResponse.code === 'OK'){
            localStorage.setItem("token", loginResponse.accessToken);
            alert("로그인 되었습니다.");
            router.push("/")
        }else{
            alert(loginResponse.message);
        }
    };

    const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
        if (e.key === 'Enter') {
            e.preventDefault(); // 기본 동작 방지 (필요한 경우)
            loginAPI();
        }
    };

    const handle = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setLoginFormData((prevForm) => ({
            ...prevForm,
            [name]: value,
        }));
    };

    return (
        <>
            <main>
                <div className={"auth-container"}>
                    <h2>Sign Up</h2>
                    <form role="form" action="/members/new" method="post">

                        <label htmlFor={"email"}>이메일</label>
                        <input
                            type="email"
                            name="email"
                            id="email"
                            placeholder="이메일을 입력하세요"
                            value={loginFormData.email}
                            onChange={handle}
                            className={"form-control"}
                        />

                        <label htmlFor={"password"}>비밀번호</label>
                        <input
                            type="password"
                            name="password"
                            id="password"
                            placeholder="비밀번호를 입력하세요"
                            value={loginFormData.password}
                            onChange={handle}
                            className={"form-control"}
                            onKeyDown={handleKeyDown}
                        />
                        <button type={"button"} onClick={loginAPI}>로그인</button>
                    </form>
                    <p className="switch-link">
                        Don&#39;t have an account? <a href="/member/new">Create one now</a>
                    </p>
                </div>
            </main>
        </>
    )
}
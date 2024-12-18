"use client";

import React, { useState, useEffect } from 'react';
import {checkedLogin} from "@service/LoginService";
import {useRouter} from "next/navigation";

interface HeaderProps {}

const Header: React.FC<HeaderProps> = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const router = useRouter();

    useEffect(() => {

        const validateToken = async () => {
            const token = localStorage.getItem('token');

            if (!token) {
                setIsLoggedIn(false);
                return;
            }

            const response = await checkedLogin(token);

            if (response.code === "OK") {
                setIsLoggedIn(true);
            } else {
                localStorage.removeItem('token');
                setIsLoggedIn(false);
            }
        };

        validateToken();
    }, []);

    const handleLogout = () => {
        if (!confirm("정말 로그아웃 하시겠습니까?")){
            return;
        }
        localStorage.removeItem('token');
        setIsLoggedIn(false);
        alert("로그아웃 되었습니다.");
        router.push("/login");
        // 로그아웃 API 호출 (선택 사항)
    };

    return (
        <header>
            <a  className="search-bar" href={"/board/list"}>BBS</a>
            <div className="auth-buttons">
                {isLoggedIn ? (
                    <>
                        <button onClick={handleLogout} className="logout-button">🔓 Logout</button>
                        <a href="/myinfo" className="myinfo-button">👤 My Info</a>
                    </>
                ) : (
                    <>
                        <a href="/signup" className="signup-button">📝 Sign Up</a>
                        <a href="/login" className="login-button">🔒 Login</a>
                    </>
                )}
            </div>
        </header>
    );
};

export default Header;
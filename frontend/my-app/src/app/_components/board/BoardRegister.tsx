"use client"
import {useState} from "react";
import {getBoardRegister} from "@service/boardService";
import {BoardRegisterRequest} from "@/app/_type/board/BoardRequestResponse";
import {useRouter} from "next/navigation";

const defaultValue = {
    title: '',
    content: '',
    memberEmail: "test@test.com"
}
export function BoardRegister(){

    const router = useRouter();
    const [boardRegisterForm, setBoardRegisterForm] = useState<BoardRegisterRequest>({
        ...defaultValue
    });

    const handle = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setBoardRegisterForm((prevForm) => ({
            ...prevForm,
            [name]: value,
        }));
    };
    const boardRegisterAPI = async () => {
        const boardRegister = await getBoardRegister(boardRegisterForm);

        if (boardRegister.code === 'OK'){
            router.push(`/board/${boardRegister.boardId}`);
        }
    };
    return(
        <>
            <div className={"container"}>
                <div className={"form-group"}>
                    <label htmlFor={"title"}>제목</label>
                    <input
                        type="text"
                        name="title"
                        id="title"
                        placeholder="제목 입력하세요"
                        value={boardRegisterForm.title}
                        onChange={handle}
                        className={"form-control"}
                        required={true}
                    />
                </div>
                <div className={"form-group"}>
                    <label htmlFor={"content"}>본문</label>
                    <input
                        type="text"
                        name="content"
                        id="content"
                        placeholder="본문 입력하세요"
                        value={boardRegisterForm.content}
                        onChange={handle}
                        className={"form-control"}
                        required={true}
                    />
                </div>
                <div className={"form-group"}>
                    <label htmlFor={"content"}>이메일</label>
                    <input
                        type="email"
                        name="email"
                        id="email"
                        placeholder="본문 입력하세요"
                        value={boardRegisterForm.memberEmail}
                        onChange={handle}
                        className={"form-control"}
                        required={true}
                    />
                </div>
                <button type={"button"} className={"btn btn-primary"} onClick={boardRegisterAPI}>
                    저장
                </button>
            </div>
        </>
    )
}
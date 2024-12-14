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

    const handle = (e: React.ChangeEvent<HTMLInputElement> | React.ChangeEvent<HTMLTextAreaElement>) => {
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
            <main>
                <div className="editor-container">
                    <div className="category-select">
                        <label htmlFor="category">카테고리:</label>
                        <select id="category">
                            <option value="general">일반</option>
                            <option value="news">뉴스</option>
                            <option value="qna">Q&A</option>
                            <option value="tips">팁/노하우</option>
                        </select>
                    </div>

                    <div className="post-title">
                        <input type="text" value={boardRegisterForm.title} id="title" name={"title"}
                               onChange={handle} placeholder="글 제목을 입력하세요"/>
                    </div>

                    <div className="toolbar">
                        <button type="button" data-cmd="bold"><b>B</b></button>
                        <button type="button" data-cmd="italic"><i>I</i></button>
                        <button type="button" data-cmd="underline"><u>U</u></button>
                        <select id="fontSizeSelect">
                            <option value="">폰트 크기</option>
                            <option value="1">작게</option>
                            <option value="3">기본</option>
                            <option value="5">크게</option>
                        </select>
                        <select id="fontNameSelect">
                            <option value="">글꼴 선택</option>
                            <option value="Arial">Arial</option>
                            <option value="Georgia">Georgia</option>
                            <option value="Tahoma">Tahoma</option>
                            <option value="Courier New">Courier New</option>
                        </select>

                        <button type="button" id="insertImageBtn">이미지 삽입</button>
                    </div>

                    <div className="editor" >
                        <textarea value={boardRegisterForm.content} name={"content"} id={"content"} onChange={handle} style={{width:'100%', height:'500px'}}>
                        여기에 본문을 입력하거나 편집 기능을 사용할 수 있습니다.
                        </textarea>
                    </div>

                    {/*<input*/}
                    {/*    type="text"*/}
                    {/*    name="content"*/}
                    {/*    id="content"*/}
                    {/*    placeholder="본문 입력하세요"*/}
                    {/*    value={boardRegisterForm.content}*/}
                    {/*    onChange={handle}*/}
                    {/*    className={"form-control"}*/}
                    {/*    required={true}*/}
                    {/*/>*/}

                    <div className="submit-area">
                        <button type="button">취소</button>
                        <button type="button" onClick={boardRegisterAPI}>등록</button>
                    </div>
                </div>
            </main>
        </>
    )
}
"use client"

import {useEffect, useState} from "react";
import {Board} from "@/app/_type/board/BoardRequestResponse";
import {getBoardDetail} from "@service/boardService";
import {useRouter} from "next/navigation";


interface BoardDetailProps {
    id: string;
}

export function BoardUpdate({ id }: BoardDetailProps) {
    const [board, setBoard] = useState<Board>({
        id: '',
        title: '',
        content: '',
        likedCnt: 0,
        memberEmail: '',
        nickname: '',
        mine: false,
         categoryId: ''
        , categoryName: ''
        , registeredDate: ''
    });
    const router = useRouter();

    const boardDetailAPI = async () => {
        const getBoard = await getBoardDetail({ boardId: id });
        if (getBoard.code ===  "OK"){
            if(!getBoard.board.mine){
                alert("권한이 없습니다.")
                router.push("/board/list");
            }else{
                setBoard(getBoard.board); // 데이터 설정
            }
        }
        else{
            alert(getBoard.message);
            router.push("/board/list");
        }
    };

    useEffect(() => {
        boardDetailAPI();
    }, [id]);

    const handle = (e: React.ChangeEvent<HTMLInputElement> | React.ChangeEvent<HTMLTextAreaElement>) => {
        const { name, value } = e.target;

        setBoard(prevBoard => ({
            ...prevBoard,
            [name]: value,
        }));

    };


    return (
        <>
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
                    <input type="text" value={board?.title} id="title" name={"title"}
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

                <div className="editor">
                        <textarea value={board?.content || ''} name={"content"} id={"content"} onChange={handle}
                                  style={{width: '100%', height: '500px'}}>
                        </textarea>
                </div>

                <div className="submit-area">
                    <button type="button">취소</button>
                    <button type="button">등록</button>
                </div>
            </div>
        </>
    )
}
"use client"


import {useEffect, useState} from "react";
import {useRouter} from "next/navigation";
import {Board} from "@/app/_type/board/BoardRequestResponse";
import {getBoardList} from "@service/boardService";


export function BoardList(){

    const [boardList, setBoardList] = useState<Board[]>([]);
    const router = useRouter();

    const [currentPage, setCurrentPage] = useState<number>(1);
    const [totalPages, setTotalPages] = useState<number>(10)

    const boardListAPI = async () => {
        const getList = await getBoardList({page: currentPage});
        setBoardList(getList.list); // 데이터 설정
        setTotalPages(getList.totalPage);

    };

    useEffect(() => {
        boardListAPI();
    }, []);

    const handlePageChange = (pageNum: number) => {
        setCurrentPage(pageNum); // 현재 페이지 변경
    };

    useEffect(() => {
        boardListAPI();
    }, [currentPage]);

    const handleRowClick = (id: number) => {
        router.push(`/board/${id}`); // 클릭 시 페이지 이동
    };

    return(
        <>
            <div className="container mt-4">
                <h1 className="mb-4">Board List</h1>
                <table className="table table-hover">
                    <thead className="thead-light">
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Title</th>
                        <th scope="col">Content</th>
                        <th scope="col">Likes</th>
                    </tr>
                    </thead>
                    <tbody>
                    {boardList.map((board) => (
                        <tr
                            key={board.id}
                            className="table-row"
                            onClick={() => handleRowClick(board.id)}
                            style={{cursor: 'pointer'}}
                        >
                            <td>{board.id}</td>
                            <td>{board.title}</td>
                            <td>{board.content}</td>
                            <td>{board.likedCnt.toString()}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
                <nav>
                    <ul className="pagination justify-content-center">
                        {[...Array(totalPages)].map((_, index) => (
                            <li
                                key={index}
                                className={`page-item ${
                                    index + 1 === currentPage ? 'active' : ''
                                }`}
                                onClick={() => handlePageChange(index + 1)}
                                style={{cursor: 'pointer'}}
                            >
                                <span className="page-link">{index + 1}</span>
                            </li>
                        ))}
                    </ul>
                </nav>
            </div>
            <a href={"/board/new"}>새로 작성하기</a>
        </>
    )
}
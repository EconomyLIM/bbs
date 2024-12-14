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

    // useEffect(() => {
    //     boardListAPI();
    // }, []);

    const handlePageChange = (pageNum: number) => {
        setCurrentPage(pageNum); // 현재 페이지 변경
    };

    useEffect(() => {
        boardListAPI();
    }, [currentPage]);

    const handleRowClick = (id: number) => {
        router.push(`/board/${id}`); // 클릭 시 페이지 이동
    };

    return (
        <>
            <header>
                <div className="search-bar">
                    <input type="text" placeholder="Search..."/>
                </div>
                <a href="#" className="login">🔒 Login</a>
            </header>

            <main>
                <section className="sidebar-left">
                    <nav className="forums-nav">
                        <a href="#" className="active">All Forums</a>
                        <a href="#">Topics</a>
                    </nav>

                    <div className="forum-search">
                        <input type="text" placeholder="Search"/>
                        <button>🔍</button>
                    </div>

                    {/* forum-row 형식으로 게시판 리스트 렌더링 */}
                    {boardList.map(board => (
                        <a href={`/board/${board.id}`} key={board.id}>
                        <div className="forum-row" >
                            <div className="forum-icon">📂</div>
                            <div className="forum-info">
                                <h3>{board.title}</h3>
                                {/*<p>{board.description}</p>*/}
                                <span>testtest</span>
                                {/*<a href="#" className="read-more">Read more</a>*/}
                            </div>
                            <div className="forum-stats">
                                {/*<span>{board.topics} Topics</span>*/}
                                {/*<span>{board.posts} Posts</span>*/}
                                <span>testtest</span>
                                <span>testtest</span>
                            </div>
                            <div className="last-post">
                                {/*<span>{board.lastPost}</span>*/}
                                <span>testtest</span>
                            </div>
                        </div>
                        </a>
                    ))}
                </section>

                <aside className="sidebar-right">
                    <div className="recent-topics">
                        <h4>Recent Topics</h4>
                        <ul>
                            <li>ceshiyigezhuti</li>
                            <li>aaaaaaaaaaaaaaaaaa</li>
                            <li>test</li>
                            <li>mmmmmmmmmmmmm</li>
                            <li>test4</li>
                        </ul>

                        <div className="footer-links">
                            <a href="#">Home</a>
                            <a href="#">About Us</a>
                            <a href="#">FAQs</a>
                            <a href="#">Blog</a>
                            <a href="#">Contact</a>
                        </div>
                    </div>
                </aside>
            </main>
        </>
    );
}
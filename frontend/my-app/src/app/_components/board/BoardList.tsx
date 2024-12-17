"use client"


import {useEffect, useState} from "react";
import {useRouter} from "next/navigation";
import {Board} from "@/app/_type/board/BoardRequestResponse";
import {getBoardList} from "@service/boardService";

const PAGE_SIZE = 10; // 한 페이지에 보여줄 페이지 수

export function BoardList(){

    const [boardList, setBoardList] = useState<Board[]>([]);
    const router = useRouter();

    const [currentPage, setCurrentPage] = useState<number>(1);
    const [totalPages, setTotalPages] = useState<number>(1);

    const boardListAPI = async () => {
        const getList = await getBoardList({page: currentPage});
        setBoardList(getList.list); // 데이터 설정
        setTotalPages(getList.totalPage);
    };

    useEffect(() => {
        boardListAPI();
    }, [currentPage]);

    /**
     * 페이징 관련
     */

        // 페이지 그룹 계산
    const currentGroup = Math.ceil(currentPage / PAGE_SIZE); // 현재 페이지 그룹
    const startPage = (currentGroup - 1) * PAGE_SIZE + 1; // 그룹 시작 페이지
    const endPage = Math.min(currentGroup * PAGE_SIZE, totalPages); // 그룹 끝 페이지

    // 페이지 이동 이벤트
    const handlePageChange = (pageNum: number) => {
        setCurrentPage(pageNum);
    };

    const handlePrevGroup = () => {
        setCurrentPage((currentGroup - 1) * PAGE_SIZE);
    };

    const handleNextGroup = () => {
        setCurrentPage(currentGroup * PAGE_SIZE + 1);
    };

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
                            <div className="forum-row">
                                <div className="forum-icon">📂</div>
                                <div className="forum-info">
                                    <h3>{board.title}</h3>
                                    {/*<p>{board.description}</p>*/}
                                    <span>{board.categoryName}</span>
                                    {/*<a href="#" className="read-more">Read more</a>*/}
                                </div>
                                <div className="forum-stats">
                                    {/*<span>{board.topics} Topics</span>*/}
                                    <span>{board.registeredDate}</span>
                                    <span>{board.nickname}</span>
                                    {/*<span>추천수:</span>*/}
                                </div>
                                <div className="last-post">
                                    <span>추천수:</span>
                                    <span>{board.likedCnt}</span>
                                </div>
                            </div>
                        </a>
                    ))}
                </section>

                <div className="pagination">
                    {/* << 버튼 */}
                    {currentGroup > 1 && (
                        <button onClick={handlePrevGroup}>«</button>
                    )}

                    {/* 페이지 번호 버튼 */}
                    {Array.from({ length: endPage - startPage + 1 }, (_, i) => (
                        <button
                            key={startPage + i}
                            onClick={() => handlePageChange(startPage + i)}
                            className={currentPage === startPage + i ? "active" : ""}
                        >
                            {startPage + i}
                        </button>
                    ))}

                    {/* >> 버튼 */}
                    {totalPages > currentGroup * PAGE_SIZE && (
                        <button onClick={handleNextGroup}>»</button>
                    )}
                </div>
            </main>
        </>
    );
}

{/*<aside className="sidebar-right">*/}
{/*    <div className="recent-topics">*/}
{/*        <h4>Recent Topics</h4>*/}
{/*        <ul>*/}
{/*            <li>ceshiyigezhuti</li>*/}
{/*            <li>aaaaaaaaaaaaaaaaaa</li>*/}
{/*            <li>test</li>*/}
{/*            <li>mmmmmmmmmmmmm</li>*/}
{/*            <li>test4</li>*/}
{/*        </ul>*/}

{/*        <div className="footer-links">*/}
{/*            <a href="#">Home</a>*/}
{/*            <a href="#">About Us</a>*/}
{/*            <a href="#">FAQs</a>*/}
{/*            <a href="#">Blog</a>*/}
{/*            <a href="#">Contact</a>*/}
{/*        </div>*/}
{/*    </div>*/}
{/*</aside>*/}
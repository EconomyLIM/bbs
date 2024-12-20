"use client"


import {useEffect, useState} from "react";
import {useRouter} from "next/navigation";
import {Board, BoardRequest} from "@/app/_type/board/BoardRequestResponse";
import {getBoardList} from "@service/boardService";
import {getCategoryList} from "@service/CategoryService";
import {Category} from "@/app/_type/category/CategoryRequestResponse";

const PAGE_SIZE = 10; // 한 페이지에 보여줄 페이지 수

export function BoardList(){

    const [boardList, setBoardList] = useState<Board[]>([]);
    const [categoryList, setCategoryList] = useState<Category[]>([]);
    const [boardRequest, setBoardRequest] = useState<BoardRequest>({
        page: 1, // 기본값 설정
        categoryId: undefined,
        searchWord: undefined,
    });
    const [searchWord, setSearchWord] = useState<string | undefined>('');

    const [currentPage, setCurrentPage] = useState<number>(1);
    const [totalPages, setTotalPages] = useState<number>(1);

    const boardListAPI = async () => {

        const getList = await getBoardList(boardRequest);
        setBoardList(getList.list); // 데이터 설정
        setTotalPages(getList.totalPage);
    };

    useEffect(() => {
        boardListAPI();
        getCategories();
    }, [boardRequest]);

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
        setBoardRequest((prev) => ({ ...prev, page: pageNum }));
    };

    const handlePrevGroup = () => {
        setCurrentPage((currentGroup - 1) * PAGE_SIZE);
        setBoardRequest((prev) => ({ ...prev, page: (currentGroup - 1) * PAGE_SIZE }));
    };

    const handleNextGroup = () => {
        setCurrentPage(currentGroup * PAGE_SIZE + 1);
        setBoardRequest((prev) => ({ ...prev, page:  currentGroup * PAGE_SIZE + 1 }));
    };

    const getCategories = async () =>{
        const categoryList = await getCategoryList();
        if (categoryList.code === "OK"){
            setCategoryList(categoryList.categories);
        }
    }

    // 카테고리 변경 핸들러
    const handleCategoryChange = (categoryId?: string) => {
        setBoardRequest((prev) => ({ ...prev, categoryId, page: 1 }));
        setCurrentPage(1);
    };

    // 검색어 변경 핸들러
    const handleSearchChange = (searchWord: string) => {
        setBoardRequest((prev) => ({ ...prev, searchWord }));
    };

    // 카테고리 클릭 이벤트 방지 및 상태 업데이트
    const handleCategoryClick = (e: React.MouseEvent<HTMLAnchorElement>, categoryId?: string) => {
        e.preventDefault(); // # 이벤트 방지
        handleCategoryChange(categoryId);
    };

    const onChangeSearchWord = (e: React.ChangeEvent<HTMLInputElement>) => {
        setSearchWord(e.target.value);
    }

    const onClickSearchWord = () => {
        setBoardRequest((prev) => ({ ...prev, searchWord: searchWord }));
        setCurrentPage(1);
    }

    return (
        <>

            <main>
                <section className="sidebar-left">
                    <nav className="forums-nav">
                        <a href="#" className={boardRequest.categoryId === undefined ? "active" : ""}
                           onClick={(e) => handleCategoryClick(e, undefined)}>
                            All Forums
                        </a>
                        {categoryList &&
                            categoryList.map((category) => (
                                <a
                                    className={boardRequest.categoryId === category.categoryId ? "active" : ""}
                                    href="#"
                                    key={`category-${category.categoryId}`}
                                    onClick={(e) => handleCategoryClick(e, category.categoryId)}
                                >
                                    {category.categoryName}
                                </a>
                            ))}
                    </nav>

                    <div className={"auth-buttons"} style={{background: "#f9f9f9"}}>
                        <a href={"/board/new"} style={{float: "right", background: "#f9f9f9"}}>
                          새로 작성하기
                        </a>
                    </div>

                    <div className="forum-search">
                        <input type="text" placeholder="Search" value={searchWord} onChange={(e) => {
                            onChangeSearchWord(e)
                        }}/>
                        <button onClick={() => {
                            onClickSearchWord()
                        }}>🔍
                        </button>
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
                    {Array.from({length: endPage - startPage + 1}, (_, i) => (
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

{/*<aside className="sidebar-right">*/
}
{/*    <div className="recent-topics">*/
}
{/*        <h4>Recent Topics</h4>*/
}
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
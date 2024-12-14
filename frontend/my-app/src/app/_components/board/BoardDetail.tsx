"use client"

import {useEffect, useState} from "react";
import {Board, BoardLikedRequest} from "@/app/_type/board/BoardRequestResponse";
import {boardLiked, getBoardDetail} from "@service/boardService";
import {Comments, SaveCommentRequest} from "@/app/_type/comment/CommentRequestReponse";
import {findCommentByBoard, saveComment} from "@service/CommentService";


interface BoardDetailProps {
    id: string;
}


export function BoardDetail({ id }: BoardDetailProps) {
    const [board, setBoard] = useState<Board>();
    const [comments, setComments] = useState<Comments[]>([]);
    const [newComment, setNewComment] = useState<string>('');
    const [replyTo, setReplyTo] = useState<bigint | null>(null); // 답글 대상 ID

    const getCommentAPI = async () => {
        const getComment = await findCommentByBoard({ boardId: id });
        setComments(getComment.comments);
    }
    const boardDetailAPI = async () => {
        const getBoard = await getBoardDetail({ boardId: id });
        setBoard(getBoard.board); // 데이터 설정
    };

    useEffect(() => {


        boardDetailAPI().then(()=>{
            getCommentAPI();
        });

    }, [id]);

    if (!board) {
        return <div>Loading...</div>;
    }

    // 댓글 추가 함수
    // const addComment = (content: string, parentId: string | null = null) => {
    //     // const newComment: Comment = {
    //     //     id: Math.random().toString(36).substring(2, 15), // 랜덤 ID 생성
    //     //     content,
    //     //     parentId,
    //     //     children: [],
    //     // };
    //     //
    //     // if (parentId === null) {
    //     //     // 최상위 댓글 추가
    //     //     setComments((prev) => [...prev, newComment]);
    //     // } else {
    //     //     // 답글 추가
    //     //     setComments((prev) =>
    //     //         prev.map((comment) =>
    //     //             comment.id === parentId
    //     //                 ? { ...comment, children: [...comment.children, newComment] }
    //     //                 : comment
    //     //         )
    //     //     );
    //     // }
    // };

    // 댓글 렌더링 함수 (재귀적으로 자식 댓글 렌더링)
    const renderComments = (comments: Comments[]) =>
        comments.map((comment) => (
            <div key={comment.commentId}>
                <p>
                    {comment.commentContent}{' '}
                    <button onClick={() => setReplyTo(comment.commentId)}>답글</button>
                </p>
                {/*{renderComments(comment.children)}*/}
            </div>
        ));

    const addComent = async () => {
        const obj= {
            boardId: id
            , memberEmail: "test@test.com"
            , commentContent: newComment
        } as SaveCommentRequest;

        const getComment = await saveComment(obj);
        if (getComment.code === "OK"){
            getCommentAPI();
        }
    }

    const updateLikes = async (id: number, isLike: boolean) => {
        const requestObj: BoardLikedRequest = {
            memberEmail: 'test@test.com',
            boardId: id.toString(),
            recommendationType: isLike ? "LIKE" : "DISLIKE",
        };

        const response = await boardLiked(requestObj);

        if (response.code === "OK") {
            alert(isLike ? "추천되었습니다" : "비추천되었습니다");
            boardDetailAPI();
        }else{
            alert(response.message);
        }
    }

    return (
        <>
            <main>
                <div className="post-container" id="post">
                    <h1>{board.title}</h1>
                    <div className="post-category">카테고리: 뉴스</div>
                    <div className="post-meta">작성자: {board.memberEmail} | 2024-12-14</div>
                    <div className="post-content">
                        {board.content}
                    </div>
                    <div className="like-dislike" data-id="post" data-type="post">
                        <button className="like-btn" onClick={() => updateLikes(board.id, true)}>👍</button>
                        <span className="like-count">{board.likedCnt}</span>
                        <button className="dislike-btn" onClick={() => updateLikes(board.id, false)}>👎</button>
                    </div>
                </div>

                <div className={"comments-section"}>
                    <h2>댓글</h2>
                    <div className="comment" id="comment-1">
                        <div className="comment-author">Alice</div>
                        <div className="comment-meta">2024-12-14 10:00</div>
                        <div className="comment-content">
                            이 게시글에 대한 댓글 내용입니다. 정말 유용한 정보네요.
                        </div>
                        <div className="like-dislike" data-id="comment-1" data-type="comment">
                            <button className="like-btn">👍</button>
                            <span className="like-count">0</span>
                            <button className="dislike-btn">👎</button>
                        </div>
                        <a href="#" className="comment-actions" data-target="#comment-1">답글</a>


                        <div className="replies">
                            <div className="reply" id="reply-bob">
                                <div className="comment-author">Bob</div>
                                <div className="comment-meta">2024-12-14 10:30</div>
                                <div className="comment-content">
                                    Alice 님의 댓글에 대한 대댓글 예시입니다.
                                </div>
                                <div className="like-dislike" data-id="reply-bob" data-type="comment">
                                    <button className="like-btn">👍</button>
                                    <span className="like-count">0</span>
                                    <button className="dislike-btn">👎</button>
                                </div>
                            </div>

                            <div className="reply-form">
                                <input type="text" placeholder="대댓글 입력"/>
                                    <button type="button">등록</button>
                            </div>
                        </div>
                    </div>

                    <div className="comment" id="comment-2">
                        <div className="comment-author">Charlie</div>
                        <div className="comment-meta">2024-12-14 11:00</div>
                        <div className="comment-content">
                            두 번째 댓글 내용입니다. 좋은 정보 감사합니다.
                        </div>
                        <div className="like-dislike" data-id="comment-2" data-type="comment">
                            <button className="like-btn">👍</button>
                            <span className="like-count">0</span>
                            <button className="dislike-btn">👎</button>
                        </div>
                        <a href="#" className="comment-actions" data-target="#comment-2">답글</a>

                        <div className="replies">

                            <div className="reply-form">
                                <input type="text" placeholder="대댓글 입력"/>
                                    <button type="button">등록</button>
                            </div>
                        </div>
                    </div>

                    <div className="comment-input">
                        <input type="text" placeholder="댓글을 입력하세요"/>
                            <button type="button">등록</button>
                    </div>

                </div>

            </main>
        </>

);
}
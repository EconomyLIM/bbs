"use client"

import {useEffect, useState} from "react";
import {Board, BoardLikedRequest} from "@/app/_type/board/BoardRequestResponse";
import {boardDelete, boardLiked, getBoardDetail} from "@service/boardService";
import {Comments, SaveCommentRequest} from "@/app/_type/comment/CommentRequestReponse";
import {findCommentByBoard, saveComment} from "@service/CommentService";
import {useRouter} from "next/navigation";


interface BoardDetailProps {
    id: string;
}

export function BoardDetail({ id }: BoardDetailProps) {
    const [board, setBoard] = useState<Board>();
    const [comments, setComments] = useState<Comments[]>([]);
    const [newComment, setNewComment] = useState<string>('');
    const [openReplies, setOpenReplies] = useState<Record<string, boolean>>({});
    const [replyInputs, setReplyInputs] = useState<Record<string, string>>({});
    const router = useRouter();

    const getCommentAPI = async () => {
        const getComment = await findCommentByBoard({ boardId: id });
        setComments(getComment.comments);
    }
    const boardDetailAPI = async () => {
        const getBoard = await getBoardDetail({ boardId: id });
        if (getBoard.code ===  "OK"){
            setBoard(getBoard.board); // 데이터 설정
            console.log(getBoard.board);
        }else{
            alert(getBoard.message);
            router.push("/board/list");
        }
    };

    useEffect(() => {
        boardDetailAPI().then(()=>{
            getCommentAPI();
        });

    }, [id]);

    if (!board) {
        return <div>Loading...</div>;
    }

    const boardUpdateLikes = async (id: string, isLike: boolean) => {
        const requestObj: BoardLikedRequest = {
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

    /**
     * 댓글 관련
     */
    const addComment = async () => {
        const obj= {
            boardId: id,
            memberEmail: "test@test.com",
            commentId: "", // 신규 등록 시 빈값 혹은 없는 형태로
            commentContent: newComment,
        } as SaveCommentRequest;

        const getComment = await saveComment(obj);
        if (getComment.code === "OK"){
            setNewComment('');
            getCommentAPI();
        } else {
            alert(getComment.message);
        }
    }

    const toggleReplies = (commentId: string) => {
        setOpenReplies((prev) => ({
            ...prev,
            [commentId.toString()]: !prev[commentId.toString()],
        }));
    };

    const boardDeleteApi = async ()=> {

        if (confirm("정말로 삭제하시겠습니까?")){
            const deleteApi = await boardDelete({ boardId: id });
            if (deleteApi.code === "OK"){
                alert("삭제되었습니다.");
                router.push("/board/list");
            }else{
                alert(deleteApi.message);
            }
        }
    }

    const addReply = async (parentCommentId: string) => {
        const replyContent = replyInputs[parentCommentId] || "";
        if (!replyContent.trim()) {
            alert("대댓글을 입력해주세요");
            return;
        }

        const obj = {
            boardId: id,
            parentCommentId: parentCommentId,
            commentContent: replyContent
        } as SaveCommentRequest;

        const response = await saveComment(obj);
        if (response.code === "OK"){
            // 댓글 목록 다시 가져오기
            getCommentAPI();
            // 해당 대댓글 입력 값 초기화
            setReplyInputs((prev) => ({ ...prev, [parentCommentId]: '' }));
        } else {
            alert(response.message);
        }
    }

    const updateBoard = () => {
        router.push(`/board/${id}/update`);
    }

    const commentUpdateLikes = async (commentId: string, isLike: boolean) => {
        const requestObj: BoardLikedRequest = {
            boardId: commentId,
            recommendationType: isLike ? "LIKE" : "DISLIKE",
        };
    }

    return (
        <>
            <main>
                <div className="post-container" id="post">
                    <h1>{board.title}</h1>
                    {board.mine &&
                        <div className="post-actions">
                            <button className="edit-btn" onClick={updateBoard}>수정</button>
                            <button className="delete-btn" onClick={boardDeleteApi}>삭제</button>
                        </div>
                    }
                    <div className="post-category">카테고리: {board.categoryName}</div>
                    <div className="post-meta">작성자: {board.nickname} | {board.registeredDate}</div>
                    <div className="post-content">
                        {board.content}
                    </div>
                    <div className="like-dislike" data-id="post" data-type="post">
                        <button className="like-btn" onClick={() => boardUpdateLikes(board.id, true)}>👍</button>
                        <span className="like-count">{board.likedCnt}</span>
                        <button className="dislike-btn" onClick={() => boardUpdateLikes(board.id, false)}>👎</button>
                    </div>
                </div>

                <div className={"comments-section"}>
                    <h2>댓글</h2>
                    {comments.map(comment => (
                        <div className="comment" id={`comment-${comment.commentId}`} key={comment.commentId.toString()}>
                            <div className="comment-author">{comment.nickname}</div>
                            <div className="comment-meta">{comment.registered}</div>
                            <div className="comment-content">
                                {comment.commentContent}
                            </div>
                            <div className="like-dislike" data-id="comment-1" data-type="comment">
                                <button className="like-btn" onClick={()=>{commentUpdateLikes(comment.commentId, true)}}>👍</button>
                                <span className="like-count">{comment.likedCnt}</span>
                                <button className="dislike-btn" onClick={()=>{commentUpdateLikes(comment.commentId, false)}}>👎</button>
                            </div>
                            {comment.mine &&
                                <div className="post-actions">
                                    <button className="edit-btn">수정</button>
                                    <button className="delete-btn">삭제</button>
                                </div>
                            }
                            <button className="comment-actions" onClick={() => toggleReplies(comment.commentId.toString())} >답글</button>
                            {openReplies[comment.commentId.toString()] && (
                                <div className="replies">
                                    {/* 대댓글 목록 */}
                                    {comment.childComments && comment.childComments.map((reply) => (
                                        <div className="reply" id={`reply-${reply.commentId}`} key={reply.commentId}>
                                            <div className="comment-author">{reply.nickname}</div>
                                            <div className="comment-meta">{reply.registered}</div>
                                            <div className="comment-content">{reply.commentContent}</div>
                                            {reply.mine &&
                                                <div className="post-actions">
                                                    <button className="edit-btn">수정</button>
                                                    <button className="delete-btn">삭제</button>
                                                </div>
                                            }
                                            <div className="like-dislike" data-id={`reply-${reply.commentId}`} data-type="comment">
                                                <button className="like-btn" onClick={()=>{commentUpdateLikes(reply.commentId, true)}}>👍</button>
                                                <span className="like-count">{reply.likedCnt}</span>
                                                <button className="dislike-btn" onClick={()=>{commentUpdateLikes(reply.commentId, false)}}>👎</button>
                                            </div>
                                        </div>
                                    ))}
                                    {/* 대댓글 입력 폼 */}
                                    <div className="reply-form">
                                        <input
                                            type="text"
                                            placeholder="대댓글 입력"
                                            value={replyInputs[comment.commentId] || ''}
                                            onChange={(e) =>
                                                setReplyInputs((prev) => ({ ...prev, [comment.commentId]: e.target.value }))
                                            }
                                        />
                                        <button type="button" onClick={() => addReply(comment.commentId)}>등록</button>
                                    </div>
                                </div>
                            )}
                        </div>
                    ))}
                    <div className="comment-input">
                        <input type="text" placeholder="댓글을 입력하세요" onChange={(e)=>{setNewComment(e.target.value)}}/>
                        <button type="button" onClick={addComment}>등록</button>
                    </div>
                </div>

            </main>
        </>

    );
}
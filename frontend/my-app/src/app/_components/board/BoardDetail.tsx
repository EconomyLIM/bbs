"use client"

import {useEffect, useState} from "react";
import {Board, BoardLikedRequest} from "@/app/_type/board/BoardRequestResponse";
import {boardDelete, boardLiked, getBoardDetail} from "@service/boardService";
import {
    CommentDeleteRequest,
    CommentLikedRequest,
    Comments,
    SaveCommentRequest, UpdateCommentRequest
} from "@/app/_type/comment/CommentRequestReponse";
import {commentLiked, deleteComment, findCommentByBoard, saveComment, updateComment} from "@service/CommentService";
import {useRouter} from "next/navigation";
import DOMPurify from "dompurify";

interface BoardDetailProps {
    id: string;
}

export function BoardDetail({ id }: BoardDetailProps) {
    const [board, setBoard] = useState<Board>();
    const [comments, setComments] = useState<Comments[]>([]);
    const [newComment, setNewComment] = useState<string>('');
    const [openReplies, setOpenReplies] = useState<Record<string, boolean>>({});
    const [replyInputs, setReplyInputs] = useState<Record<string, string>>({});
    const [editingCommentId, setEditingCommentId] = useState<string | null>(null);
    const [editingContent, setEditingContent] = useState<{[key: string]: string}>({});
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

    /**
     * 댓글 관련
     */
    const addComment = async () => {
        if (newComment === null || newComment === ''){
            alert("댓글을 입력해주세요.");
            return;
        }

        const obj= {
            boardId: id,
            memberEmail: "test@test.com",
            commentId: "", // 신규 등록 시 빈값 혹은 없는 형태로
            commentContent: newComment,
        } as SaveCommentRequest;

        const getComment = await saveComment(obj);
        if (getComment.code === "OK"){

            getCommentAPI().then(()=>{
                setNewComment('');
            });
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
            getCommentAPI().finally(()=>{
                // 해당 대댓글 입력 값 초기화
                setReplyInputs((prev) => ({ ...prev, [parentCommentId]: '' }));
            });
        } else {
            alert(response.message);
        }
    }

    const updateBoard = () => {
        router.push(`/board/${id}/update`);
    }

    const commentUpdateLikes = async (commentId: string, isLike: boolean) => {
        const requestObj: CommentLikedRequest = {
            commentId: commentId,
            recommendationType: isLike ? "LIKE" : "DISLIKE",
        };

        const likedCommentResponse = await commentLiked(requestObj);
        if (likedCommentResponse.code === "OK"){
            alert(isLike ? "추천되었습니다" : "비추천되었습니다");
            getCommentAPI();
        }else{
            alert(likedCommentResponse.message);
        }
    }

    const callDeleteComment = async (commentId: string) =>{

        if(!confirm("정말로 삭제하시겠습니까?")){
            return;
        }

        const requestObj = {
            commentId: commentId
        } as CommentDeleteRequest;

        const deleteCommentResponse = await deleteComment(requestObj);
        if (deleteCommentResponse.code === "OK"){
            alert("삭제되었습니다.");
            getCommentAPI();
        }else {
            alert(deleteCommentResponse.message);
        }
    }

    const startEditing = (commentId: string, currentContent: string, status: string) => {
        if (status === "DELETED"){
            alert("삭제된 댓글을 수정이 불가합니다.");
            return;
        }
        setEditingCommentId(commentId);
        setEditingContent((prev) => ({ ...prev, [commentId]: currentContent }));
    };

    const cancelEditing = () => {
        setEditingCommentId(null);
    };

    const saveEditedComment = async (commentId: string, status: string) => {
        console.log(status);
        if (status === "DELETED"){
            alert("삭제된 댓글을 수정이 불가합니다.");
            return;
        }

        const newContent = editingContent[commentId];
        if (!newContent || newContent.trim() === '') {
            alert("댓글 내용을 입력해주세요.");
            return;
        }

        // 수정 API 호출용 요청 객체 생성
        const obj = {
            commentId: commentId,
            updatedContent: newContent
        } as UpdateCommentRequest; // 실제 타입에 맞춰 수정

        const response = await updateComment(obj); // 실제로 수정하는 API 함수
        if (response.code === "OK") {
            alert("수정되었습니다.");
            getCommentAPI().finally(()=>{
                setEditingCommentId(null);
            }); // 목록 다시 불러오기
        } else {
            alert(response.message);
        }
    };

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
                    <div
                        className="post-content"
                        dangerouslySetInnerHTML={{__html: DOMPurify.sanitize(board.content)}}
                    />
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

                            {/* 수정 모드인지 확인 */}
                            {editingCommentId === comment.commentId ? (
                                // 수정 모드일 때: input + 저장/취소 버튼
                                <div className="comment-edit-form">
                                    <input
                                        type="text"
                                        value={editingContent[comment.commentId] || ''}
                                        onChange={(e) =>
                                            setEditingContent((prev) => ({
                                                ...prev,
                                                [comment.commentId]: e.target.value
                                            }))
                                        }
                                    />
                                    <button onClick={() => saveEditedComment(comment.commentId, comment.status)}>저장</button>
                                    <button onClick={cancelEditing}>취소</button>
                                </div>
                            ) : (
                                // 평상시 모드일 때: 댓글 내용 표시
                                <div className="comment-content">
                                    {comment.commentContent}
                                </div>
                            )}

                            <div className="like-dislike" data-id="comment-1" data-type="comment">
                                <button className="like-btn" onClick={() => {
                                    commentUpdateLikes(comment.commentId, true)
                                }}>👍
                                </button>
                                <span className="like-count">{comment.likedCnt}</span>
                                <button className="dislike-btn" onClick={() => {
                                    commentUpdateLikes(comment.commentId, false)
                                }}>👎
                                </button>
                            </div>

                            {comment.mine && (
                                <div className="post-actions">
                                    {/* 수정 모드가 아닐 때만 수정 버튼 표시 */}
                                    {editingCommentId !== comment.commentId && (
                                        <button className="edit-btn"
                                                onClick={() => startEditing(comment.commentId, comment.commentContent, comment.status)}>수정</button>
                                    )}
                                    <button className="delete-btn" onClick={() => {
                                        callDeleteComment(comment.commentId)
                                    }}>삭제
                                    </button>
                                </div>
                            )}

                            <button className="comment-actions"
                                    onClick={() => toggleReplies(comment.commentId.toString())}>답글
                                : {comment.childComments && comment.childComments.length}개
                            </button>
                            {openReplies[comment.commentId.toString()] && (
                                <div className="replies">
                                    {/* 대댓글 목록 */}
                                    {comment.childComments && comment.childComments.map((reply) => (
                                        <div className="reply" id={`reply-${reply.commentId}`} key={reply.commentId}>
                                            <div className="comment-author">{reply.nickname}</div>
                                            <div className="comment-meta">{reply.registered}</div>

                                            {/* 대댓글도 수정 기능을 추가하려면 같은 로직 적용 가능 */}
                                            {/*<div className="comment-content">{reply.commentContent}</div>*/}
                                            {/* 수정 모드인지 확인 */}
                                            {editingCommentId === reply.commentId ? (
                                                // 수정 모드일 때: input + 저장/취소 버튼
                                                <div className="comment-edit-form">
                                                    <input
                                                        type="text"
                                                        value={editingContent[reply.commentId] || ''}
                                                        onChange={(e) =>
                                                            setEditingContent((prev) => ({
                                                                ...prev,
                                                                [reply.commentId]: e.target.value
                                                            }))
                                                        }
                                                    />
                                                    <button onClick={() => saveEditedComment(reply.commentId, reply.status)}>저장</button>
                                                    <button onClick={cancelEditing}>취소</button>
                                                </div>
                                            ) : (
                                                // 평상시 모드일 때: 댓글 내용 표시
                                                <div className="comment-content">
                                                    {reply.commentContent}
                                                </div>
                                            )}
                                            {(reply.mine && reply.status !== "DELETED" )&& (
                                                <div className="post-actions">
                                                    {/* 대댓글 수정 기능 적용 가능 */}
                                                    {editingCommentId !== reply.commentId && (
                                                        <button className="edit-btn"
                                                                onClick={() => startEditing(reply.commentId, reply.commentContent, reply.status)}>수정</button>
                                                    )}
                                                    <button className="delete-btn" onClick={() => {
                                                        callDeleteComment(reply.commentId)
                                                    }}>삭제
                                                    </button>
                                                </div>
                                            )}
                                            <div className="like-dislike" data-id={`reply-${reply.commentId}`}
                                                 data-type="comment">
                                                <button className="like-btn" onClick={() => {
                                                    commentUpdateLikes(reply.commentId, true)
                                                }}>👍
                                                </button>
                                                <span className="like-count">{reply.likedCnt}</span>
                                                <button className="dislike-btn" onClick={() => {
                                                    commentUpdateLikes(reply.commentId, false)
                                                }}>👎
                                                </button>
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
                                                setReplyInputs((prev) => ({
                                                    ...prev,
                                                    [comment.commentId]: e.target.value
                                                }))
                                            }
                                        />
                                        <button type="button" onClick={() => addReply(comment.commentId)}>등록</button>
                                    </div>
                                </div>
                            )}
                        </div>
                    ))}
                    <div className="comment-input">
                        <input type="text" placeholder="댓글을 입력하세요" value={newComment} onChange={(e) => {
                            setNewComment(e.target.value)
                        }}/>
                        <button type="button" onClick={addComment}>등록</button>
                    </div>
                </div>

            </main>
        </>

    );
}
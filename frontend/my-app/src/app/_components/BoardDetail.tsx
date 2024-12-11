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
            <div className="bg-white shadow rounded-lg overflow-hidden max-w-4xl mx-auto">
                <div className="p-8">
                    <h2 className="text-3xl font-bold mb-4">Title: {board.title}</h2>
                    <p className="text-xl text-gray-600 mb-6">Author: {board.memberEmail}</p>
                    <div className="border-t border-b py-6 mb-6">
                        <p className="text-xl text-gray-800 whitespace-pre-wrap">content : {board.content}</p>
                    </div>
                    <div>
                        좋아요: {board.likedCnt}
                    </div>
                </div>
            </div>
            <div>
                <textarea
                    placeholder={"작성해주세요."}
                    value={newComment}
                    onChange={(e) => setNewComment(e.target.value)}
                />
                <button onClick={addComent}>
                    {replyTo ? '답글 작성' : '댓글 작성'}
                </button>
                {replyTo && (
                    <button onClick={() => setReplyTo(null)}>취소</button>
                )}

                <div>{renderComments(comments)}</div>
            </div>

            <button onClick={() => updateLikes(board.id, true)}>👍 추천</button>
            {/*<span>{comment.likes}</span>*/}
            <button onClick={() => updateLikes(board.id, false)}>👎 비추천</button>
            {/*<span>{comment.dislikes}</span>*/}
        </>

    );
}
import {BoardDetail} from "@/app/_components/board/BoardDetail";
import React from 'react';

interface BoardPageProps {
    params: {
        id: string;
    };
}

export default async function BoardPage({ params }: BoardPageProps) {
    const { id } = await params; // 비동기로 params 언래핑

    return (
        <>
            <BoardDetail id={id} />
        </>
    );
}



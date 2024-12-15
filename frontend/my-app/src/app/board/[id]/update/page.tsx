import {BoardPageProps} from "@/app/board/[id]/page";
import {BoardUpdate} from "@/app/_components/board/BoardUpdate";


export default async function BoardPage({ params }: BoardPageProps){
    const { id } = await params; // 비동기로 params 언래핑

    return (
        <>
            <BoardUpdate id={id}></BoardUpdate>
        </>
    )
}
"use client"

import {useEffect, useState} from "react";
import Link from "next/link";
import Image from "next/image";
import {Board, BoardRequest} from "@/app/_type/CommonResponse";
import {requestApiFetch} from "@/lib/requestApiFetch";


export default function Home() {
    const [boards, setBoards] = useState<Board[]>([]);

    const getPayInformation = async (): Promise<BoardRequest> => {
        return await requestApiFetch<BoardRequest>('GET', '/board');
    };


    const aaa = async () =>{
        const payInformation = await getPayInformation();
        setBoards(payInformation.board ?? []);
    }

    useEffect(() => {
       aaa();
    }, []);

    return (
        <div className="space-y-8 max-w-7xl mx-auto">
            <h2 className="text-4xl font-bold mb-8">Bulletin Boards</h2>
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
                {boards.map((board) => (
                    <Link key={board.id} href={`/board/${board.id}`} className="block hover:shadow-lg transition-shadow duration-300">
                        <div className="bg-white rounded-lg overflow-hidden shadow">
                            {board.imageUrl && (
                                <div className="relative h-48">
                                    <Image
                                        src={board.imageUrl}
                                        alt={board.title}
                                        layout="fill"
                                        objectFit="cover"
                                    />
                                </div>
                            )}
                            <div className="p-6">
                                <h3 className="text-2xl font-semibold mb-2">{board.title}</h3>
                                <p className="text-gray-600 mb-4">Author: {board.author}</p>
                                <p className="text-gray-500">Likes: {board.likes}</p>
                            </div>
                        </div>
                    </Link>
                ))}
            </div>
            <div className="mt-12">
                <Link href="/create-post" className="bg-indigo-600 text-white py-4 px-8 rounded-md text-lg hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2 transition-colors duration-300">
                    Create New Post
                </Link>
            </div>
        </div>
    )
}
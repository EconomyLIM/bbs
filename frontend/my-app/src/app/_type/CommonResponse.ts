

export interface ApiResponse {
    code : string,
    message ?: string,
    errorCode ?: string
}

export interface Board {
    id: number;
    title: string;
    author?: string;
    content: string;
    imageUrl?: string;
    likes?: number;
    comments?: Comment[];
}

export interface BoardRequest extends ApiResponse{
    board: Board[];
}
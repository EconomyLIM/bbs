"use client";
import { useEffect, useState } from "react";
import { getBoardRegister } from "@service/boardService";
import { BoardRegisterRequest } from "@/app/_type/board/BoardRequestResponse";
import { useRouter } from "next/navigation";
import { Category } from "@/app/_type/category/CategoryRequestResponse";
import { getCategoryList } from "@service/CategoryService";
import axios from "axios";
import EditorCommon from "@/app/_components/editor/EditorCommon";

const defaultValue = {
    title: "",
    content: "여기에 본문을 입력하거나 편집 기능을 사용할 수 있습니다.",
    memberEmail: "test@test.com",
    categoryId: "",
};

export function BoardRegister() {
    const router = useRouter();
    const [boardRegisterForm, setBoardRegisterForm] =
        useState<BoardRegisterRequest>({
            ...defaultValue,
        });
    const [categoryList, setCategoryList] = useState<Category[]>([]);
    const [uploadedImages, setUploadedImages] = useState<string[]>([]); // 업로드된 이미지 URL 리스트

    useEffect(() => {
        getCategoryListApi();

        // 페이지 이탈 시 업로드된 이미지 삭제
        return () => {
            deleteUnsubmittedImages();
        };
    }, []);

    const handle = (
        e:
            | React.ChangeEvent<HTMLInputElement>
            | React.ChangeEvent<HTMLTextAreaElement>
            | React.ChangeEvent<HTMLSelectElement>
    ) => {
        const { name, value } = e.target;
        setBoardRegisterForm((prevForm) => ({
            ...prevForm,
            [name]: value,
        }));
    };

    const handleEditorChange = (html: string) => {
        setBoardRegisterForm((prev) => ({
            ...prev,
            content: html,
        }));
    };

    const boardRegisterAPI = async () => {
        const boardRegister = await getBoardRegister(boardRegisterForm);

        if (!boardRegisterForm.categoryId){
            alert("카테고리를 선택해주세요.");
            return;
        }

        if (!boardRegisterForm.title){
            alert("제목을 입력해주세요.");
            return;
        }

        if (!boardRegisterForm.content){
            alert("본문을 입력해주세요.");
            return;
        }


        if (boardRegister.code === "OK") {
            router.push(`/board/${boardRegister.boardId}`);
        }
    };

    const getCategoryListApi = async () => {
        const categoryListResponse = await getCategoryList();

        if (categoryListResponse.code === "OK") {
            setCategoryList(categoryListResponse.categories);
        }
    };

    const handleImageUpload = async (event: React.ChangeEvent<HTMLInputElement>) => {
        const file = event.target.files?.[0];
        if (file) {
            const formData = new FormData();
            formData.append("file", file);

            try {
                // 이미지 업로드 API 호출
                const response = await axios.post("/api/upload", formData, {
                    headers: { "Content-Type": "multipart/form-data" },
                });

                const imageUrl = response.data.url; // 백엔드에서 반환된 이미지 URL

                setUploadedImages((prev) => [...prev, imageUrl]);

                // 본문에 삽입
                setBoardRegisterForm((prevForm) => ({
                    ...prevForm,
                    content: prevForm.content + `\n<img src="${imageUrl}" alt="uploaded image" />\n`,
                }));
            } catch (error) {
                alert("이미지 업로드에 실패했습니다." + error);
            }
        }
    };

    const deleteUnsubmittedImages = async () => {
        try {
            // 업로드된 이미지 삭제 API 호출
            await axios.post("/api/delete-temp-images", { images: uploadedImages });
        } catch (error) {
            console.error("임시 이미지 삭제 실패:", error);
        }
    };

    return (
        <>
            <main>
                <div className="editor-container">
                    <div className="category-select">
                        <label htmlFor="category">카테고리:</label>
                        <select
                            id="categoryId"
                            name={"categoryId"}
                            onChange={(e) => {
                                handle(e);
                            }}
                        >
                            <option value="">-- 카테고리 선택 --</option>
                            {categoryList.map((category) => (
                                <option key={category.categoryName} value={category.categoryId}>
                                    {category.categoryName}
                                </option>
                            ))}
                        </select>
                    </div>
                    <div className="post-title">
                        <input
                            type="text"
                            value={boardRegisterForm.title}
                            id="title"
                            name={"title"}
                            onChange={handle}
                            placeholder="글 제목을 입력하세요"
                        />
                    </div>

                    <div className="toolbar">
                        <button type="button" data-cmd="bold">
                            <b>B</b>
                        </button>
                        <button type="button" data-cmd="italic">
                            <i>I</i>
                        </button>
                        <button type="button" data-cmd="underline">
                            <u>U</u>
                        </button>
                        <button
                            type="button"
                            onClick={() => document.getElementById("imageUpload")?.click()}
                        >
                            이미지 선택
                        </button>
                        <input
                            type="file"
                            id="imageUpload"
                            accept="image/*"
                            style={{ display: "none" }}
                            onChange={handleImageUpload}
                        />
                    </div>

                    <div className="editor">
            {/*<textarea*/}
            {/*    value={boardRegisterForm.content}*/}
            {/*    name={"content"}*/}
            {/*    id={"content"}*/}
            {/*    onChange={handle}*/}
            {/*    style={{ width: "100%", height: "500px" }}*/}
            {/*/>*/}
                        {/* Toast UI Editor */}
                        <EditorCommon
                            initialValue="여기에 본문을 작성하거나 이미지를 업로드해보세요."
                            onChangeContent={handleEditorChange}
                        />
                    </div>

                    <div className="submit-area">
                        <button type="button">취소</button>
                        <button type="button" onClick={boardRegisterAPI}>
                            등록
                        </button>
                    </div>
                </div>
                {/*<EditorCommon/>*/}
            </main>
        </>
    );
}

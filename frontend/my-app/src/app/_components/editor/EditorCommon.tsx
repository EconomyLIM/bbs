"use client";
import "@toast-ui/editor/dist/toastui-editor.css";
import { Editor } from "@toast-ui/react-editor";
import { EditorProps } from "@toast-ui/react-editor";
import { useRef } from "react";
import axios from "axios";

/**
 * 원하는 옵션을 props로 넘길 수도 있지만,
 * 여기서는 간단하게 이미지 업로드까지 지원하는 기본 예시를 작성합니다.
 */

interface EditorCommonProps {
    initialValue?: string;       // 초기에 표시될 내용
    onChangeContent?: (html: string) => void;
}

export default function EditorCommon({
                                         initialValue = "여기에 내용을 작성하세요",
                                         onChangeContent,
                                     }: EditorCommonProps) {
    const editorRef = useRef<Editor>(null);

    // Toast UI Editor가 이미지를 삽입할 때마다 불리는 hook
    const onUploadImage = async (blob: File, callback: (url: string, altText: string) => void) => {
        try {
            const formData = new FormData();
            formData.append("file", blob);

            // 백엔드에 파일 업로드
            const res = await axios.post("/api/upload", formData, {
                headers: { "Content-Type": "multipart/form-data" },
            });

            // 서버에서 돌려준 이미지 URL
            const imageUrl = res.data.url;
            console.log("imageUrl"+ imageUrl);
            // callback으로 Toast UI Editor 내부에 이미지를 삽입
            callback(imageUrl, "uploaded image");
        } catch (error) {
            console.error("Image upload failed:", error);
        }
    };

    const editorOptions: EditorProps = {
        initialValue,
        previewStyle: "vertical",
        height: "600px",
        initialEditType: "wysiwyg",
        useCommandShortcut: true,
        hooks: {
            // 이미지 업로드 훅
            addImageBlobHook: onUploadImage,
        },
        // onChange 이벤트 - 에디터 내용이 바뀔 때마다 상위 컴포넌트에 알림
        onChange: () => {
            if (onChangeContent && editorRef.current) {
                const instance = editorRef.current.getInstance();
                // getHTML() or getMarkdown() 선택 가능
                const html = instance.getHTML();
                onChangeContent(html);
            }
        },
    };

    return <Editor ref={editorRef} {...editorOptions} />;
}

import {ApiResponse} from "@/app/_type/CommonResponse";


export interface Category{
    categoryId: string,
    categoryName: string,
    categoryDepths: number,
    subCategories: Category[]
}

export interface CategoryListResponse extends ApiResponse{
    categories: Category[]
}
import {requestApiFetch} from "@/lib/requestApiFetch";
import {CategoryListResponse} from "@/app/_type/category/CategoryRequestResponse";


export const getCategoryList = async () :Promise<CategoryListResponse> => {
    return await requestApiFetch<CategoryListResponse>('GET', `/category/list`);
}
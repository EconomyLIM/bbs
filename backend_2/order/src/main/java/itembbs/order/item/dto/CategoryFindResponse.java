package itembbs.order.item.dto;

import itembbs.order.common.dto.CommonResponse;
import itembbs.order.item.domain.ItemCategory;
import lombok.Getter;

/**
 * date           : 2025-01-10
 * created by     : 임경재
 * description    :
 */
@Getter
public class CategoryFindResponse extends CommonResponse {

    private CategoryDTO category;

    private CategoryFindResponse() {
    }

    private CategoryFindResponse(final CategoryDTO category) {
        this.category = category;
    }

    public static CategoryFindResponse of(final ItemCategory findCategory){
        CategoryDTO categoryDTO = new CategoryDTO(findCategory);
        return new CategoryFindResponse(categoryDTO);
    }
}

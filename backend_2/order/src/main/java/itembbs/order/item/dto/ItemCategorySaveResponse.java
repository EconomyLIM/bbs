package itembbs.order.item.dto;

import itembbs.order.common.dto.CommonResponse;
import lombok.Getter;

/**
 * date           : 2025-01-10
 * created by     : 임경재
 * description    :
 */
@Getter
public class ItemCategorySaveResponse extends CommonResponse {

    private Long categoryId;

    private ItemCategorySaveResponse() {
    }

    private ItemCategorySaveResponse(final Long categoryId) {
        this.categoryId = categoryId;
    }

    public static ItemCategorySaveResponse of (final Long categoryId){
        return new ItemCategorySaveResponse(categoryId);
    }
}

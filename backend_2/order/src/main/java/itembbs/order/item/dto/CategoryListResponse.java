package itembbs.order.item.dto;

import itembbs.order.common.dto.CommonResponse;
import itembbs.order.item.domain.ItemCategory;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * date           : 2025-01-10
 * created by     : 임경재
 * description    :
 */
@Getter
public class CategoryListResponse extends CommonResponse {

    private List<CategoryDTO> list;

    private CategoryListResponse() {
    }

    private CategoryListResponse(final List<CategoryDTO> list) {
        this.list = list;
    }

    public static CategoryListResponse of(final List<ItemCategory> list) {
        return new CategoryListResponse(list
                .stream()
                .map(CategoryDTO::new)
                .collect(Collectors.toList())
        );
    }
}

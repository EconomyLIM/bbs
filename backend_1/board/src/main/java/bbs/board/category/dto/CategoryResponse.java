package bbs.board.category.dto;

import bbs.board.dto.common.BasicResponse;
import lombok.Getter;

import java.util.List;

/**
 * date           : 2024-12-17
 * created by     : 임경재
 * description    :
 */
@Getter
public class CategoryResponse extends BasicResponse {
    private List<CategoryDTO> categories;

    private CategoryResponse() {
    }

    private CategoryResponse(final List<CategoryDTO> categories) {
        this.categories = categories;
    }

    public static CategoryResponse of(final List<CategoryDTO> categories) {
        return new CategoryResponse(categories);
    }
}

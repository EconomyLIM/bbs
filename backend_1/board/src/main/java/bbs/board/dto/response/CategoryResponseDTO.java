package bbs.board.dto.response;

import bbs.board.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * date           : 2024-11-28
 * created by     : 임경재
 * description    :
 */
@NoArgsConstructor @AllArgsConstructor
@Getter
public class CategoryResponseDTO {

    private String categoryName;
    private Integer categoryDepths;
    private List<CategoryResponseDTO> subCategories = new ArrayList<>();

    public CategoryResponseDTO(Category category) {
        this.categoryName = category.getCategoryName();
        this.categoryDepths = category.getCategoryDepths();
        this.subCategories = category.getSubCategories().stream().map(CategoryResponseDTO::new).toList();
    }
}

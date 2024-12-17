package bbs.board.category.dto;

import bbs.board.category.entity.Category;
import lombok.AllArgsConstructor;
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
public class CategoryDTO {

    private Long categoryId;
    private String categoryName;
    private Integer categoryDepths;
    private List<CategoryDTO> subCategories = new ArrayList<>();

    public CategoryDTO(Category category) {
        this.categoryId = category.getId();
        this.categoryName = category.getCategoryName();
        this.categoryDepths = category.getCategoryDepths();
        this.subCategories = category.getSubCategories().stream().map(CategoryDTO::new).toList();
    }
}

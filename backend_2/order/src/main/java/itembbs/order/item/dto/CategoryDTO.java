package itembbs.order.item.dto;

import itembbs.order.item.domain.ItemCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * date           : 2025-01-10
 * created by     : 임경재
 * description    :
 */
@Data @NoArgsConstructor @AllArgsConstructor
public class CategoryDTO {

    private Long id;
    private int depth;
    private String name;
    private Long parentId;
    private List<CategoryDTO> children;

    public CategoryDTO(final Long parentId) {
        this.parentId = parentId;
    }

    public CategoryDTO(final ItemCategory findCategory) {
        this.id = findCategory.getId();
        this.depth = findCategory.getDepth();
        this.name = findCategory.getName();
        if (findCategory.getChildren() != null && !findCategory.getChildren().isEmpty()) {
            this.children = findCategory.getChildren()
                    .stream()
                    .map(CategoryDTO::new)
                    .collect(Collectors.toList());
        }
    }
}

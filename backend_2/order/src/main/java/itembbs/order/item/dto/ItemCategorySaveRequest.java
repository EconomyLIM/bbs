package itembbs.order.item.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * date           : 2025-01-10
 * created by     : 임경재
 * description    :
 */
@Data @NoArgsConstructor @AllArgsConstructor
public class ItemCategorySaveRequest {

    @Min(value = 1, message = "Depth는 1부터 가능합니다.")
    private int depth;

    private Long parentId;

    @NotNull @NotEmpty
    private String name;
}

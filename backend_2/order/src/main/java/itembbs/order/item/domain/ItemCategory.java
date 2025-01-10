package itembbs.order.item.domain;

import itembbs.order.item.dto.ItemCategorySaveRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * date           : 2025-01-10
 * created by     : 임경재
 * description    :
 */
@Entity @Getter
public class ItemCategory {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_category_id")
    private Long id;
    private int depth;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @Setter
    private ItemCategory parent;

    @OneToMany(mappedBy = "parent")
    private List<ItemCategory> children = new ArrayList<>();

    protected ItemCategory() {
    }

    public ItemCategory(final int depth, final String name, final ItemCategory parent) {
        this.depth = depth;
        this.name = name;
        this.parent = parent;
    }

    public ItemCategory(final ItemCategorySaveRequest request, final ItemCategory parentCategory) {
        this.depth = request.getDepth();
        this.name = request.getName();
        if (parentCategory != null) {
            addChildForParent(parentCategory);
        }
    }

    public void addChildForParent(final ItemCategory parent) {
        this.parent = parent;
        parent.getChildren().add(this);
    }
}

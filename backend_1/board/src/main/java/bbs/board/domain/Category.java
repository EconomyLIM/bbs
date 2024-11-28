package bbs.board.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

/**
 * date           : 2024-11-28
 * created by     : 임경재
 * description    :
 */
@Entity
@Getter @AllArgsConstructor
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private Long id;

    private String categoryName;
    public Integer categoryDepths;

    @OneToMany(mappedBy = "category")
    private List<Board> boards = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
//    @BatchSize(size = 15)
    private List<Category> subCategories = new ArrayList<>();

    public void addParentCategoryInChild (Category category) {
        parent = category;
        parent.getSubCategories().add(this);
    }

    // Constructors
    protected Category() {

    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public Category(String categoryName, Integer categoryDepths) {
        this.categoryName = categoryName;
        this.categoryDepths = categoryDepths;
    }
}

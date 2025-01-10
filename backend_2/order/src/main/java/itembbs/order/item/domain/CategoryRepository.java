package itembbs.order.item.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * date           : 2025-01-10
 * created by     : 임경재
 * description    :
 */
public interface CategoryRepository extends JpaRepository<ItemCategory, Long> {

    List<ItemCategory> findByDepth(int depth);

    @Query("select c from ItemCategory c left join fetch c.children where c.parent = :parent")
    List<ItemCategory> findByParent(ItemCategory parent);
}

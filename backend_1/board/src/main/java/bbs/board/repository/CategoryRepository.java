package bbs.board.repository;

import bbs.board.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * date           : 2024-11-28
 * created by     : 임경재
 * description    :
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select distinct c from Category c join fetch c.subCategories")
    public List<Category> findAllBy();
}

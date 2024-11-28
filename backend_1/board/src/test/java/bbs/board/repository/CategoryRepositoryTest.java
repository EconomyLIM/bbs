package bbs.board.repository;

import bbs.board.domain.Category;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * date           : 2024-11-28
 * created by     : 임경재
 * description    :
 */
@SpringBootTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("카테고리 등록을 성공해야한다.")
    @Transactional
    @Rollback(false)
    public void CategoryRegister() throws Exception{
        // given
        Category topCategory1 = new Category("TopDepth1", 1);
        Category topCategory2 = new Category("TopDepth2", 1);

        categoryRepository.save(topCategory1);
        categoryRepository.save(topCategory2);

        Category subCategory1 = new Category("subDepth1", 2);
        subCategory1.addParentCategoryInChild(topCategory1);

        Category subCategory2 = new Category("subDepth1", 2);
        subCategory2.addParentCategoryInChild(topCategory2);
        categoryRepository.save(subCategory1);
        categoryRepository.save(subCategory2);

        em.flush();
        em.clear();

        // when

        Category findCategory1 = categoryRepository.findById(subCategory1.getId()).get();
        Category findCategory2 = categoryRepository.findById(subCategory2.getId()).get();

        // then
        Assertions.assertThat(findCategory1.getParent().getId()).isEqualTo(1L);
        Assertions.assertThat(findCategory2.getParent().getId()).isEqualTo(2L);
    }

    @Test
    @Transactional
    public void findAllMenu() throws Exception{
        // given
        Category topCategory1 = new Category("TopDepth1", 1);
        Category topCategory2 = new Category("TopDepth2", 1);

        categoryRepository.save(topCategory1);
        categoryRepository.save(topCategory2);

        Category subCategory1 = new Category("subDepth1", 2);
        subCategory1.addParentCategoryInChild(topCategory1);

        Category subCategory2 = new Category("subDepth2", 2);
        subCategory2.addParentCategoryInChild(topCategory2);
        categoryRepository.save(subCategory1);
        categoryRepository.save(subCategory2);

        em.flush();
        em.clear();
        // when
        List<Category> allBy = categoryRepository.findAllBy();
        for (Category category : allBy) {
            System.out.println(category.getCategoryName());
            List<Category> subCategories = category.getSubCategories();
            System.out.println("=");
            for (Category subCategory : subCategories) {
                System.out.println("subCategory.getCategoryName() = " + subCategory.getCategoryName());
            }
            System.out.println("================");
        }

        // then
    }
}
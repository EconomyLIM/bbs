package itembbs.order.item.domain;

import itembbs.order.item.dto.CategoryDTO;
import itembbs.order.item.dto.CategoryListResponse;
import itembbs.order.item.dto.ItemCategorySaveRequest;
import itembbs.order.item.dto.ItemCategorySaveResponse;
import itembbs.order.item.service.ItemCategoryService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * date           : 2025-01-10
 * created by     : 임경재
 * description    :
 */
@SpringBootTest
class ItemCategoryServiceTest {

    @Autowired
    ItemCategoryService itemCategoryService;

    @Autowired
    EntityManager em;


    @Test
    @DisplayName("1뎁스 카테고리 저장에 성공해야한다.")
    public void saveCategory() throws Exception{
        // given
        ItemCategorySaveRequest request = new ItemCategorySaveRequest(1, null, "1뎁스 카테고리");

        // when
        ItemCategorySaveResponse savedItemCategory = itemCategoryService.saveItemCategory(request);

        // then
        assertThat(savedItemCategory.getCategoryId()).isEqualTo(1L);
    }

    @Test
    @Transactional
    @Rollback(false)
    @DisplayName("2뎁스 카테고리 저장에 성공해야 한다.")
    public void saveSubCategory() throws Exception{
        // given
        ItemCategorySaveRequest request = new ItemCategorySaveRequest(1, null, "1뎁스 카테고리");
        ItemCategorySaveResponse savedItemCategory = itemCategoryService.saveItemCategory(request);

        em.flush();
        em.clear();
        // when
        ItemCategorySaveRequest subRequest = new ItemCategorySaveRequest(2, 1L, "2뎁스 카테고리");
        ItemCategorySaveResponse subSavedItemCategory = itemCategoryService.saveItemCategory(subRequest);

        // then
        assertThat(subSavedItemCategory.getCategoryId()).isEqualTo(2L);
    }

    @Test
    @Transactional
    @Rollback(false)
    @DisplayName("카테고리를 뎁스로 조회가 가능해야 한다.")
    public void findCategoryByDepth() throws Exception{
        // given
        ItemCategorySaveRequest request1 = new ItemCategorySaveRequest(1, null, "1뎁스 카테고리1");
        ItemCategorySaveRequest request2 = new ItemCategorySaveRequest(1, null, "1뎁스 카테고리2");
        ItemCategorySaveRequest request3 = new ItemCategorySaveRequest(1, null, "1뎁스 카테고리3");

        itemCategoryService.saveItemCategory(request1);
        itemCategoryService.saveItemCategory(request2);
        itemCategoryService.saveItemCategory(request3);

        // when
        CategoryListResponse categoryByDepth = itemCategoryService.findCategoryByDepth(1);

        // then
        assertThat(categoryByDepth.getList().size()).isEqualTo(3);
    }

    @Test
    @Transactional
    @Rollback(false)
    @DisplayName("부모 카테고리로 자식 카테고리저장에 성공해야한다.")
    public void findCategoryByParent() throws Exception{
        // given
        ItemCategorySaveRequest request = new ItemCategorySaveRequest(1, null, "1뎁스 카테고리");
        ItemCategorySaveResponse savedItemCategory = itemCategoryService.saveItemCategory(request);

        ItemCategorySaveRequest subRequest1 = new ItemCategorySaveRequest(2, 1L, "2뎁스 카테고리1");
        ItemCategorySaveRequest subRequest2 = new ItemCategorySaveRequest(2, 1L, "2뎁스 카테고리2");
        ItemCategorySaveRequest subRequest3 = new ItemCategorySaveRequest(2, 1L, "2뎁스 카테고리3");

        ItemCategorySaveResponse subSavedItemCategory1 = itemCategoryService.saveItemCategory(subRequest1);
        ItemCategorySaveResponse subSavedItemCategory2 = itemCategoryService.saveItemCategory(subRequest2);
        ItemCategorySaveResponse subSavedItemCategory3 = itemCategoryService.saveItemCategory(subRequest3);

        em.flush();
        em.clear();

        System.out.println("======================================");
        // when
        CategoryListResponse categoryByParent = itemCategoryService.findCategoryByParent(new CategoryDTO(savedItemCategory.getCategoryId()));


        // then
        assertThat(categoryByParent.getList().size()).isEqualTo(3);
    }
}
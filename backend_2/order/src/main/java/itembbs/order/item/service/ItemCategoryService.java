package itembbs.order.item.service;

import itembbs.order.item.domain.CategoryRepository;
import itembbs.order.item.domain.ItemCategory;
import itembbs.order.item.dto.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * date           : 2025-01-10
 * created by     : 임경재
 * description    :
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemCategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public ItemCategorySaveResponse saveItemCategory(ItemCategorySaveRequest request) {

        ItemCategory parentCategory = null;
        if (request.getParentId() != null){
            parentCategory = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent category not found"));
        }

        ItemCategory itemCategory = new ItemCategory(request, parentCategory);
        ItemCategory savedCategory = categoryRepository.save(itemCategory);

        return ItemCategorySaveResponse.of(savedCategory.getId());
    }

    public CategoryFindResponse findCategoryById(Long id) {
        ItemCategory findCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category Not Found"));
        return CategoryFindResponse.of(findCategory);
    }

    public CategoryListResponse findCategoryByDepth(int depth){
        List<ItemCategory> findCategories = categoryRepository.findByDepth(depth);
        return CategoryListResponse.of(findCategories);
    }

    public CategoryListResponse findCategoryByParent(CategoryDTO request){

        ItemCategory parentCategory = categoryRepository.findById(request.getParentId())
                .orElseThrow(() -> new RuntimeException("Parent Category Not Found"));

        List<ItemCategory> findCategories = categoryRepository.findByParent(parentCategory);
        return CategoryListResponse.of(findCategories);
    }

}

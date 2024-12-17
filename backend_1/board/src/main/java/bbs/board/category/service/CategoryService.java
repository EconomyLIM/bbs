package bbs.board.category.service;

import bbs.board.category.dto.CategoryResponse;
import bbs.board.category.entity.Category;
import bbs.board.category.dto.CategoryDTO;
import bbs.board.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * date           : 2024-11-28
 * created by     : 임경재
 * description    :
 */
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponse findAll() {
        List<Category> allCategory = categoryRepository.findAllBy();
        List<CategoryDTO> dtos = allCategory
                .stream()
                .map(CategoryDTO::new)
                .toList();
        return CategoryResponse.of(dtos);
    }

    @Transactional
    public void save(Category category) {
        categoryRepository.save(category);
    }
}

package bbs.board.service;

import bbs.board.domain.Category;
import bbs.board.dto.response.CategoryResponseDTO;
import bbs.board.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public List<CategoryResponseDTO> findAll() {
        List<Category> all = categoryRepository.findAllBy();
        List<CategoryResponseDTO> categoryResponseDTOS = new ArrayList<>();
        for (Category category : all) {
            System.out.println("category.getCategoryName() = " + category.getCategoryName());
            System.out.println("category.getCategoryDepths() = " + category.getCategoryDepths());
            System.out.println("category.getSubCategories().getClass() = " + category.getSubCategories().getClass());
            categoryResponseDTOS.add(new CategoryResponseDTO(category));
        }
        return categoryResponseDTOS;
    }
}

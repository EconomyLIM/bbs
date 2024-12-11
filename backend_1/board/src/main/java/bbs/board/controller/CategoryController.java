package bbs.board.controller;

import bbs.board.domain.Category;
import bbs.board.dto.response.CategoryResponseDTO;
import bbs.board.repository.CategoryRepository;
import bbs.board.service.CategoryService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * date           : 2024-11-28
 * created by     : 임경재
 * description    :
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    @GetMapping("/list")
    public List<CategoryResponseDTO> findAllCategories(){

        List<CategoryResponseDTO> all = categoryService.findAll();
        for (CategoryResponseDTO categoryResponseDTO : all) {

        }
        return all;
    }

//    @EventListener(ApplicationReadyEvent.class)
//    @PostConstruct
    public void init(){
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
    }


}

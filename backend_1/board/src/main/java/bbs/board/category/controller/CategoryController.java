package bbs.board.category.controller;

import bbs.board.category.dto.CategoryResponse;
import bbs.board.category.entity.Category;
import bbs.board.category.dto.CategoryDTO;
import bbs.board.category.repository.CategoryRepository;
import bbs.board.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
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


    @GetMapping("/list")
    public ResponseEntity<CategoryResponse> findAllCategories(){

        return ResponseEntity.ok(categoryService.findAll());
    }

    private final CategoryRepository categoryRepository;
//    @EventListener(ApplicationReadyEvent.class)
    public void init(){
        Category topCategory1 = new Category("야구", 1);
        Category topCategory2 = new Category("축구", 1);
        Category topCategory3 = new Category("배드민턴", 1);
        Category topCategory4 = new Category("탁구", 1);

        categoryRepository.save(topCategory1);
        categoryRepository.save(topCategory2);
        categoryRepository.save(topCategory3);
        categoryRepository.save(topCategory4);
    }


}

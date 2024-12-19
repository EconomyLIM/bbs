package bbs.board.category.controller;

import bbs.board.category.dto.CategoryResponse;
import bbs.board.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}

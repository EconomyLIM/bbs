package itembbs.order.item.controller;

import itembbs.order.item.dto.CategoryDTO;
import itembbs.order.item.dto.CategoryFindResponse;
import itembbs.order.item.dto.CategoryListResponse;
import itembbs.order.item.service.ItemCategoryService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * date           : 2025-01-10
 * created by     : 임경재
 * description    :
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item/category")
public class ItemCategoryController {

    private final ItemCategoryService itemCategoryService;

    @GetMapping("/{id}")
    public ResponseEntity<CategoryFindResponse> getCategoryById(@PathVariable Long id) {
        CategoryFindResponse response = itemCategoryService.findCategoryById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public ResponseEntity<CategoryListResponse> getCategoryByDepth(@RequestParam @DefaultValue(value = "0") String depth) {
        CategoryListResponse categoryByDepth = itemCategoryService.findCategoryByDepth(Integer.parseInt(depth));
        return ResponseEntity.ok(categoryByDepth);
    }

    @GetMapping("/parent")
    public ResponseEntity<CategoryListResponse> getCategoryByParent(CategoryDTO request) {
        CategoryListResponse categoryByDepth = itemCategoryService.findCategoryByParent(request);
        return ResponseEntity.ok(categoryByDepth);
    }


}

package itembbs.order.item.controller;

import itembbs.order.item.domain.Item;
import itembbs.order.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * date           : 2025-01-03
 * created by     : 임경재
 * description    :
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/")
    public String index() {
        return "ok";
    }

    @PostMapping("/item")
    public ResponseEntity<Long> save(@RequestBody Item item) {
        return ResponseEntity.ok(itemService.saveItem(item));
    }


}

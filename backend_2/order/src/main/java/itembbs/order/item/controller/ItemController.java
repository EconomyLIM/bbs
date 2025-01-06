package itembbs.order.item.controller;

import itembbs.order.item.domain.Item;
import itembbs.order.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * date           : 2025-01-03
 * created by     : 임경재
 * description    :
 */
@RestController
@RequiredArgsConstructor
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

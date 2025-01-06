package itembbs.order.item.service;

import itembbs.order.item.domain.Item;
import itembbs.order.item.domain.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * date           : 2025-01-03
 * created by     : 임경재
 * description    :
 */
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Long saveItem(Item item) {
        return itemRepository.save(item).getId();
    }
}

package itembbs.order.common.init;

import itembbs.order.item.domain.Item;
import itembbs.order.item.domain.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

/**
 * date           : 2025-01-09
 * created by     : 임경재
 * description    :
 */
@RequiredArgsConstructor
public class ItemInit {

    private final ItemRepository itemRepository;

    @PostConstruct
    @Transactional
    public void init() {
        for (int i = 0; i < 10; i++) {
            Item item = new Item("item" + i, (i + 1) * 1000, i * 10);
            itemRepository.save(item);
        }
    }
}

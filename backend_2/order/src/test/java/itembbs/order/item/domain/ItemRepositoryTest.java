package itembbs.order.item.domain;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * date           : 2025-01-03
 * created by     : 임경재
 * description    :
 */
@SpringBootTest
@DisplayName("아이템 리포지토리에서")
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private EntityManager em;

    @Test
    @Transactional
    @DisplayName("아이템 저장에 성공해야한다.")
    public void itemSave() throws Exception{
        // given
        Item item = new Item("item1", 10000, 10);
        // when
        itemRepository.save(item);
        // then
        em.flush();
        em.clear();

        Item findItem = itemRepository.findById(item.getId())
                .orElseThrow(() -> new RuntimeException("에러"));

        assertThat(item.getItemName()).isEqualTo(findItem.getItemName());
    }

    @Test
    @DisplayName("조건에 맞는 아이템 조회에 성공해야 한다.")
    public void itemFind() throws Exception{
        // given

        // when

        // then
    }
}
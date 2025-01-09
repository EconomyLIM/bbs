package itembbs.order.config;

import itembbs.order.common.init.ItemInit;
import itembbs.order.common.init.MemberInit;
import itembbs.order.item.domain.ItemRepository;
import itembbs.order.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * date           : 2025-01-09
 * created by     : 임경재
 * description    :
 */
@Configuration
public class InitConfig {

//    private final ItemRepository itemRepository;
//    private final MemberRepository memberRepository;

    @Bean
    public ItemInit itemInit(ItemRepository itemRepository) {
        return new ItemInit(itemRepository);
    }

    @Bean
    public MemberInit memberInit(MemberRepository memberRepository) {
        return new MemberInit(memberRepository);
    }
}

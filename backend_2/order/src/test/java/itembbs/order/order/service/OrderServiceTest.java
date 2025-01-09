package itembbs.order.order.service;

import itembbs.order.item.domain.Item;
import itembbs.order.item.domain.ItemRepository;
import itembbs.order.member.domain.Member;
import itembbs.order.member.domain.MemberRepository;
import itembbs.order.order.domain.Address;
import itembbs.order.order.dto.OrderDTO;
import itembbs.order.order.dto.OrderRequest;
import itembbs.order.order.dto.OrderSaveResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * date           : 2025-01-09
 * created by     : 임경재
 * description    :
 */
@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("주문에 성공해야 한다.")
    public void saveOrder() throws Exception{
        // given
        List<OrderDTO> list = new ArrayList<>();
        list.add(new OrderDTO(2L, 1000, 1));
        list.add(new OrderDTO(3L, 2000, 2));
        OrderRequest orderRequest = new OrderRequest(new Address("city", "street", "zipcode"), list);

        // when
        OrderSaveResponse response = orderService.order(orderRequest, new Member("test@test.com", "1234!"));

        // then
        Assertions.assertThat(response.getOrderId()).isEqualTo(1L);


        List<OrderDTO> list2 = new ArrayList<>();
        list2.add(new OrderDTO(4L, 1000, 1));
        list2.add(new OrderDTO(5L, 2000, 2));
        OrderRequest orderRequest2 = new OrderRequest(new Address("city", "street", "zipcode"), list2);
        OrderSaveResponse response2 = orderService.order(orderRequest2, new Member("test@test.com", "1234!"));
    }

    @Test
    @DisplayName("멀티 스레드 환경에서 동시에 주문을 시도할 경우 재고 감소가 올바르게 처리되는지 확인한다.")
    void concurrencyOrderTest() throws InterruptedException {
        // given
        // 1) 사전 준비 - 테스트용 아이템 & 멤버 확인/생성
        Long testItemId = 2L; // 이미 DB에 존재한다고 가정
        int initialStock = 10; // 예: DB에서 직접 확인하거나, setUp() 등으로 준비

        // 재고가 10개인 아이템이 있다고 가정
        Item testItem = itemRepository.findById(testItemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        // 멤버도 미리 DB에 존재한다고 가정 (이메일이 "test@test.com")
        Member testMember = memberRepository.findByEmail("test@test.com")
                .orElseGet(() -> memberRepository.save(new Member("test@test.com", "1234!")));

        // when
        // 2) 동시 호출 스레드 개수 및 차감 수량 설정
        int numberOfThreads = 5;         // 스레드를 5개 띄워서 동시에 주문
        int quantityPerOrder = 2;        // 각 스레드마다 2개씩 주문

        // 3) 멀티 스레드 실행 준비
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        // 스레드에서 실행할 로직
        Runnable task = () -> {
            try {
                // 각 스레드가 수행할 주문 요청
                List<OrderDTO> orderList = new ArrayList<>();
                // 동일한 상품 1종을 quantityPerOrder만큼 주문
                orderList.add(new OrderDTO(testItemId, /* currentPrice */ 1000, quantityPerOrder));

                OrderRequest orderRequest = new OrderRequest(
                        new Address("city", "street", "zipcode"),
                        orderList
                );

                // 동시 주문
                orderService.order(orderRequest, testMember);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        };

        // 4) 여러 개의 스레드를 실행
        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(task);
        }

        // 5) 모든 스레드가 끝날 때까지 대기
        latch.await();
        executorService.shutdown();

        // then
        // 6) 최종 재고 확인
        Item afterItem = itemRepository.findById(testItemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found after concurrency test"));

        int expectedStock = initialStock - (numberOfThreads * quantityPerOrder);
        System.out.println(">>> 최종 재고: " + afterItem.getStock());
        System.out.println(">>> 기대 재고: " + expectedStock);

        Assertions.assertThat(afterItem.getStock()).isEqualTo(expectedStock);
    }
}
package itembbs.order.order.service;

import itembbs.order.item.domain.Item;
import itembbs.order.item.domain.ItemRepository;
import itembbs.order.member.domain.Member;
import itembbs.order.member.domain.MemberRepository;
import itembbs.order.order.domain.*;
import itembbs.order.order.dto.OrderDTO;
import itembbs.order.order.dto.OrderRequest;
import itembbs.order.order.dto.OrderSaveResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * date           : 2025-01-09
 * created by     : 임경재
 * description    :
 */
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final DeliveryRepository deliveryRepository;
    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public OrderSaveResponse order(OrderRequest request, Member member) {
        List<OrderDTO> items = request.getItems();
        Address address = request.getAddress();

        List<OrderItem> orderItems = new ArrayList<>();
        Delivery delivery = new Delivery(address, DeliveryStatus.READY);

        Member findMember = memberRepository.findByEmail(member.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("user not found"));

        for (OrderDTO orderDTO : items) {
            Long itemId = orderDTO.getId();
            Item findItem = itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("item not found"));

            findItem.decreaseStock(orderDTO.getQuantity());

            OrderItem orderItem = new OrderItem(findItem, orderDTO.getCurrentPrice(), orderDTO.getQuantity());
            orderItems.add(orderItem);
        }

        Order createdOrder = Order.createOrder(findMember, orderItems, delivery);

        // Todo 결제 처리

        orderRepository.save(createdOrder);
        return new OrderSaveResponse(createdOrder.getId());
    }


}

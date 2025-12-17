package com.seowon.coding.service;

import com.seowon.coding.domain.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryEventListener {

    /**
     * 주문 생성 이벤트를 구독하여 재고를 차감하는 로직을 구현하세요.
     * 1. @EventListener 또는 @TransactionalEventListener 활용
     * 2. 주문-재고 시스템 분리 시 발생할 수 있는 '최종적 일관성(Eventual Consistency)' 고려
     * 3. 실패 시 보상 트랜잭션 또는 재시도 전략에 대한 고민을 주석으로 남길 것
     */
    @EventListener
    public void handleOrderCreatedEvent(OrderCreatedEvent event) {
    }
}

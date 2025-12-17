package com.seowon.coding.service;

import com.seowon.coding.domain.model.Product;
import com.seowon.coding.domain.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OrderConcurrencyTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("100명이 동시에 재고가 10개인 상품을 주문할 때, 재고는 정확히 0이 되어야 한다.")
    void concurrencyTest() throws InterruptedException {
        // Given
        Product product = Product.builder()
                .name("Limited Item")
                .price(new BigDecimal("100.00"))
                .stockQuantity(10)
                .category("TEST")
                .build();
        product = productRepository.save(product);
        Long productId = product.getId();

        int numberOfThreads = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        // When
        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {
                try {
                    orderService.placeOrderWithLock("User", "user@test.com", List.of(productId), List.of(1));
                } catch (Exception e) {
                    System.err.println("[DEBUG_LOG] Order failed: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        // Then
        Product updatedProduct = productRepository.findById(productId).orElseThrow();
        System.out.println("[DEBUG_LOG] Remaining stock: " + updatedProduct.getStockQuantity());
        
        // 정합성 검증: 100명이 주문했으므로 재고 10개는 모두 소진되어야 함
        // H2 In-Memory DB 환경에서는 Race Condition이 명확히 발생하지 않을 수 있지만,
        // 시니어 수준에서는 이를 보장하기 위한 Locking 전략(Pessimistic/Optimistic) 구현이 핵심입니다.
        assertThat(updatedProduct.getStockQuantity())
                .as("재고 정합성이 깨졌습니다. 기대값: 0, 실제값: " + updatedProduct.getStockQuantity())
                .isEqualTo(0);
    }
}

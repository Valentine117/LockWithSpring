package com.example.springlock;

import com.example.springlock.domain.Stock;
import com.example.springlock.repository.StockRepository;
import com.example.springlock.service.PlainStockService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@SpringBootTest
class StockConcurrencyTest {

    @Autowired
    private PlainStockService plainService;

    @Autowired
    private StockRepository stockRepository;

    @BeforeEach
    void setup() {
        stockRepository.save(new Stock(1L, 100));
    }

    @Test
    void simulateConcurrencyProblem() throws InterruptedException {
        int threadCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    plainService.decrease(1L, 10);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Stock stock = stockRepository.findById(1L).orElseThrow();
        log.info("남은 재고: " + stock.getQuantity());
    }
}

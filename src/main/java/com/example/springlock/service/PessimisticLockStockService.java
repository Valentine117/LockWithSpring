package com.example.springlock.service;

import com.example.springlock.domain.Stock;
import com.example.springlock.repository.StockRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PessimisticLockStockService {
    private final StockRepository stockRepository;

    @Transactional
    public void decrease(Long id, int quantity) {
        Stock stock = stockRepository.findByIdForUpdate(id).orElseThrow();
        stock.decrease(quantity);
    }
}

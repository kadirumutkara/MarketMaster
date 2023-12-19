package com.ing.marketMaster.marketMaster.service;

import com.ing.marketMaster.model.Stock;
import com.ing.marketMaster.model.StockExchange;
import com.ing.marketMaster.repository.StockExchangeRepository;
import com.ing.marketMaster.repository.StockRepository;
import com.ing.marketMaster.service.StockService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class StockServiceTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private StockExchangeRepository stockExchangeRepository;

    @InjectMocks
    private StockService stockService;

    @Test
    public void testCreateStock() {
        Stock stock = new Stock();
        stock.setName("Test Stock");
        stock.setCurrentPrice(new BigDecimal("100.00"));
        stock.setLastUpdate(LocalDateTime.now());

        when(stockRepository.save(any(Stock.class))).thenReturn(stock);
        Stock created = stockService.createStock(stock);

        assertNotNull(created);
        assertEquals(stock.getName(), created.getName());
        verify(stockRepository, times(1)).save(stock);
    }

    @Test
    public void testGetAllStocks() {
        Stock stock1 = new Stock();
        Stock stock2 = new Stock();
        when(stockRepository.findAll()).thenReturn(Arrays.asList(stock1, stock2));

        List<Stock> stocks = stockService.getAllStocks();

        assertNotNull(stocks);
        assertEquals(2, stocks.size());
        verify(stockRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateStockPrice() {
        Stock stock = new Stock();
        stock.setId(1L);
        stock.setCurrentPrice(new BigDecimal("100.00"));

        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));
        when(stockRepository.save(any(Stock.class))).thenReturn(stock);

        Stock updated = stockService.updateStockPrice(1L, new BigDecimal("200.00"));

        assertNotNull(updated);
        assertEquals(new BigDecimal("200.00"), updated.getCurrentPrice());
        verify(stockRepository, times(1)).findById(1L);
        verify(stockRepository, times(1)).save(stock);
    }

    @Test
    public void testDeleteStock() {
        Long stockId = 1L;
        when(stockRepository.existsById(stockId)).thenReturn(true);
        stockService.deleteStock(stockId);
        verify(stockRepository, times(1)).deleteById(stockId);
    }
}




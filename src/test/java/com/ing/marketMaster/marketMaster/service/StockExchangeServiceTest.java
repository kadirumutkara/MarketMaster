package com.ing.marketMaster.marketMaster.service;

import com.ing.marketMaster.model.Stock;
import com.ing.marketMaster.model.StockExchange;
import com.ing.marketMaster.repository.StockExchangeRepository;
import com.ing.marketMaster.repository.StockRepository;
import com.ing.marketMaster.service.StockExchangeService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;





@SpringBootTest
public class StockExchangeServiceTest {

    @Mock
    private StockExchangeRepository stockExchangeRepository;

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockExchangeService stockExchangeService;

    private StockExchange mockExchange;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockExchange = new StockExchange("TestExchange", "Description", false);
        mockExchange.setStocks(new HashSet<>());


        when(stockExchangeRepository.findByName("TestExchange"))
                .thenAnswer(invocation -> new StockExchange(mockExchange.getName(), mockExchange.getDescription(), mockExchange.getLiveInMarket()));

        when(stockRepository.save(any(Stock.class)))
                .thenAnswer(invocation -> {
                    Stock stock = invocation.getArgument(0);
                    mockExchange.getStocks().add(stock);
                    return stock;
                });

        when(stockExchangeRepository.save(any(StockExchange.class)))
                .thenAnswer(invocation -> {
                    StockExchange savedExchange = invocation.getArgument(0);
                    mockExchange.setLiveInMarket(savedExchange.getLiveInMarket());
                    mockExchange.setStocks(new HashSet<>(savedExchange.getStocks()));
                    return savedExchange;
                });
    }





    @Test
    public void testRemoveStockFromStockExchange() {
        String exchangeName = "TestExchange";
        StockExchange stockExchange = new StockExchange();
        stockExchange.setName(exchangeName);
        HashSet<Stock> stocks = new HashSet<>();
        Stock stock = new Stock();
        stock.setId(1L);
        stocks.add(stock);
        stockExchange.setStocks(stocks);

        when(stockExchangeRepository.findByName(exchangeName)).thenReturn(stockExchange);
        when(stockExchangeRepository.save(any(StockExchange.class))).thenReturn(stockExchange);

        StockExchange updatedExchange = stockExchangeService.removeStockFromStockExchange(exchangeName, 1L);

        assertNotNull(updatedExchange);
        assertFalse(updatedExchange.getStocks().contains(stock));
        verify(stockExchangeRepository, times(1)).save(stockExchange);
    }

}



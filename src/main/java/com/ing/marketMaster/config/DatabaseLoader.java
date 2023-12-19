package com.ing.marketMaster.config;


import com.ing.marketMaster.model.Stock;
import com.ing.marketMaster.model.StockExchange;
import com.ing.marketMaster.repository.StockRepository;
import com.ing.marketMaster.repository.StockExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class DatabaseLoader implements CommandLineRunner {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockExchangeRepository stockExchangeRepository;

    @Override
    public void run(String... args) throws Exception {
        //Create Stock
        Stock stock1 = new Stock("BMW", "Car trade", new BigDecimal("100.00"), LocalDateTime.now());
        Stock stock2 = new Stock("LUTFANSA", "AIRLINE", new BigDecimal("200.00"), LocalDateTime.now());
        Stock stock3 = new Stock("BANK", "BANK TRADE", new BigDecimal("999.00"), LocalDateTime.now());

        // Create Exchange
        StockExchange exchange1 = new StockExchange("NASDAQ", "Description1", false);
        StockExchange exchange2 = new StockExchange("ISTANBUL", "Description2", false);
        StockExchange exchange3 = new StockExchange("AMSTERDAM", "Description3", false);


        exchange1.getStocks().add(stock1);
        exchange2.getStocks().add(stock2);
        exchange1.getStocks().add(stock3);


        stockExchangeRepository.save(exchange1);
        stockExchangeRepository.save(exchange2);
        stockExchangeRepository.save(exchange3);


    }
}


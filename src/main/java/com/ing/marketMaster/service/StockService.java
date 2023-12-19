package com.ing.marketMaster.service;


import com.ing.marketMaster.exception.BusinessException;
import com.ing.marketMaster.exception.ErrorCodes;
import com.ing.marketMaster.model.Stock;
import com.ing.marketMaster.model.StockExchange;
import com.ing.marketMaster.repository.StockExchangeRepository;
import com.ing.marketMaster.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockExchangeRepository stockExchangeRepository;

    private static final Logger log = LoggerFactory.getLogger(StockService.class);


    @Transactional(readOnly = true)
    public List<Stock> getAllStocks() {
        log.info("Fetching all stocks");
        List<Stock> stocks = stockRepository.findAll();
        log.info("Successfully fetched {} stocks", stocks.size());
        return stocks;
    }


    @Transactional
    public Stock createStock(Stock stock) {
        log.info("Creating a new stock with name: {}", stock.getName());

        if (stock.getName() == null || stock.getName().isEmpty()) {
            log.error("Stock name cannot be null or empty");
            throw new BusinessException("Stock name cannot be null or empty", ErrorCodes.STOCK_NAME_REQUIRED);
        }

        if (stock.getCurrentPrice() == null) {
            log.error("Stock current price cannot be null");
            throw new BusinessException("Stock current price cannot be null", ErrorCodes.STOCK_PRICE_CANNOT_BE_NULL);
        }
        if (stock.getCurrentPrice().signum() == -1) {
            log.error("Stock price must not be negative: {}", stock.getCurrentPrice());
            throw new BusinessException("Stock price must not be negative", ErrorCodes.STOCK_PRICE_MUST_NOT_BE_NEGATIVE);
        }
        stock.setLastUpdate(LocalDateTime.now());
        Stock savedStock = stockRepository.save(stock);
        log.info("Stock created successfully with ID: {}", savedStock.getId());
        return savedStock;
    }


    @Transactional
    public Stock updateStockPrice(Long id, BigDecimal newPrice) {
        log.info("Updating stock price for stock with ID: {}", id);

        if (newPrice == null) {
            log.error("Stock price cannot be null");
            throw new BusinessException("Stock price cannot be null", ErrorCodes.STOCK_PRICE_CANNOT_BE_NULL);
        }

        if (newPrice.signum() == -1) {
            log.error("Stock price must not be negative: {}", newPrice);
            throw new BusinessException("Stock price must not be negative", ErrorCodes.STOCK_PRICE_MUST_NOT_BE_NEGATIVE);
        }

        Optional<Stock> stockOpt = stockRepository.findById(id);
        Stock stock = stockOpt.orElseThrow(() -> {
            log.error("Stock not found for ID: {}", id);
            return new BusinessException("Stock not found", ErrorCodes.STOCK_NOT_FOUND);
        });

        stock.setCurrentPrice(newPrice);
        stock.setLastUpdate(LocalDateTime.now());
        Stock updatedStock = stockRepository.save(stock);
        log.info("Stock price updated successfully for stock with ID: {}", id);
        return updatedStock;
    }


    @Transactional
    public void deleteStock(Long id) {
     /*   log.info("Attempting to delete stock with ID: {}", id);
        if (!stockRepository.existsById(id)) {
            log.error("Stock not found with ID: {}", id);
            throw new BusinessException("Stock not found", ErrorCodes.STOCK_NOT_FOUND);
        }
        List<StockExchange> stockExchanges = stockExchangeRepository.findAll();
        for (StockExchange exchange : stockExchanges) {
            boolean removed = exchange.getStocks().removeIf(stock -> stock.getId().equals(id));
            if (removed) {
                stockExchangeRepository.save(exchange);
            }
        }
        stockRepository.deleteById(id);
        log.info("Stock with ID: {} successfully deleted", id);*/


        log.info("Attempting to delete stock with ID: {}", id);
        stockExchangeRepository.removeStockFromAllExchanges(id);
        stockRepository.deleteById(id);
        log.info("Stock with ID: {} successfully deleted", id);
    }
}

package com.ing.marketMaster.service;

import com.ing.marketMaster.exception.BusinessException;
import com.ing.marketMaster.exception.ErrorCodes;
import com.ing.marketMaster.model.Stock;
import com.ing.marketMaster.model.StockExchange;
import com.ing.marketMaster.repository.StockExchangeRepository;
import com.ing.marketMaster.repository.StockRepository;
import com.sun.xml.bind.v2.TODO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class StockExchangeService {

    private static final Logger log = LoggerFactory.getLogger(StockExchangeService.class);


    @Autowired
    private StockExchangeRepository stockExchangeRepository;

    @Autowired
    private StockRepository stockRepository;

    @Transactional(readOnly = true)
    public StockExchange getStockExchangeWithStocks(String name) {
        log.info("Fetching stock exchange with name: {}", name);
        StockExchange stockExchange = stockExchangeRepository.findByName(name);

        if (stockExchange == null) {
            log.error("Stock exchange not found with name: {}", name);
            throw new BusinessException("Stock exchange not found", ErrorCodes.STOCK_EXCHANGE_NOT_FOUND);
        }

        log.info("Successfully fetched stock exchange with name: {}", name);
        return stockExchange;
    }

    /* if (existingStockOpt.isPresent()) {
            existingStock = existingStockOpt.get();
            // Check if the stock is already part of this exchange
            if (stockExchangeRepository.isStockInExchange(stockExchange.getId(), existingStock.getId())) {
                log.error("Stock already exists in this exchange: {}", stock.getName());
                throw new BusinessException("Stock already exists in this exchange", ErrorCodes.STOCK_ALREADY_EXISTS_IN_EXCHANGE);
            }
        } else {
            // If the stock does not exist, save it as a new entity
            existingStock = stockRepository.save(stock);
        }*/

    @Transactional
    public StockExchange addStockToStockExchange(String name, Stock stock) {
        log.info("Adding stock to stock exchange: {}", name);
        StockExchange stockExchange = stockExchangeRepository.findByName(name);
        if (stockExchange == null) {
            log.error("Stock exchange not found: {}", name);
            throw new BusinessException("Stock exchange not found", ErrorCodes.STOCK_EXCHANGE_NOT_FOUND);
        }

        Optional<Stock> existingStockOpt = stockRepository.findByName(stock.getName());
        Stock existingStock;
        // REMOVED for Contains Performance PROBLEM
        if (existingStockOpt.isPresent()) {
            // Use the existing stock from the database
            existingStock = existingStockOpt.get();
        } else {
            // If the stock does not exist, save it as a new entity
            existingStock = stockRepository.save(stock);
        }

        // Check if the stock is already part of this exchange
        if (stockExchange.getStocks().contains(existingStock)) {
            log.error("Stock already exists in this exchange: {}", stock.getName());
            throw new BusinessException("Stock already exists in this exchange", ErrorCodes.STOCK_ALREADY_EXISTS_IN_EXCHANGE);
        }

        // Add the existing or new stock to the stock exchange
        stockExchange.getStocks().add(existingStock);
        updateLiveInMarketStatus(stockExchange);
        stockExchangeRepository.save(stockExchange);

        log.info("Stock successfully added to exchange: {}", name);
        return stockExchange;

    }

    @Transactional
    public StockExchange removeStockFromStockExchange(String name, Long stockId) {
        log.info("Removing stock with ID {} from stock exchange: {}", stockId, name);
        StockExchange stockExchange = stockExchangeRepository.findByName(name);

        if (stockExchange == null) {
            log.error("Stock exchange not found: {}", name);
            throw new BusinessException("Stock exchange not found", ErrorCodes.STOCK_EXCHANGE_NOT_FOUND);
        }

        boolean removed = stockExchange.getStocks().removeIf(stock -> stock.getId().equals(stockId));
        if (!removed) {
            log.error("Stock with ID {} not found in exchange: {}", stockId, name);
            throw new BusinessException("Stock not in exchange", ErrorCodes.STOCK_NOT_IN_EXCHANGE);
        }

        updateLiveInMarketStatus(stockExchange);
        stockExchangeRepository.save(stockExchange);
        log.info("Stock with ID {} successfully removed from exchange: {}", stockId, name);
        return stockExchange;
    }

    private void updateLiveInMarketStatus(StockExchange stockExchange) {
        boolean liveStatus = stockExchange.getStocks().size() >= 5;
        stockExchange.setLiveInMarket(liveStatus);
    }
}


package com.ing.marketMaster.controller;

import com.ing.marketMaster.exception.ApplicationException;
import com.ing.marketMaster.exception.BusinessException;
import com.ing.marketMaster.exception.ErrorCodes;
import com.ing.marketMaster.model.Stock;
import com.ing.marketMaster.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    @Autowired
    private StockService stockService;


    // List of all stocks
    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {
        List<Stock> stocks = stockService.getAllStocks();
        return ResponseEntity.ok(stocks);
    }


    // Create New Stock
    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody Stock stock) {
        Stock createdStock = stockService.createStock(stock);
        return ResponseEntity.ok(createdStock);
    }

    // Update the price of a stock
    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStockPrice(@PathVariable Long id, @RequestParam BigDecimal newPrice) {
        Stock updatedStock = stockService.updateStockPrice(id, newPrice);
        return ResponseEntity.ok(updatedStock);
    }

    // Delete stock from system
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        return ResponseEntity.ok("Stock "+ id+ " successfully deleted .");
    }


}

package com.ing.marketMaster.controller;

import com.ing.marketMaster.model.Stock;
import com.ing.marketMaster.model.StockExchange;
import com.ing.marketMaster.service.StockExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stock-exchange")
public class StockExchangeController {

    @Autowired
    private StockExchangeService stockExchangeService;


    @GetMapping("/{name}")
    public ResponseEntity<StockExchange> getStockExchangeWithStocks(@PathVariable String name) {
        StockExchange stockExchange = stockExchangeService.getStockExchangeWithStocks(name);
        return ResponseEntity.ok(stockExchange);
    }

    @PostMapping("/{name}")
    public ResponseEntity<StockExchange> addStockToStockExchange(@PathVariable String name, @RequestBody Stock stock) {
        StockExchange stockExchange = stockExchangeService.addStockToStockExchange(name, stock);
        return ResponseEntity.ok(stockExchange);
    }


    @DeleteMapping("/{exchangeName}/{stockId}")
    public ResponseEntity<?> removeStockFromStockExchange(@PathVariable String exchangeName, @PathVariable Long stockId) {
        stockExchangeService.removeStockFromStockExchange(exchangeName, stockId);
        return ResponseEntity.ok("Stock "+ stockId+ " successfully deleted .");
    }
}

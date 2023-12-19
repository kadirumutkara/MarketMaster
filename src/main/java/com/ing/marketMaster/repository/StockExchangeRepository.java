package com.ing.marketMaster.repository;

import com.ing.marketMaster.model.StockExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StockExchangeRepository extends JpaRepository<StockExchange, Long> {

    StockExchange findByName(String name);

    @Modifying
    @Query(value = "DELETE FROM stock_exchange_stock WHERE stock_id = :stockId", nativeQuery = true)
    void removeStockFromAllExchanges(@Param("stockId") Long stockId);


    @Modifying
    @Query(value = "SELECT COUNT(s) > 0 FROM StockExchange se JOIN se.stocks s WHERE se.id = :exchangeId AND s.id = :stockId", nativeQuery = true)
    boolean isStockInExchange(@Param("exchangeId") Long exchangeId, @Param("stockId") Long stockId);
}

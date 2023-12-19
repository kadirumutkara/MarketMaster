package com.ing.marketMaster.repository;

import com.ing.marketMaster.model.Stock;
import com.ing.marketMaster.model.StockExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> findByName(String name);


   /* @Modifying
    @Query("UPDATE Stock s SET s.currentPrice = :newPrice, s.lastUpdate = :newLastUpdate WHERE s.id = :id")
    void updateStockPriceAndLastUpdate(@Param("id") Long id, @Param("newPrice") BigDecimal newPrice, @Param("newLastUpdate") LocalDateTime newLastUpdate);*/


}
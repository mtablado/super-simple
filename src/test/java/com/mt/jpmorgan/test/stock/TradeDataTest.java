package com.mt.jpmorgan.test.stock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mt.jpmorgan.SuperSimpleApp;
import com.mt.jpmorgan.dao.stock.TradeData;
import com.mt.jpmorgan.model.Trade;
import com.mt.jpmorgan.types.StockSymbol;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SuperSimpleApp.class)
public class TradeDataTest {

	private static final Duration WINDOW = Duration.ofMinutes(15);
	private static final Duration EXCEEDED_DURATION = Duration.ofMinutes(45);

	private Trade oldTrade;
	private Trade currentTrade1;
	private Trade currentTrade2;
	private Trade currentTrade3;
	private Trade currentTrade4;

	@Autowired
	private TradeData tradeData;

	@Before
	public void init() {
		LocalDateTime now = LocalDateTime.now();
		this.currentTrade1 = new Trade();
		this.currentTrade1.setStockSymbol(StockSymbol.GIN);
		this.currentTrade1.setDate(now);
		this.currentTrade1.setQuantity(100);

		this.currentTrade2 = new Trade();
		this.currentTrade2.setStockSymbol(StockSymbol.GIN);
		this.currentTrade2.setDate(now);
		this.currentTrade2.setQuantity(15);

		this.currentTrade3 = new Trade();
		this.currentTrade3.setStockSymbol(StockSymbol.JOE);
		this.currentTrade3.setDate(now);
		this.currentTrade3.setQuantity(1800);

		this.currentTrade4 = new Trade();
		this.currentTrade4.setStockSymbol(StockSymbol.ALE);
		this.currentTrade4.setDate(now);
		this.currentTrade4.setQuantity(733);
		this.currentTrade4.setPrice(new BigDecimal("0.08"));

		this.oldTrade = new Trade();
		this.oldTrade.setStockSymbol(StockSymbol.ALE);
		this.oldTrade.setDate(now.minus(EXCEEDED_DURATION));
		this.oldTrade.setQuantity(17);
		this.oldTrade.setPrice(new BigDecimal("0.95"));

		this.tradeData.addTrade(this.currentTrade1);
		this.tradeData.addTrade(this.currentTrade2);
		this.tradeData.addTrade(this.currentTrade3);
		this.tradeData.addTrade(this.currentTrade4);
		this.tradeData.addTrade(this.oldTrade);
	}

	@Test
	public void filterTrades() {
		List<Trade> allTrades = this.tradeData.search();

		assertFalse("Trades cannot be null", allTrades == null);
		assertEquals("Trades are expected to be 5", 5, allTrades.size());

		List<Trade> currentTrades =
				this.tradeData.filterByCurrentTrades(StockSymbol.ALE, WINDOW);
		assertFalse("currentTrades cannot be null", currentTrades == null);
		assertEquals("currentTrades are expected to be 1", 1, currentTrades.size());
	}

}

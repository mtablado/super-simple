package com.mt.jpmorgan.dao.stock;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.mt.jpmorgan.model.Trade;
import com.mt.jpmorgan.types.StockSymbol;

@Component
public class TradeData {

	private final List<Trade> records = new ArrayList<Trade>();

	private Predicate<Trade> isCurrentTrade(Duration thresholdTime) {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime initialTime = now.minus(thresholdTime);
		return (trade) -> trade.getDate().isAfter(initialTime);
	}

	private Predicate<Trade> isStockType(StockSymbol stockSymbol) {
		return (trade) -> trade.getStockSymbol().equals(stockSymbol);
	}

	public List<Trade> filterByCurrentTrades(StockSymbol stockSymbol, Duration thresholdTime) {

		// Avoid streaming if the list if empty
		if (this.records.isEmpty()) {
			return this.records;
		}

		List<Trade> currentTrades = this.records
			.stream()
			.filter(this.isStockType(stockSymbol))
			.filter(this.isCurrentTrade(thresholdTime))
			.collect(Collectors.<Trade>toList());
		return currentTrades;
	}

	public Map<StockSymbol, List<Trade>> groupTradesBySymbol() {

		return this.records
			.stream()
			.collect(Collectors.groupingBy(Trade::getStockSymbol));
	}

	public List<Trade> search(StockSymbol stockSymbol) {

		// Avoid streaming if the list if empty
		if (this.records.isEmpty()) {
			return this.records;
		}

		List<Trade> stockTrades = this.records
			.stream()
			.filter(this.isStockType(stockSymbol))
			.collect(Collectors.<Trade>toList());
		return stockTrades;
	}

	public List<Trade> search() {
		return this.records;
	}

	public void addTrade(Trade trade) {
		this.records.add(trade);
	}

}

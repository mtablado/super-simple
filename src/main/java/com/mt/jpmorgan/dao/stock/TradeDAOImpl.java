package com.mt.jpmorgan.dao.stock;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mt.jpmorgan.model.Trade;
import com.mt.jpmorgan.types.StockSymbol;

@Component
public class TradeDAOImpl implements TradeDAO {

	@Autowired
	private TradeData tradeData;

	@Override
	public void recordTrade(Trade trade) {
		this.tradeData.addTrade(trade);
	}

	@Override
	public List<Trade> search(StockSymbol stockSymbol) {
		return this.tradeData.search(stockSymbol);
	}

	@Override
	public List<Trade> searchCurrentTrades(StockSymbol stockSymbol, Duration thresholdTime) {
		return this.tradeData.filterByCurrentTrades(stockSymbol, thresholdTime);
	}

	@Override
	public Map<StockSymbol, List<Trade>> groupTradesBySymbol() {
		return this.tradeData.groupTradesBySymbol();
	}

	@Override
	public List<Trade> search() {
		return this.tradeData.search();
	}

}

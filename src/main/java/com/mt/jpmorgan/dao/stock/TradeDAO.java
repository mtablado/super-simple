package com.mt.jpmorgan.dao.stock;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import com.mt.jpmorgan.model.Trade;
import com.mt.jpmorgan.types.StockSymbol;

public interface TradeDAO {

	void recordTrade(Trade trade);

	List<Trade> searchCurrentTrades(StockSymbol stockSymbol, Duration thresholdTime);

	/**
	 * Returns all the trades recorded for a given stock type.
	 * @param stockSymbol The stock type.
	 * @return A list of trades.
	 */
	List<Trade> search(StockSymbol stockSymbol);

	/**
	 * Returns all the trades recorded.
	 * @return A list of trades.
	 */
	List<Trade> search();

	/**
	 * Returns a map that groups the trades by their symbol.
	 * @return A map of trades where the map key is the stock symbol.
	 */
	Map<StockSymbol, List<Trade>> groupTradesBySymbol();
}

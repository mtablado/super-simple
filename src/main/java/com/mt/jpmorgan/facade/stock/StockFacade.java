package com.mt.jpmorgan.facade.stock;

import java.math.BigDecimal;

import com.mt.jpmorgan.types.StockSymbol;

public interface StockFacade {

	void buyStocks(StockSymbol stockSymbol, BigDecimal price, Integer quantity);

	void sellStocks(StockSymbol stockSymbol, BigDecimal price, Integer quantity);

	/**
	 * Calculates the dividend yield for a given stock.
	 * @param stockSymbol The stock type.
	 */
	BigDecimal calculateDividendYield(StockSymbol stockSymbol);

	/**
	 * Calculates Stock Price based on trades recorded in past 15 minutes.
	 * @param stockSymbol The stock type.
	 * @return The calculated price.
	 */
	BigDecimal calculateTickerPrice(StockSymbol stockSymbol);

	/**
	 * Calculates the stock price based on all recorded trades.
	 * @param stockSymbol The stock type.
	 * @return The calculated price.
	 */
	BigDecimal calculateStockPrice(StockSymbol stockSymbol);

	/**
	 * Calculates the P/E Ratio as result of divide the ticker price by dividend.
	 * @param stockSymbol The stock type.
	 * @return The calculated ratio.
	 */
	BigDecimal calculatePERatio(StockSymbol stockSymbol);

	/**
	 * Calculates the GBCE All Share Index.
	 * @return The GBCE All share Index.
	 */
	BigDecimal calculateGBCE();

}

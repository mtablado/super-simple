package com.mt.jpmorgan.service.stock;

import java.math.BigDecimal;
import java.util.List;

import com.mt.jpmorgan.types.StockSymbol;

public interface StockService {

	/**
	 * Calculates the dividend yield for a given stock.
	 * @param stockSymbol The stock type.
	 */
	BigDecimal calculateDividendYield(StockSymbol stockSymbol);

	/**
	 * Calculates Stock Price based on trades recorded in past 15 minutes.
	 * If no current trades are found the ticker price will be the par value.
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
	 * We assume that if:
	 *   i. Not fixed dividend exists
	 *   ii. and Last dividend is set to zero
	 * Then, the P/E ratio is zero.
	 * @param stockSymbol The stock type.
	 * @return The calculated ratio.
	 */
	BigDecimal calculatePERatio(StockSymbol stockSymbol);

	/**
	 * Method to calculate the geometric mean of a list of values.
	 * @param prices The list of values for the calculation.
	 * @return The Geometric Mean.
	 */
	BigDecimal calculateGeometricMean(List<BigDecimal> prices);

	/**
	 * Calculates the GBCE All Share Index.
	 * If no trades are available, it returns 0.
	 * @return The GBCE All share Index.
	 */
	BigDecimal calculateGBCE();

}

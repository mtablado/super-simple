package com.mt.jpmorgan.service.stock;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mt.jpmorgan.dao.stock.StockDAO;
import com.mt.jpmorgan.dao.stock.TradeDAO;
import com.mt.jpmorgan.model.Stock;
import com.mt.jpmorgan.model.Trade;
import com.mt.jpmorgan.types.StockSymbol;
import com.mt.jpmorgan.types.StockType;

@Service
public class StockServiceImpl implements StockService {

	private static final Duration WINDOW = Duration.ofMinutes(15);

	@Autowired
	private StockDAO stockDao;

	@Autowired
	private TradeDAO tradeDao;

	@Override
	public BigDecimal calculateDividendYield(StockSymbol stockSymbol) {

		Stock stock = this.stockDao.searchBySymbol(stockSymbol);
		BigDecimal stockTickerPrice = this.calculateTickerPrice(stockSymbol);

		BigDecimal dividend;
		if (StockType.PREFERRED.equals(stock.getStockType())) {
			dividend = this.calculateFixedDividendYield(stock, stockTickerPrice);
		} else {
			dividend = this.calculateCommonDividendYield(stock, stockTickerPrice);
		}

		return dividend;

	}

	@Override
	public BigDecimal calculateTickerPrice(StockSymbol stockSymbol) {
		BigDecimal tickerPrice;
		List<Trade> currentTrades = this.tradeDao.searchCurrentTrades(stockSymbol, WINDOW);
		if (null == currentTrades || currentTrades.isEmpty()) {
			// If no previous trades are recorded we assume that the price is the par value.
			tickerPrice = this.searchStockParValue(stockSymbol);
		} else {
			tickerPrice = this.calculateStockPrice(currentTrades);
		}

		return tickerPrice;
	}

	@Override
	public BigDecimal calculateStockPrice(StockSymbol stockSymbol) {
		List<Trade> trades = this.tradeDao.search(stockSymbol);
		BigDecimal stockPrice;
		if (null == trades || trades.isEmpty()) {
			// If no previous trades are recorded we assume that the price is the par value.
			stockPrice = this.searchStockParValue(stockSymbol);
		} else {
			stockPrice = this.calculateStockPrice(trades);
		}

		return stockPrice;
	}

	private BigDecimal searchStockParValue(StockSymbol stockSymbol) {
		Stock stock = this.stockDao.searchBySymbol(stockSymbol);
		return stock.getParValue();
	}

	private BigDecimal calculateStockPrice(List<Trade> trades) {
		BigDecimal numerator = new BigDecimal("0.0");
		BigDecimal totalQuantity = new BigDecimal("0");
		for (Trade t: trades) {
			BigDecimal q = new BigDecimal(t.getQuantity());
			BigDecimal tradeValue = t.getPrice().multiply(q);
			numerator = numerator.add(tradeValue);
			totalQuantity = totalQuantity.add(q);
		}
		return numerator.divide(totalQuantity, 2);
	}


	private BigDecimal calculateFixedDividendYield(Stock stock, BigDecimal tickerPrice) {

		return stock.getFixedDividend()
				.multiply(stock.getParValue())
				.divide(tickerPrice, 2);
	}

	private BigDecimal calculateCommonDividendYield(Stock stock, BigDecimal tickerPrice) {
		return stock.getLastDividend()
				.divide(tickerPrice, 2);
	}

	@Override
	public BigDecimal calculatePERatio(StockSymbol stockSymbol) {
		BigDecimal dividend = this.calculateDividendYield(stockSymbol);
		BigDecimal per = new BigDecimal("0");
		if (!new BigDecimal("0").equals(dividend)) {
			BigDecimal tickerPrice = this.calculateTickerPrice(stockSymbol);
			per = tickerPrice.divide(dividend, 2);
		}
		return per;
	}

	@Override
	public BigDecimal calculateGeometricMean(List<BigDecimal> prices) {
		BigDecimal total = prices.stream()
				.reduce(new BigDecimal("1"), BigDecimal::multiply);
		double exponent = 1.0 / prices.size();
		double gm = Math.pow(total.doubleValue(), exponent);
		return new BigDecimal(gm);
	}

	@Override
	public BigDecimal calculateGBCE() {
		// Look for the trades grouped by the symbol data.
		Map<StockSymbol, List<Trade>> groupedData =
				this.tradeDao.groupTradesBySymbol();

		// Calculate the stock price of every available symbol
		List<BigDecimal> prices = new ArrayList<>();
		for (StockSymbol symbol : groupedData.keySet()) {
			List<Trade> symbolTrades = groupedData.get(symbol);
			prices.add(this.calculateStockPrice(symbolTrades));
		}

		BigDecimal gm = new BigDecimal("0");
		if (!prices.isEmpty()) {
			// calculate (and return) the G for the prices.
			gm = this.calculateGeometricMean(prices);
		}

		return gm;
	}

}

package com.mt.jpmorgan.facade.stock;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mt.jpmorgan.annotation.UseCase;
import com.mt.jpmorgan.model.ModelFactory;
import com.mt.jpmorgan.model.Trade;
import com.mt.jpmorgan.service.stock.StockService;
import com.mt.jpmorgan.service.trade.TradeService;
import com.mt.jpmorgan.types.StockSymbol;
import com.mt.jpmorgan.types.TradeType;

@Component
public class StockFacadeImpl implements StockFacade {

	@Autowired
	private StockService stockService;

	@Autowired
	private TradeService tradeService;

	@Autowired
	private ModelFactory modelFactory;

	@Override
	@UseCase(name = "FR-STOCKS-001"
			, description = "Calculate dividend yield command for specified stock")
	public BigDecimal calculateDividendYield(StockSymbol stockSymbol) {
		return this.stockService.calculateDividendYield(stockSymbol);
	}

	@Override
	@UseCase(name = "FR-STOCKS-002"
			, description = "Buy stocks")
	public void buyStocks(StockSymbol stockSymbol, BigDecimal price, Integer quantity) {
		Trade trade = this.initTrade(TradeType.BUY, stockSymbol, price, quantity);
		this.tradeService.recordTrade(trade);
	}

	@Override
	@UseCase(name = "FR-STOCKS-003"
			, description = "Sell stocks")
	public void sellStocks(StockSymbol stockSymbol, BigDecimal price, Integer quantity) {
		Trade trade = this.initTrade(TradeType.SELL, stockSymbol, price, quantity);
		this.tradeService.recordTrade(trade);
	}

	private Trade initTrade(TradeType tradeType, StockSymbol stockSymbol
			, BigDecimal price, Integer quantity) {

		Trade trade = this.modelFactory.createTrade();
		trade.setType(tradeType);
		trade.setDate(LocalDateTime.now());
		trade.setStockSymbol(stockSymbol);
		trade.setPrice(price);
		trade.setQuantity(quantity);
		return trade;
	}

	@Override
	@UseCase(name = "FR-STOCKS-004"
		, description = "Calculates the ticker price of a stock for the last 15 minutes trades")
	public BigDecimal calculateTickerPrice(StockSymbol stockSymbol) {
		return this.stockService.calculateTickerPrice(stockSymbol);
	}

	@Override
	@UseCase(name = "FR-STOCKS-005"
		, description = "Calculates the stock price")
	public BigDecimal calculateStockPrice(StockSymbol stockSymbol) {
		return this.stockService.calculateStockPrice(stockSymbol);
	}

	@Override
	@UseCase(name = "FR-STOCKS-006"
		, description = "Calculates the P/E ratio for a given stock")
	public BigDecimal calculatePERatio(StockSymbol stockSymbol) {
		return this.stockService.calculatePERatio(stockSymbol);
	}

	@Override
	@UseCase(name = "FR-STOCKS-007"
		, description = "Calculates GBCE All Share Index")
	public BigDecimal calculateGBCE() {
		return this.stockService.calculateGBCE();
	}
}

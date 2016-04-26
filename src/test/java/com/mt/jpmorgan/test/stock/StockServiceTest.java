package com.mt.jpmorgan.test.stock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mt.jpmorgan.SuperSimpleApp;
import com.mt.jpmorgan.dao.stock.StockDAO;
import com.mt.jpmorgan.dao.stock.TradeDAO;
import com.mt.jpmorgan.model.Stock;
import com.mt.jpmorgan.model.Trade;
import com.mt.jpmorgan.service.stock.StockService;
import com.mt.jpmorgan.service.stock.StockServiceImpl;
import com.mt.jpmorgan.types.StockSymbol;
import com.mt.jpmorgan.types.StockType;
import com.mt.jpmorgan.types.TradeType;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SuperSimpleApp.class)
public class StockServiceTest {

	private static final Duration EXCEEDED_DURATION = Duration.ofHours(3);
	private static final Duration WINDOW = Duration.ofMinutes(15);

	@InjectMocks
	private StockServiceImpl stockService;

	@Mock
	private StockDAO stockDAO;

	@Mock
	private TradeDAO tradeDAO;

	private Stock aleStock;
	private Stock ginStock;

	private List<Trade> emptyTradeList;
	private List<Trade> mockTradeList;
	// Gin trades list with all data
	private List<Trade> mockGinList;
	// Gin trades with current trades (for ticker price test)
	private List<Trade> mockGinCurrentTradesList;

	// Some mocked trades
	private Trade buy1;
	private Trade buy2;
	private Trade buy3;
	private Trade sell1;
	private Trade sell2;
	private Trade sell3;

	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        this.emptyTradeList = new ArrayList<>();
        this.mockTradeList = new ArrayList<>();
        this.mockGinList = new ArrayList<>();
        this.mockGinCurrentTradesList = new ArrayList<>();

        this.aleStock = new Stock();
        this.aleStock.setStockSymbol(StockSymbol.ALE);
        this.aleStock.setStockType(StockType.COMMON);
        this.aleStock.setFixedDividend(null);
        this.aleStock.setLastDividend(new BigDecimal("0.23"));
        this.aleStock.setParValue(new BigDecimal("0.60"));

        this.ginStock = new Stock();
        this.ginStock.setStockSymbol(StockSymbol.GIN);
        this.ginStock.setStockType(StockType.PREFERRED);
        this.ginStock.setFixedDividend(new BigDecimal("0.02"));
        this.ginStock.setLastDividend(new BigDecimal("0.08"));
        this.ginStock.setParValue(new BigDecimal("1"));

		LocalDateTime now = LocalDateTime.now();
		this.buy1 = new Trade();
		this.buy1.setType(TradeType.BUY);
		this.buy1.setStockSymbol(StockSymbol.GIN);
		this.buy1.setDate(now);
		this.buy1.setQuantity(100);
		this.buy1.setPrice(new BigDecimal("3.31"));

		this.buy2 = new Trade();
		this.buy2.setType(TradeType.BUY);
		this.buy2.setStockSymbol(StockSymbol.GIN);
		this.buy2.setDate(now.minus(EXCEEDED_DURATION));
		this.buy2.setQuantity(15);
		this.buy2.setPrice(new BigDecimal("3.18"));

		this.buy3 = new Trade();
		this.buy3.setType(TradeType.BUY);
		this.buy3.setStockSymbol(StockSymbol.JOE);
		this.buy3.setDate(now);
		this.buy3.setQuantity(1800);
		this.buy3.setPrice(new BigDecimal("12.01"));

		this.sell1 = new Trade();
		this.sell1.setType(TradeType.SELL);
		this.sell1.setStockSymbol(StockSymbol.ALE);
		this.sell1.setDate(now);
		this.sell1.setQuantity(721);
		this.sell1.setPrice(new BigDecimal("7.28"));

		this.sell2 = new Trade();
		this.sell2.setType(TradeType.SELL);
		this.sell2.setStockSymbol(StockSymbol.GIN);
		this.sell2.setDate(now);
		this.sell2.setQuantity(356);
		this.sell2.setPrice(new BigDecimal("3.52"));

		this.sell3 = new Trade();
		this.sell3.setType(TradeType.SELL);
		this.sell3.setStockSymbol(StockSymbol.POP);
		this.sell3.setDate(now);
		this.sell3.setQuantity(312);
		this.sell3.setPrice(new BigDecimal("1.11"));

		this.mockTradeList.add(this.buy1);
		this.mockTradeList.add(this.buy2);
		this.mockTradeList.add(this.buy3);
		this.mockTradeList.add(this.sell1);
		this.mockTradeList.add(this.sell2);
		this.mockTradeList.add(this.sell3);

		this.mockGinList.add(this.buy1);
		this.mockGinList.add(this.buy2);
		this.mockGinList.add(this.sell2);

		this.mockGinCurrentTradesList.add(this.buy1);
		this.mockGinCurrentTradesList.add(this.sell2);

	}

	@Test
	public void calculateDividendYield() {

		BigDecimal mockedStockPrice = new BigDecimal("6.0");
		// Create stub for inner method.
		StockService spyStockService = Mockito.spy(this.stockService);
		Mockito
			.doReturn(mockedStockPrice)
			.when(spyStockService)
			.calculateTickerPrice(StockSymbol.ALE);

		Mockito
			.doReturn(this.aleStock)
			.when(this.stockDAO)
			.searchBySymbol(StockSymbol.ALE);

		BigDecimal dividendYield = spyStockService.calculateDividendYield(StockSymbol.ALE);

		BigDecimal expectedValue = this.aleStock.getLastDividend().divide(mockedStockPrice, 2);
		assertFalse("Calculate Dividend cannot be null", dividendYield == null);
		assertEquals("Calculate Dividend is incorrect", expectedValue, dividendYield);
	}

	@Test
	public void calculateFixedYield() {

		BigDecimal mockedStockPrice = new BigDecimal("6.0");
		// Create stub for inner method.
		StockService spyStockService = Mockito.spy(this.stockService);
		Mockito
			.doReturn(mockedStockPrice)
			.when(spyStockService)
			.calculateTickerPrice(StockSymbol.GIN);

		Mockito
			.doReturn(this.ginStock)
			.when(this.stockDAO)
			.searchBySymbol(StockSymbol.GIN);

		BigDecimal dividendYield = spyStockService.calculateDividendYield(StockSymbol.GIN);

		BigDecimal expectedValue = this.ginStock.getFixedDividend()
				.multiply(this.ginStock.getParValue())
				.divide(mockedStockPrice, 2);
		assertFalse("Calculate Dividend cannot be null", dividendYield == null);
		assertEquals("Calculate Dividend is incorrect", expectedValue, dividendYield);
	}

	@Test
	public void shouldCalculateTickerPriceBasedOnTrades() {
		// Create stub for inner method.
		StockService spyStockService = Mockito.spy(this.stockService);

		Mockito
			.when(this.tradeDAO.searchCurrentTrades(StockSymbol.GIN, WINDOW))
			.thenReturn(this.mockGinCurrentTradesList);

		Mockito
			.when(this.stockDAO.searchBySymbol(StockSymbol.GIN))
			.thenReturn(this.ginStock);

		// Calculate price * quantity for each gin trade
		BigDecimal gin1Value = this.buy1.getPrice().multiply(new BigDecimal(this.buy1.getQuantity()));
		BigDecimal gin2Value = this.sell2.getPrice().multiply(new BigDecimal(this.sell2.getQuantity()));

		// Sum quantities
		Integer totalQuantity = Integer.sum(this.buy1.getQuantity(), this.sell2.getQuantity());

		// Calculate total (expected value)
		BigDecimal sum = gin1Value.add(gin2Value);
		BigDecimal expectedValue = sum.divide(new BigDecimal(totalQuantity), 2);

		BigDecimal tickerPrice = spyStockService.calculateTickerPrice(StockSymbol.GIN);
		assertFalse("Ticker price cannot be null", tickerPrice == null);
		assertEquals("Ticker price is incorrect", expectedValue, tickerPrice);

	}

	@Test
	public void shouldTickerPriceBeParValueWhenNoTradesAvailable() {
		// Create stub for inner method.
		StockService spyStockService = Mockito.spy(this.stockService);

		Duration WINDOW = Duration.ofMinutes(15);
		Mockito.when(this.tradeDAO.searchCurrentTrades(StockSymbol.GIN, WINDOW)).thenReturn(this.emptyTradeList);
		Mockito.when(this.stockDAO.searchBySymbol(StockSymbol.GIN)).thenReturn(this.ginStock);

		BigDecimal expectedValue = this.ginStock.getParValue();
		BigDecimal tickerPrice = spyStockService.calculateTickerPrice(StockSymbol.GIN);
		assertFalse("Ticker price cannot be null", tickerPrice == null);
		assertEquals("Ticker price is incorrect", expectedValue, tickerPrice);

	}

	@Test
	public void shouldCalculateStockPriceBasedOnTrades() {
		// Create stub for inner method.
		StockService spyStockService = Mockito.spy(this.stockService);

		Mockito.when(this.tradeDAO.search(StockSymbol.GIN)).thenReturn(this.mockGinList);
		Mockito.when(this.stockDAO.searchBySymbol(StockSymbol.GIN)).thenReturn(this.ginStock);

		// Calculate price * quantity for each gin trade
		BigDecimal gin1Value = this.buy1.getPrice().multiply(new BigDecimal(this.buy1.getQuantity()));
		BigDecimal gin2Value = this.buy2.getPrice().multiply(new BigDecimal(this.buy2.getQuantity()));
		BigDecimal gin3Value = this.sell2.getPrice().multiply(new BigDecimal(this.sell2.getQuantity()));

		// Sum quantities
		Integer totalQuantity = Integer.sum(this.buy1.getQuantity(), this.buy2.getQuantity());
		totalQuantity = Integer.sum(totalQuantity, this.sell2.getQuantity());

		// Calculate total (expected value)
		BigDecimal sum = gin1Value.add(gin2Value).add(gin3Value);
		BigDecimal expectedValue = sum.divide(new BigDecimal(totalQuantity), 2);

		BigDecimal stockPrice = spyStockService.calculateStockPrice(StockSymbol.GIN);
		assertFalse("Stock price cannot be null", stockPrice == null);
		assertEquals("Stock price is incorrect", expectedValue, stockPrice);

	}


	@Test
	public void shouldStockPriceshouldBeParValueWhenNoTradesAvailable() {
		// Create stub for inner method.
		StockService spyStockService = Mockito.spy(this.stockService);

		Duration WINDOW = Duration.ofMinutes(15);
		Mockito.when(this.tradeDAO.searchCurrentTrades(StockSymbol.GIN, WINDOW)).thenReturn(this.emptyTradeList);
		Mockito.when(this.stockDAO.searchBySymbol(StockSymbol.GIN)).thenReturn(this.ginStock);

		BigDecimal expectedValue = this.ginStock.getParValue();
		BigDecimal tickerPrice = spyStockService.calculateStockPrice(StockSymbol.GIN);
		assertFalse("Stock price cannot be null", tickerPrice == null);
		assertEquals("Stock price is incorrect", expectedValue, tickerPrice);

	}

	@Test
	public void calculatePERatio() {

		BigDecimal mockedTickerPrice = new BigDecimal("6.0");
		BigDecimal mockedDividend = new BigDecimal("0.15");

		// Mock the DAO.
		Mockito.when(this.stockDAO.searchBySymbol(StockSymbol.GIN)).thenReturn(this.ginStock);

		// Create stubs for inner methods.
		StockService spyStockService = Mockito.spy(this.stockService);
		Mockito
			.doReturn(mockedTickerPrice)
			.when(spyStockService)
			.calculateTickerPrice(StockSymbol.GIN);

		Mockito
			.doReturn(mockedDividend)
			.when(spyStockService)
			.calculateDividendYield(StockSymbol.GIN);

		BigDecimal ratio = spyStockService.calculatePERatio(StockSymbol.GIN);

		BigDecimal expectedValue = mockedTickerPrice
				.divide(mockedDividend, 2);
		assertFalse("P/E ratio cannot be null", ratio == null);
		assertEquals("P/E ratio is incorrect", expectedValue, ratio);

	}

	@Test
	public void calculateGeometricMean() {
		// Number of products
		List<BigDecimal> prices = new ArrayList<>();
		prices.add(new BigDecimal(1));
		prices.add(new BigDecimal(2));
		prices.add(new BigDecimal(3));
		prices.add(new BigDecimal(4));
		prices.add(new BigDecimal(5));

		BigDecimal numerator = prices.stream().reduce(new BigDecimal("1"), BigDecimal::multiply);
		double exponent = 1.0 / prices.size();
		double value = Math.pow(numerator.doubleValue(), exponent);

		BigDecimal expectedValue = new BigDecimal(value);

		BigDecimal gm = this.stockService.calculateGeometricMean(prices);
		assertFalse("GM cannot be null", gm == null);
		assertEquals("GM value is incorrect", expectedValue, gm);
	}


}


package com.mt.jpmorgan.test.stock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.mt.jpmorgan.model.Trade;
import com.mt.jpmorgan.service.stock.StockService;
import com.mt.jpmorgan.service.stock.StockServiceImpl;
import com.mt.jpmorgan.types.StockSymbol;
import com.mt.jpmorgan.types.TradeType;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SuperSimpleApp.class)
public class StockGBCETest {

	@InjectMocks
	private StockServiceImpl stockService;

	@Mock
	private StockDAO stockDAO;

	@Mock
	private TradeDAO tradeDAO;

	private Map<StockSymbol, List<Trade>> groupedData;
	private BigDecimal mockGinStockPrice;
	private BigDecimal mockPopStockPrice;

	@Before
	public void init() {
        MockitoAnnotations.initMocks(this);

        this.groupedData = new HashMap<>();
		LocalDateTime now = LocalDateTime.now();
		Trade buy1 = new Trade();
		buy1.setType(TradeType.BUY);
		buy1.setStockSymbol(StockSymbol.GIN);
		buy1.setDate(now);
		buy1.setQuantity(100);
		buy1.setPrice(new BigDecimal("3.31"));

		Trade buy2 = new Trade();
		buy2.setType(TradeType.BUY);
		buy2.setStockSymbol(StockSymbol.GIN);
		buy2.setDate(now);
		buy2.setQuantity(15);
		buy2.setPrice(new BigDecimal("3.18"));

		List<Trade> ginTrades = new ArrayList<>();
		ginTrades.add(buy1);
		ginTrades.add(buy2);

		// Calculate price * quantity for each gin trade
		BigDecimal gin1Value = buy1.getPrice().multiply(new BigDecimal(buy1.getQuantity()));
		BigDecimal gin2Value = buy2.getPrice().multiply(new BigDecimal(buy2.getQuantity()));

		// Sum quantities
		Integer totalQuantity = Integer.sum(buy1.getQuantity(), buy2.getQuantity());

		// Calculate gin stock price
		BigDecimal sum = gin1Value.add(gin2Value);
		this.mockGinStockPrice = sum.divide(new BigDecimal(totalQuantity), 2);


		Trade pop1 = new Trade();
		pop1.setType(TradeType.BUY);
		pop1.setStockSymbol(StockSymbol.POP);
		pop1.setDate(now);
		pop1.setQuantity(10);
		pop1.setPrice(new BigDecimal("2.17"));

		Trade pop2 = new Trade();
		pop2.setType(TradeType.BUY);
		pop2.setStockSymbol(StockSymbol.POP);
		pop2.setDate(now);
		pop2.setQuantity(15);
		pop2.setPrice(new BigDecimal("3.18"));

		List<Trade> popTrades = new ArrayList<>();
		popTrades.add(buy1);
		popTrades.add(buy2);

		// Calculate price * quantity for each gin trade
		BigDecimal pop1Value = buy1.getPrice().multiply(new BigDecimal(buy1.getQuantity()));
		BigDecimal pop2Value = buy2.getPrice().multiply(new BigDecimal(buy2.getQuantity()));

		// Sum quantities
		Integer totalPopQuantity = Integer.sum(buy1.getQuantity(), buy2.getQuantity());

		// Calculate pop stock price
		BigDecimal popSum = pop1Value.add(pop2Value);
		this.mockPopStockPrice = popSum.divide(new BigDecimal(totalPopQuantity), 2);

		this.groupedData.put(StockSymbol.GIN, ginTrades);
		this.groupedData.put(StockSymbol.POP, popTrades);

	}

	@Test
	public void calculateGBCE() {

		// Mock the DAO.
		Mockito
			.when(this.tradeDAO.groupTradesBySymbol())
			.thenReturn(this.groupedData);

		// Create stub for inner method.
		StockService spyStockService = Mockito.spy(this.stockService);
		Mockito
			.doReturn(this.mockGinStockPrice)
			.when(spyStockService)
			.calculateStockPrice(StockSymbol.GIN);

		Mockito
			.doReturn(this.mockPopStockPrice)
			.when(spyStockService)
			.calculateStockPrice(StockSymbol.POP);

		List<BigDecimal> prices = new ArrayList<>();
		prices.add(this.mockGinStockPrice);
		prices.add(this.mockPopStockPrice);

		// Once that everything is mocked the only test is to be sure that geometric mean is called.
		BigDecimal expectedValue = spyStockService.calculateGeometricMean(prices);
		BigDecimal gbce = spyStockService.calculateGBCE();
		assertFalse("GBCE cannot be null", gbce == null);
		assertEquals("GBCE is incorrect", expectedValue, gbce);
	}

	@Test
	public void shouldGBCEReturnZeroWhenEmptyTrades() {

		Map<StockSymbol, List<Trade>> emptyData = new HashMap<>();
		// Mock the DAO.
		Mockito
			.doReturn(emptyData)
			.when(this.tradeDAO)
			.groupTradesBySymbol();

		BigDecimal expectedValue = new BigDecimal("0");
		BigDecimal gbce = this.stockService.calculateGBCE();
		assertFalse("GBCE cannot be null", gbce == null);
		assertEquals("GBCE is incorrect", expectedValue, gbce);

	}
}

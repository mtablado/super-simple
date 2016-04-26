package com.mt.jpmorgan.test.stock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mt.jpmorgan.SuperSimpleApp;
import com.mt.jpmorgan.dao.stock.StockDAOImpl;
import com.mt.jpmorgan.model.Stock;
import com.mt.jpmorgan.types.StockSymbol;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SuperSimpleApp.class)
public class StockDAOTest {

	@InjectMocks
	private StockDAOImpl stockDao;

	private Stock aleStock;

	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.aleStock = new Stock();
        this.aleStock.setStockSymbol(StockSymbol.ALE);
        this.aleStock.setFixedDividend(null);
        this.aleStock.setLastDividend(new BigDecimal("0.23"));
        this.aleStock.setParValue(new BigDecimal("0.60"));
    }

	@Test
	public void searchAleStock() {

		Stock stock = this.stockDao.searchBySymbol(StockSymbol.ALE);

		assertFalse("Ale stock cannot be null", stock == null);
		assertEquals("Ale stock symbol must be ALE", StockSymbol.ALE, stock.getStockSymbol());
	}

}

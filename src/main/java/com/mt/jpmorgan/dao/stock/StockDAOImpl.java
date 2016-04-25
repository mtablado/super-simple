package com.mt.jpmorgan.dao.stock;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.mt.jpmorgan.model.Stock;
import com.mt.jpmorgan.types.StockSymbol;

@Resource
@Component
public class StockDAOImpl implements StockDAO {

	private final Map<StockSymbol, Stock> dummyStockData
		= new HashMap<StockSymbol, Stock>();

	private Stock getAleStock() {
        Stock aleStock = new Stock();
        aleStock.setStockSymbol(StockSymbol.ALE);
        aleStock.setFixedDividend(null);
        aleStock.setLastDividend(new BigDecimal("0.23"));
        aleStock.setParValue(new BigDecimal("0.60"));
        return aleStock;
	}

	private Stock getGinStock() {
        Stock aleStock = new Stock();
        aleStock.setStockSymbol(StockSymbol.GIN);
        aleStock.setFixedDividend(new BigDecimal("0.02"));
        aleStock.setLastDividend(new BigDecimal("0.08"));
        aleStock.setParValue(new BigDecimal("1"));
        return aleStock;
	}

	private Stock getPopStock() {
        Stock aleStock = new Stock();
        aleStock.setStockSymbol(StockSymbol.POP);
        aleStock.setFixedDividend(null);
        aleStock.setLastDividend(new BigDecimal("0.08"));
        aleStock.setParValue(new BigDecimal("1"));
        return aleStock;
	}

	private Stock getJoeStock() {
        Stock aleStock = new Stock();
        aleStock.setStockSymbol(StockSymbol.JOE);
        aleStock.setFixedDividend(null);
        aleStock.setLastDividend(new BigDecimal("0.13"));
        aleStock.setParValue(new BigDecimal("2.5"));
        return aleStock;
	}

	private Stock getTeaStock() {
        Stock aleStock = new Stock();
        aleStock.setStockSymbol(StockSymbol.TEA);
        aleStock.setFixedDividend(null);
        aleStock.setLastDividend(new BigDecimal("0"));
        aleStock.setParValue(new BigDecimal("1"));
        return aleStock;
	}

	/**
	 * Not the best constructor for dummy purposes.
	 */
	public StockDAOImpl() {
		this.dummyStockData.put(StockSymbol.ALE, this.getAleStock());
		this.dummyStockData.put(StockSymbol.GIN, this.getGinStock());
		this.dummyStockData.put(StockSymbol.JOE, this.getJoeStock());
		this.dummyStockData.put(StockSymbol.POP, this.getPopStock());
		this.dummyStockData.put(StockSymbol.TEA, this.getTeaStock());
	}


	@Override
	public Stock searchBySymbol(StockSymbol stockSymbol) {
		return this.dummyStockData.get(stockSymbol);
	}

}


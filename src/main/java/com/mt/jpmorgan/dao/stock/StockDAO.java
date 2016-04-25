package com.mt.jpmorgan.dao.stock;

import com.mt.jpmorgan.model.Stock;
import com.mt.jpmorgan.types.StockSymbol;

public interface StockDAO {

	Stock searchBySymbol(StockSymbol stockSymbol);
}

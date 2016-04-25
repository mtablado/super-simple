package com.mt.jpmorgan.model;

import java.math.BigDecimal;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mt.jpmorgan.types.StockSymbol;
import com.mt.jpmorgan.types.StockType;

@Component
@Scope("prototype")
public class Stock {

	private Integer id;
	private StockSymbol stockSymbol;
	private StockType stockType;
	private BigDecimal parValue;
	private BigDecimal fixedDividend;
	private BigDecimal lastDividend;


	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public StockSymbol getStockSymbol() {
		return this.stockSymbol;
	}
	public void setStockSymbol(StockSymbol stockSymbol) {
		this.stockSymbol = stockSymbol;
	}
	public StockType getStockType() {
		return this.stockType;
	}
	public void setStockType(StockType stockType) {
		this.stockType = stockType;
	}
	public BigDecimal getParValue() {
		return this.parValue;
	}
	public void setParValue(BigDecimal parValue) {
		this.parValue = parValue;
	}
	public BigDecimal getFixedDividend() {
		return this.fixedDividend;
	}
	public void setFixedDividend(BigDecimal fixedDividend) {
		this.fixedDividend = fixedDividend;
	}
	public BigDecimal getLastDividend() {
		return this.lastDividend;
	}
	public void setLastDividend(BigDecimal lastDividend) {
		this.lastDividend = lastDividend;
	}

}

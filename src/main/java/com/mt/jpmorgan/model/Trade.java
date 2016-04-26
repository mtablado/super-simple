package com.mt.jpmorgan.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.mt.jpmorgan.types.StockSymbol;
import com.mt.jpmorgan.types.TradeType;

public class Trade {

	private LocalDateTime date;
	private Integer quantity;
	// TODO Check what sell indicator is.
	private TradeType type;
	private StockSymbol stockSymbol;
	private BigDecimal price;

	public LocalDateTime getDate() {
		return this.date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public Integer getQuantity() {
		return this.quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public TradeType getType() {
		return this.type;
	}
	public void setType(TradeType type) {
		this.type = type;
	}
	public BigDecimal getPrice() {
		return this.price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public StockSymbol getStockSymbol() {
		return this.stockSymbol;
	}
	public void setStockSymbol(StockSymbol stockSymbol) {
		this.stockSymbol = stockSymbol;
	}


}

/**
 *
 */
package com.mt.jpmorgan.controller.stock;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mt.jpmorgan.facade.stock.StockFacade;
import com.mt.jpmorgan.types.StockSymbol;

/**
 * @author miguel.tablado.leon
 *
 */
@RestController
@RequestMapping("/stock/**")
public class StockController {

	private static final Logger LOG = LoggerFactory.getLogger(StockController.class);

	@Autowired
	private StockFacade stockFacade;

	@RequestMapping("/calculate-dividend-yield/{id}")
	@ResponseBody
	public BigDecimal calculateDivedendYield(
			@PathVariable("id") StockSymbol stockSymbol) {

		LOG.info("Calculate Dividend Yield request received for stock id: " + stockSymbol);
		return this.stockFacade.calculateDividendYield(stockSymbol);
	}

	@RequestMapping(value = "/price-earning-ratio/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String priceEarningRatio(
			@PathVariable("id") StockSymbol stockSymbol) {

		LOG.info("Calculate PE ratio request received for stock id: " + stockSymbol);
		BigDecimal per = this.stockFacade.calculatePERatio(stockSymbol);
		LOG.info("PE ratio: " + per);
		return per.toString();
	}

	@RequestMapping(value = "/ticker-price/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String calculateTickerPrice(
			@PathVariable("id") StockSymbol stockSymbol) {

		LOG.info("Calculate ticker price request received for stock id: " + stockSymbol);
		BigDecimal tickerPrice = this.stockFacade.calculateTickerPrice(stockSymbol);
		LOG.info("Ticker price: " + tickerPrice);
		return tickerPrice.toString();

	}

	@RequestMapping(value = "/stock-price/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String calculateStockPrice(
			@PathVariable("id") StockSymbol stockSymbol) {

		LOG.info("Calculate stock price request received for stock id: " + stockSymbol);
		BigDecimal stockPrice = this.stockFacade.calculateStockPrice(stockSymbol);
		LOG.info("Stock price: " + stockPrice);
		return stockPrice.toString();
	}

	@RequestMapping(value = "/gbce", method = RequestMethod.GET)
	@ResponseBody
	public String calculateGBCE() {

		LOG.info("Calculate GBCE All Share index request received.");
		BigDecimal gbce = this.stockFacade.calculateGBCE();
		LOG.info("GBCE: " + gbce);
		return gbce.toString();
	}

}
